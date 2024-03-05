package com.envelopes.addressing;
import com.envelopes.http.FileHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Manu on 8/25/2015.
 */
public class AddressingUtil {

	public static final String DEFAULT_ATTRIBUTE_SET = "address";

	public static final Map<String, String[]> attributeSetMap = new HashMap<>();

	static {
		attributeSetMap.put(DEFAULT_ATTRIBUTE_SET, new String [] {"Name Line 1", "Name Line 2", "Address Line 1", "Address Line 2", "City", "State", "Zip", "Country"});
	}

	public static final String module = AddressingUtil.class.getName();

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyMMddHHmmss");

	public static final String DEFAULT_ADDRESS_BOOK_NAME = "AddressBook-";

	public static void main(String[] args) {
		parseAddressFile("C:\\var\\addressesXLS.xls", true);
		parseAddressFile("C:\\var\\addressesXLSX.xlsx", true);
		parseAddressFile("C:\\var\\addressesCSV.csv", true);
		parseAddressFile("C:\\var\\addressesTAB.txt", true);

	}

	public static String[][] parseAddressFile(String filePath, boolean hasHeader) {
		String ext = FilenameUtils.getExtension(filePath);
		String[][] data = new String[0][];
		if(ext.equalsIgnoreCase("xls") || ext.equalsIgnoreCase("xlsx")) {
			data = convertListToArray(readXLSAndXLSX(filePath, hasHeader));
		} else if(ext.equalsIgnoreCase("csv") || ext.equalsIgnoreCase("txt")) {
			data = convertListToArray(readCSVAndTABDelimited(filePath, ext.equalsIgnoreCase("txt"), hasHeader));
		}
		return data;
	}

	public static List<List<Object>> readXLSAndXLSX(String filePath, boolean hasHeader) {
		List<List<Object>> gridData = new ArrayList<>();
		try {
			Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(filePath)));
			Sheet sheet = workbook.getSheetAt(0);

			for(int i = hasHeader ? 1 : 0; i < sheet.getLastRowNum(); i ++) {
				Row row = sheet.getRow(i);
				System.out.println("");
				List<Object> rowData = new ArrayList<>();
				if(row != null) {
					boolean emptyRow = true;
					for(int j = 0; j < row.getLastCellNum(); j ++) {
						Cell cell = row.getCell(j);
						String cellData = "";
						if(cell != null) {
							if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
								cellData = cell.getStringCellValue();
								System.out.print(cell.getStringCellValue());
							} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								cellData = Long.toString((long) cell.getNumericCellValue());
								System.out.print((long) cell.getNumericCellValue());
							}
						}
						if(!cellData.trim().isEmpty()) {
							emptyRow = false;
						}
						if(!emptyRow) {
							rowData.add(cellData);
						}
						System.out.print(" <--> ");
					}
				}
				if(!rowData.isEmpty()) {
					gridData.add(rowData);
				}
			}

		} catch (IOException | InvalidFormatException e) {
			e.printStackTrace();
		}

		return gridData;
	}

	public static List<List<Object>> readCSVAndTABDelimited(String filePath, boolean tabDelimited, boolean hasHeader) {
		List<List<Object>> gridData = new ArrayList<>();
		try {
			ICsvListReader listReader = null;
			try {
				listReader = new CsvListReader(new FileReader(filePath), tabDelimited ? CsvPreference.TAB_PREFERENCE : CsvPreference.STANDARD_PREFERENCE);
				if(hasHeader) {
					listReader.getHeader(true);
				}

				while( (listReader.read()) != null ) {
					// use different processors depending on the number of columns
					final CellProcessor[] processors = getProcessors(listReader.length());

					List<Object> rowData = listReader.executeProcessors(processors);
					if(!isEmptyOrNull(rowData)) {
						gridData.add(rowData);
					}
					System.out.println(String.format("lineNo=%s, rowNo=%s, columns=%s, customerList=%s",
							listReader.getLineNumber(), listReader.getRowNumber(), rowData.size(), rowData));
				}
			}
			finally {
				if( listReader != null ) {
					listReader.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gridData;
	}

	public static String uploadAddressingFile(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		boolean success = false;
		String fileLocation = "";
		String[][] addressingData = new String[0][];

		Map<String, Object> jsonResponse = new HashMap<>();
		try {
			jsonResponse = new HashMap<String, Object>();
			List<Map> pathList = new ArrayList<Map>();
			Map<String, Object> fileData = FileHelper.uploadFile(request, EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.PRODUCT_UPLOAD_DIR, false, false);
			if (UtilValidate.isNotEmpty(fileData) && UtilValidate.isNotEmpty(fileData.get("files"))) {
				List<Map> files = (List<Map>) fileData.get("files");
				for (Map file : files) {
					Iterator iter = file.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry pairs = (Map.Entry) iter.next();
						if ("path".equalsIgnoreCase((String) pairs.getKey())) {
							fileLocation = EnvConstantsUtil.OFBIZ_HOME + ((String) pairs.getValue());
							break;
						}
					}
				}
			}

			if(UtilValidate.isNotEmpty(fileLocation)) {
				addressingData = AddressingUtil.parseAddressFile(fileLocation, true);
				success = true;
			}
		} catch (Exception e) {
			Debug.logError(e, "Error: " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		jsonResponse.put("addressingData", addressingData);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static Map<String, Object> getNewVariableDataGroup(String attributeSet) {

		Map<String, Object> variableDataGroup = new HashMap<>();
		variableDataGroup.put("id", 0);
		variableDataGroup.put("name", getDefaultAddressBookName());
		variableDataGroup.put("variableData", "[[]]");
		if(!attributeSetMap.containsKey(attributeSet)) {
			attributeSet = DEFAULT_ATTRIBUTE_SET;
		}
		variableDataGroup.put("attributeSet", attributeSet);
		variableDataGroup.put("attributeNames", attributeSetMap.get(attributeSet));
		return variableDataGroup;
	}

	public static Map<String, Object> getVariableDataGroup(long groupId, Delegator delegator) {
		Map<String, Object> variableDataGroup = new HashMap<>();

		try {
			List<GenericValue> variableDataGroupGVs = EntityUtil.filterByDate(delegator.findByAnd("VariableDataGroup", UtilMisc.toMap("variableDataGroupId", groupId), null, false));

			if(UtilValidate.isNotEmpty(variableDataGroupGVs)) {
				GenericValue variableDataGroupGV = variableDataGroupGVs.get(0);
				variableDataGroup.put("id", variableDataGroupGV.getString("variableDataGroupId"));
				variableDataGroup.put("name", variableDataGroupGV.getString("name"));
				variableDataGroup.put("created", variableDataGroupGV.getString("createdStamp"));
				variableDataGroup.put("attributeSet", variableDataGroupGV.getString("attributeSet"));
				variableDataGroup.put("attributeNames", attributeSetMap.get(variableDataGroupGV.getString("attributeSet")));

				List<String[]> variableData = new ArrayList<>();
				List<GenericValue> variableDataGVs = delegator.findByAnd("VariableData", UtilMisc.toMap("variableDataGroupId", groupId), null, false);
				for(GenericValue variableDataGV : variableDataGVs) {
					variableData.add(new Gson().fromJson(variableDataGV.getString("data"), String[].class)
							/*UtilMisc.<String, Object>toMap(
									"sequenceNum", variableDataGV.getString("sequenceNum"),
									"data", new Gson().fromJson(variableDataGV.getString("data"), String[].class)
							)*/
					);
				}
				variableDataGroup.put("variableData", variableData.toArray(new String[][]{}));
			}
		} catch (GenericEntityException e) {
			e.printStackTrace();
		}


		return variableDataGroup;
	}

	public static String findVariableDataGroup(HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> variableDataGroup = new HashMap<>();
		if(context.containsKey("groupId")) {
			variableDataGroup = getVariableDataGroup(NumberUtils.toLong((String)context.get("groupId"), 0L), delegator);
		} else {
			variableDataGroup = getNewVariableDataGroup((String)context.get("attributeSet"));
		}
		success = true;

		Map<String, Object> jsonResponse = new HashMap<>();

		jsonResponse.put("variableDataGroup", variableDataGroup);
		jsonResponse.put("success", success);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	private static String getDefaultAddressBookName() {
		return DEFAULT_ADDRESS_BOOK_NAME + DATE_FORMAT.format(new Date());
	}

	public static String saveAddressBook(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		long variableDataGroupId     = (long) context.get("id");
		String variableDataGroupName = (String)context.get("name");
		String attributeSet          = (String)context.get("attributeSet");
		String[] attributes      = new Gson().fromJson((String)context.get("attributes"), String[].class);
		String[] variableDataIndices = new Gson().fromJson((String)context.get("variableDataIndices"), String[].class);
		String[][] variableData      = new Gson().fromJson((String)context.get("variableData"), String[][].class);


		success = true;
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	private static CellProcessor[] getProcessors(int columns) {
		CellProcessor[] processors = new CellProcessor[columns];
		for(int i = 0; i < columns; i ++) {
			processors[i] = new Optional();
		}
		return processors;
	}

	private static String[][] convertListToArray(List<List<Object>> listData) {
		String[][] gridArrayData = new String[listData.size()][];
		int i = 0;
		for (List<Object> listRowData : listData) {
			String[] rowArrayData = new String[listRowData.size()];
			int j = 0;
			for (Object columnData : listRowData) {
				rowArrayData[j++] = (String) columnData;
			}
			gridArrayData[i++] = rowArrayData;
		}
		return gridArrayData;
	}

	private static boolean isEmptyOrNull(List<Object> rowData) {
		for (Object columnData : rowData) {
			if(columnData != null) {
				if(columnData instanceof String) {
					if(!((String)columnData).trim().isEmpty()) {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return true;
	}
}
