/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.http;

import com.envelopes.util.EnvConstantsUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.content.data.DataResourceWorker;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class FileHelper {
	public static final String module = FileHelper.class.getName();

	/*
	 * Get a file from server for stream to user, only from uploads folder
	 */
	public static String serveFileForStream(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException, IOException, Exception {
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			if(UtilValidate.isNotEmpty(request.getParameter("filePath"))) {
				String filePath = EnvConstantsUtil.OFBIZ_HOME + request.getParameter("filePath");
				//validate the path, make sure there arent any "../" in there
				if(filePath.contains("../")) {
					return "false";
				}

				File file = new File(filePath);

				//if for any reason the new path doesnt contain upload root, exit
				if(file.getCanonicalPath().contains(EnvConstantsUtil.FILE_PATH) || file.getCanonicalPath().contains(EnvConstantsUtil.OFBIZ_HOME) || file.getCanonicalPath().replace('\\', '/').contains(EnvConstantsUtil.OFBIZ_HOME)) {
					FileInputStream in = new FileInputStream(file);
					response.setContentType(getMimeType((Delegator) request.getAttribute("delegator"), file.getName()));
					if(UtilValidate.isNotEmpty(request.getParameter("downLoad")) && request.getParameter("downLoad").equalsIgnoreCase("Y")) {
						response.setHeader("Content-Disposition","attachment;filename=" + (UtilValidate.isNotEmpty(request.getParameter("fileName")) ? request.getParameter("fileName") : file.getName()));
					}
					UtilIO.copy(in, true, os, false);
				}
			} else if(UtilValidate.isNotEmpty(request.getParameter("url"))) {
				List<Map> processedURLs = HTTPHelper.splitURL(UtilMisc.toList(request.getParameter("url")));
				for(Map urlMap : processedURLs) {
					String base = (String) urlMap.get("url");
					String params = (String) urlMap.get("body");

					URL page = new URL(base);
					URLConnection connection = page.openConnection();

					//if the url is too long we will be passing it via POST
					if(params != null) {
						connection.setDoOutput(true);
						OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
						writer.write(params);
						writer.flush();
					}
					response.setContentType(connection.getHeaderField("Content-Type"));
					UtilIO.copy(connection.getInputStream(), true, os, false);
				}
			}
			os.flush();
		} finally {
			if(os != null) {
				os.close();
			}
		}

		return "success";
	}

	public static Map<String, Object> saveBase64File(String base64, String fileName, String extension, String fileUploadDir) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();

		String fileUploadPath = getUploadPath(fileUploadDir);
		String fullFileName = fileName + "." + extension;

		byte[] data = Base64.decodeBase64(base64);
		OutputStream stream = new FileOutputStream(fileUploadPath+ "/" + fullFileName);
		stream.write(data);

		response.put("name", fullFileName);
		response.put("path", fileUploadPath.replace(EnvConstantsUtil.OFBIZ_HOME, "") + "/" + fullFileName);

		response.put("success", true);

		return response;
	}

	public static String convertToBase64(HttpServletRequest request) throws Exception {
		if(UtilValidate.isNotEmpty(request.getParameter("filePath"))) {
			String filePath = EnvConstantsUtil.OFBIZ_HOME + request.getParameter("filePath");
			//validate the path, make sure there aren't any "../" in there
			if(filePath.contains("../")) {
				return "";
			}
			File imageFile = new File(filePath);
			byte[] fileContent = FileUtils.readFileToByteArray(imageFile);
			return Base64.encodeBase64String(fileContent);
		} else if(UtilValidate.isNotEmpty(request.getParameter("url"))) {
			URL url = new URL(request.getParameter("url"));
			byte[] bytes = IOUtils.toByteArray(url.openStream());
			return Base64.encodeBase64String(bytes);
		}
		return "";
	}

	/*
	 * Takes a file from the request and saves it to a specified dir
	 * @param deleteFile   Pass false if thie file should not be saved, only used to pass to another function
	 * @param readOnly     Pass true if you want to convert the uploaded file to a string for reading only
	 */
	public static Map<String, Object> uploadFile(HttpServletRequest request, String fileUploadDir, boolean deleteFile, boolean readOnly) throws FileUploadException, Exception {
		Map<String, Object> response = new HashMap<String, Object>();

		Map<String, Object> context = UtilHttp.getParameterMap(request);
		List<Map> fileList = new ArrayList<Map>();
		List<FileItem> items = new ArrayList<FileItem>();
		String fileUploadPath = getUploadPath(fileUploadDir);

		if(UtilValidate.isNotEmpty(context)) {
			Iterator iter = context.entrySet().iterator();
			while(iter.hasNext()) {
				Map.Entry pairs = (Map.Entry) iter.next();
				if(pairs.getValue() instanceof FileItem) {
					items.add((FileItem) pairs.getValue());
				}
			}
		}

		for(FileItem item : items) {
			Map<String, Object> fileData = new HashMap<String, Object>();
			if(!readOnly) {
				File file = makeFileFromFileItem(item, fileUploadPath, null);
				if(!deleteFile) {
					item.write(file);
					fileData.put("file", file);
				}

				fileData.put("name", item.getName());
				fileData.put("path", fileUploadPath.replace(EnvConstantsUtil.OFBIZ_HOME, "") + "/" + file.getName());
				fileData.put("size", item.getSize());
				fileData.put("data", null);
				fileData.put("fileitem", item);
				fileList.add(fileData);
			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader(item.getInputStream()));
				try {
					StringBuilder strB = new StringBuilder();
					String line;
					while((line = br.readLine()) != null) {
						strB.append(line);
					}
					fileData.put("name", item.getName());
					fileData.put("path", null);
					fileData.put("size", item.getSize());
					fileData.put("data", strB.toString());
					fileList.add(fileData);
				} finally {
					item.delete();
				}
			}
		}

		response.put("success", true);
		response.put("files", fileList);
		return response;
	}

	public static Map<String, Object> saveFileFromStream(HttpServletRequest request, String fileName, String fileUploadDir) throws Exception {
		Map<String, Object> response = new HashMap<>();
		Map<String, Object> context = UtilHttp.getParameterMap(request);
		String fileUploadPath = getUploadPath(fileUploadDir);
		String fileData = (String) context.get("base64");
		boolean success = false;
		if(UtilValidate.isNotEmpty(fileData)) {
			String fileDataBytes = fileData.substring(fileData.indexOf(",") + 1);
			String extension = null;
			if(UtilValidate.isNotEmpty(fileName) && fileName.contains(".")) {
				extension = fileName.substring(fileName.lastIndexOf(".") + 1).trim();
			}
			if(UtilValidate.isEmpty(extension)) {
				extension = null;
			}

			OutputStream outStream = null;
			try {
				File file = FileHelper.makeFile(null, fileUploadPath, extension);
				outStream = new FileOutputStream(file);
				outStream.write(java.util.Base64.getDecoder().decode(fileDataBytes.getBytes()));
				response.put("name", fileName);
				response.put("path", fileUploadPath.replace(EnvConstantsUtil.OFBIZ_HOME, "") + "/" + file.getName());
				success = true;
			} catch (IOException e) {
				Debug.logError(e, "Error trying to create file :" + fileName, module);
			} finally {
				if(outStream != null) {
					outStream.close();
				}
			}
		}
		response.put("success", success);
		return response;
	}

	public static Map<String, Object> createFileFromFile(File src, String fileName) throws Exception {
		Map<String, Object> response = new HashMap<>();
		boolean success = false;

		if (UtilValidate.isNotEmpty(fileName)) {
			String fileUploadPath = getUploadPath(null);
			File file = makeFile(src, fileUploadPath, null);
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
			try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
				int inByte;
				while((inByte = bis.read()) != -1) bos.write(inByte);
				bis.close();
				response.put("name", fileName);
				response.put("path", fileUploadPath.replace(EnvConstantsUtil.OFBIZ_HOME, "") + "/" + file.getName());
				success = true;
			} catch(Exception e) {
				Debug.logError(e, "Error trying to create file :" + fileName, module);
			}
		}
		response.put("success", success);
		return response;
	}

	/*
	 * Generate a FileItem with unique name
	 */
	public static File makeFileFromFileItem(FileItem item, String fileUploadPath, String extension) {
		String fileName = generateFileName(item, extension);
		File file = new File(fileUploadPath, fileName);
		while(file.exists() && !file.isDirectory()) {
			fileName = generateFileName(item, extension);
			file = new File(fileUploadPath, fileName);
		}

		return file;
	}

	/*
	 * Generate a File with unique name
	 */
	public static File makeFile(File item, String fileUploadPath, String extension) {
		String fileName = generateFileName(item, extension);
		File file = new File(fileUploadPath, fileName);
		while(file.exists() && !file.isDirectory()) {
			fileName = generateFileName(item, extension);
			file = new File(fileUploadPath, fileName);
		}

		return file;
	}

	/*
	 * Format file name
	 */
	public static String generateFileName(FileItem item, String extension) {
		String fileName = null;
		if(item != null) {
			fileName = RandomStringUtils.randomAlphanumeric(8).toUpperCase() + "." + getFileExtension(item.getName());
		} else if(UtilValidate.isNotEmpty(extension)) {
			fileName = RandomStringUtils.randomAlphanumeric(8).toUpperCase() + "." + extension;
		} else {
			fileName = RandomStringUtils.randomAlphanumeric(8).toUpperCase() + ".noext";
		}
		return fileName;
	}

	/*
	 * Format file name
	 */
	public static String generateFileName(File item, String extension) {
		String fileName = null;
		if(item != null) {
			fileName = RandomStringUtils.randomAlphanumeric(8).toUpperCase() + "." + getFileExtension(item.getName());
		} else if(UtilValidate.isNotEmpty(extension)) {
			fileName = RandomStringUtils.randomAlphanumeric(8).toUpperCase() + "." + extension;
		} else {
			fileName = RandomStringUtils.randomAlphanumeric(8).toUpperCase() + ".noext";
		}
		return fileName;
	}

	/*
	 * Get full path dir to store file
	 */
	public static String getUploadPath(String fileUploadDir) {
		String path = null;
		if(UtilValidate.isNotEmpty(fileUploadDir)) {
			path = DataResourceWorker.getDataResourceContentUploadPath(fileUploadDir, EnvConstantsUtil.UPLOAD_MAX_FILES, true);
		} else {
			path = DataResourceWorker.getDataResourceContentUploadPath(EnvConstantsUtil.UPLOAD_DIR, EnvConstantsUtil.UPLOAD_MAX_FILES, true);
		}

		return path.toString();
	}

	public static String getMimeType(Delegator delegator, String fileName) throws GenericEntityException {
		String mimeTypeId = null;

		if(fileName != null && fileName.indexOf('.') > -1) {
			String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
			if(UtilValidate.isNotEmpty(fileExtension)) {
				GenericValue ext = null;
				try {
					ext = delegator.findOne("FileExtension", UtilMisc.toMap("fileExtensionId", fileExtension), true);
				} catch (GenericEntityException e) {
					Debug.logError(e, module);
				}
				if(ext != null) {
					mimeTypeId = ext.getString("mimeTypeId");
				}
			}
		}

		// check one last time
		if (UtilValidate.isEmpty(mimeTypeId)) {
			// use a default mime type
			mimeTypeId = "application/octet-stream";
		}


		return mimeTypeId;
	}

	public static String doesFileExist(String folder, String fileName) {
		String filePath = EnvConstantsUtil.OFBIZ_HOME + folder;
		filePath = cleanFolderPath(filePath);

		File file = new File(filePath, fileName);
		if(file.exists()) {
			return filePath.replace(EnvConstantsUtil.OFBIZ_HOME, "") + fileName;
		}

		return null;
	}

	private static String getFileExtension(String filename) {
		if(UtilValidate.isEmpty(filename)) {
			return null;
		}
		String suffix = "";
		int pos = filename.lastIndexOf('.');
		if(pos > 0 && pos < filename.length() - 1) {
			suffix = filename.substring(pos + 1);
		}
		return suffix;
	}

	public static String cleanFolderPath(String folder) {
		if(UtilValidate.isNotEmpty(folder)) {
			String newFolder = folder.replaceAll("\\/\\/", "\\/");
			if(!newFolder.endsWith("/")) {
				newFolder = newFolder + "/";
			}
			return newFolder;
		}

		return folder;
	}
}