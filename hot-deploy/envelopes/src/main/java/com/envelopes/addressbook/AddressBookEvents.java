/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.addressbook;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.envelopes.party.PartyHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.ofbiz.base.crypto.HashCrypt;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.GeneralException;
import org.apache.ofbiz.base.util.StringUtil;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntity;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityConditionList;
import org.apache.ofbiz.entity.condition.EntityExpr;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityFindOptions;
import org.apache.ofbiz.entity.util.EntityListIterator;
import org.apache.ofbiz.entity.util.EntityTypeUtil;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.comment.CommentStartsWith;

import com.envelopes.http.FileHelper;
import com.envelopes.util.*;

public class AddressBookEvents {
	public static final String module = AddressBookEvents.class.getName();

	/*
	 * Create addressbook
	 */
	public static String createAddressBook(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		String partyId = (UtilValidate.isNotEmpty(cart.getPartyId())) ? cart.getPartyId() : "Company";

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		try {
			String addressBookId = AddressBookHelper.createAddressBook(request, delegator, partyId);
			jsonResponse.put("success" , true);
			jsonResponse.put("id" , addressBookId);
		} catch (GenericEntityException e) {
			jsonResponse.put("success", false);
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to create address book. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Update address book
	 */
	public static String updateAddressBook(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		String partyId = (UtilValidate.isNotEmpty(cart.getPartyId())) ? cart.getPartyId() : "Company";

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		try {
			AddressBookHelper.updateAddressBook(delegator, (String) context.get("id"), (String) context.get("value"), partyId, request.getSession().getId());
			jsonResponse.put("success" , true);
		} catch (GenericEntityException e) {
			jsonResponse.put("success", false);
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to create address book. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Create addressbook
	 */
	public static String removeAddressBook(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		String partyId = (UtilValidate.isNotEmpty(cart.getPartyId())) ? cart.getPartyId() : "Company";

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		try {
			if(AddressBookHelper.removeAddressBook(delegator, (String) context.get("id"), partyId, request.getSession().getId())) {
				jsonResponse.put("success" , true);
			} else {
				jsonResponse.put("success", false);
			}
		} catch (GenericEntityException e) {
			jsonResponse.put("success", false);
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to create address book. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Create addressbook
	 */
	public static String addAddress(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		String partyId = (UtilValidate.isNotEmpty(cart.getPartyId())) ? cart.getPartyId() : "Company";

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		try {
			String addressId = AddressBookHelper.addAddress(delegator, (String) context.get("id"), partyId, request.getSession().getId());
			if(addressId != null) {
				jsonResponse.put("success" , true);
				jsonResponse.put("id" , addressId);
			} else {
				jsonResponse.put("success", false);
			}
		} catch (GenericEntityException e) {
			jsonResponse.put("success", false);
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to create address book. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String saveAddress(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		boolean success = false;

		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		Map<String, String> customerAddress = new HashMap<>();
		if(userLogin != null) {
			customerAddress = PartyHelper.saveAddressingData(request);

		}
		Map<String, Object> _customerAddress = new HashMap<>();
		_customerAddress.putAll(customerAddress);
		_customerAddress.put("jsonData", new Gson().toJson(customerAddress));
		jsonResponse.put("success", !customerAddress.isEmpty());
		jsonResponse.put("address", _customerAddress);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Remove addressbook
	 */
	public static String removeAddress(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		String partyId = (UtilValidate.isNotEmpty(cart.getPartyId())) ? cart.getPartyId() : "Company";

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		try {
			if(AddressBookHelper.removeAddress(delegator, (String) context.get("id"), partyId, request.getSession().getId())) {
				jsonResponse.put("success" , true);
			} else {
				jsonResponse.put("success", false);
			}
		} catch (GenericEntityException e) {
			jsonResponse.put("success", false);
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to create address book. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String removeAddress1(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		boolean success = false;
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		if(userLogin != null) {
			String partyId = userLogin.getString("partyId");
			Delegator delegator = (Delegator) request.getAttribute("delegator");
			String customerAddressId = request.getParameter("customerAddressId");
			success = AddressBookHelper.removeAddress(delegator, customerAddressId, partyId, null);
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Update addressbook
	 */
	public static String updateAddress(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		String partyId = (UtilValidate.isNotEmpty(cart.getPartyId())) ? cart.getPartyId() : "Company";

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		try {
			if(AddressBookHelper.updateAddress(delegator, (String) context.get("id"), (String) context.get("field"), (String) context.get("value"), partyId, request.getSession().getId())) {
				jsonResponse.put("success" , true);
			} else {
				jsonResponse.put("success", false);
			}
		} catch (GenericEntityException e) {
			jsonResponse.put("success", false);
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to create address book. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get all addressbooks
	 */
	public static String getAddressBooks(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		String partyId = (UtilValidate.isNotEmpty(cart.getPartyId())) ? cart.getPartyId() : "Company";

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		List<Map> addressList = new ArrayList<Map>();

		try {
			List<GenericValue> addressBookList = null;
			if(partyId.equals("Company")) {
				addressBookList = EntityUtil.filterByDate(delegator.findByAnd("CustomerAddressGroup", UtilMisc.toMap("sessionId", request.getSession().getId()), null, false));
			} else {
				addressBookList = EntityUtil.filterByDate(delegator.findByAnd("CustomerAddressGroup", UtilMisc.toMap("partyId", partyId), null, false));
			}

			for(GenericValue addressBook : addressBookList) {
				addressList.add(UtilMisc.<String, String>toMap(
					"id", addressBook.getString("customerAddressGroupId"),
					"name", addressBook.getString("name"),
					"created", addressBook.getString("createdStamp")
				));
			}

			if(UtilValidate.isNotEmpty(addressList)) {
				jsonResponse.put("success", true);
				jsonResponse.put("data", addressList);
			} else {
				jsonResponse.put("success", false);
			}
		} catch (GenericEntityException e) {
			jsonResponse.put("success", false);
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to create address book. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get All addresses in an addressbook
	 */
	public static String getAddresses(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		String partyId = (UtilValidate.isNotEmpty(cart.getPartyId())) ? cart.getPartyId() : "Company";

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		List<Map> addresses = new ArrayList<Map>();

		try {
			List<GenericValue> addressList = delegator.findByAnd("CustomerAddress", UtilMisc.toMap("customerAddressGroupId", (String) context.get("id")), null, false);
			for(GenericValue address : addressList) {
				Map<String, String> addressMap = new HashMap<String, String>();
				addressMap.put("id", address.getString("customerAddressId"));
				addressMap.put("name", address.getString("name"));
				addressMap.put("name2", address.getString("name2"));
				addressMap.put("address1", address.getString("address1"));
				addressMap.put("address2", address.getString("address2"));
				addressMap.put("city", address.getString("city"));
				addressMap.put("state", address.getString("state"));
				addressMap.put("zip", address.getString("zip"));
				addressMap.put("country", address.getString("country"));
				addresses.add(addressMap);
			}

			GenericValue addressBook = delegator.findOne("CustomerAddressGroup", UtilMisc.toMap("customerAddressGroupId", (String) context.get("id")), false);

			if(UtilValidate.isNotEmpty(addressBook)) {
				jsonResponse.put("success", true);
				jsonResponse.put("data", addresses);
				jsonResponse.put("name",  addressBook.getString("name"));
			} else {
				jsonResponse.put("success", false);
			}
		} catch (GenericEntityException e) {
			jsonResponse.put("success", false);
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to create address book. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Process address data from file upload
	 */
	public static String processAddressFile(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		String partyId = (UtilValidate.isNotEmpty(cart.getPartyId())) ? cart.getPartyId() : "Company";

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		String status = "OK";
		String addressGroupId = null;
		try {
			Map<String, Object> data = FileHelper.uploadFile(request, null, true, false);
			if(UtilValidate.isNotEmpty(data) && UtilValidate.isNotEmpty((List<Map>) data.get("files"))) {
				List<String> expectedColumns = Arrays.asList("Name Line 1", "Name Line 2", "Address Line 1", "Address Line 2", "City", "State", "Zip", "Country");
				List<String> databaseColumns = Arrays.asList("name", "name2", "address1", "address2", "city", "state", "zip", "country");

				addressGroupId = AddressBookHelper.createAddressBook(request, delegator, partyId);
				List<GenericValue> toBeStored = new LinkedList<GenericValue>();
				for(Map fileData : (List<Map>) data.get("files")) {
					FileItem item = (FileItem) fileData.get("fileitem");
					BufferedReader br = null;
					Reader reader = new InputStreamReader(item.getInputStream());
					br = new BufferedReader(reader);

					int firstDataRow = 3;

					ICsvMapReader mapReader = null;
					try {
						mapReader = new CsvMapReader(br, new CsvPreference.Builder(CsvPreference.STANDARD_PREFERENCE).skipComments(new CommentStartsWith("#")).build());

						// the header columns are used as the keys to the Map
						final String[] header = mapReader.getHeader(true);
						final CellProcessor[] processors = getProcessors();
						if(!StringUtils.join(expectedColumns.toArray(), "").equals(StringUtils.join(header, "")) ){
							status = "Invalid input file format\n\nExpected Columns\n "  + StringUtils.join(expectedColumns.toArray(), ", ") + "\nYour File\n"  + StringUtils.join(header, ", ");
							jsonResponse.put("success", false);
							break;
						}

						Map<String, Object> rowMap;
						Map<String, String> datarowMap = new HashMap<String, String>();
						datarowMap.put("customerAddressGroupId", addressGroupId);
						while( (rowMap = mapReader.read(header, processors)) != null ) {
							if(mapReader.getLineNumber() > firstDataRow){
								datarowMap.put("customerAddressId", delegator.getNextSeqId("CustomerAddress"));
								for(int j = 0; j < databaseColumns.size(); j++){
									if(rowMap.get(expectedColumns.get(j)) != null) {
										if(databaseColumns.get(j).equals("zip")) {
											if(((String)rowMap.get(expectedColumns.get(j))).length() < 5 &&
																										(
																											rowMap.get(expectedColumns.get(j+1)) == null ||
																											(rowMap.get(expectedColumns.get(j+1)) != null && (((String)rowMap.get(expectedColumns.get(j+1))).contains("United States") || ((String)rowMap.get(expectedColumns.get(j+1))).contains("USA") || ((String)rowMap.get(expectedColumns.get(j+1))).contains("U.S.A.")))
																										)) {
												datarowMap.put(databaseColumns.get(j), StringUtils.leftPad((String)rowMap.get(expectedColumns.get(j)), 5, "0"));
											} else {
												datarowMap.put(databaseColumns.get(j), (String)rowMap.get(expectedColumns.get(j)));
											}
										} else {
											datarowMap.put(databaseColumns.get(j), (String)rowMap.get(expectedColumns.get(j)));
										}
									} else {
										datarowMap.put(databaseColumns.get(j), "");
									}
								}
								toBeStored.add(delegator.makeValue("CustomerAddress", datarowMap));
							}
						}
						item.delete();
						delegator.storeAll(toBeStored);
					} finally {
						if(mapReader != null) {
							mapReader.close();
						}
					}
				}
			}
		} catch (Exception e) {
			jsonResponse.put("success", false);
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to create address book. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isNotEmpty(addressGroupId)) {
			jsonResponse.put("status", status);
			jsonResponse.put("id" , addressGroupId);
		} else {
			jsonResponse.put("status", status);
			jsonResponse.put("success", false);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	//download address book
	public static String downloadAddressBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		response.setContentType(EnvConstantsUtil.RESPONSE_CSV);
		response.setCharacterEncoding(EnvConstantsUtil.ENV_CHAR_ENCODE);

		PrintWriter output = null;

		final String[] header = new String[] { "Name Line 1","Name Line 2","Address Line 1","Address Line 2","City","State","Zip","Country" };

		String addressBookId = request.getParameter("id");
		ICsvMapWriter mapWriter = null;
		try {
			mapWriter = new CsvMapWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			final CellProcessor[] processors = getProcessors();

			// write the header
			mapWriter.writeHeader(new String[] { "#ENVELOPES.COM Addressing Template","","","","","","",""});
			mapWriter.writeHeader(new String[] { "#Please enter your addresses starting on line 4","","","","","","",""});
			mapWriter.writeHeader(header);

			// write data
			String addressBookNameString = delegator.findOne("CustomerAddressGroup", UtilMisc.toMap("customerAddressGroupId", addressBookId), false).getString("name");
			response.setHeader("Content-Disposition", "attachment; fileName="+addressBookNameString+".csv");

			List<GenericValue> addressesList = delegator.findByAnd("CustomerAddress", UtilMisc.toMap("customerAddressGroupId", addressBookId), null, false);
			for(GenericValue addressBook : addressesList) {
				Map<String, Object> csvMap = new HashMap<String, Object>();
				csvMap.put(header[0], addressBook.getString("name"));
				csvMap.put(header[1], addressBook.getString("name2"));
				csvMap.put(header[2], addressBook.getString("address1"));
				csvMap.put(header[3], addressBook.getString("address2"));
				csvMap.put(header[4], addressBook.getString("city"));
				csvMap.put(header[5], addressBook.getString("state"));
				csvMap.put(header[6], addressBook.getString("zip"));
				csvMap.put(header[7], addressBook.getString("country"));
				mapWriter.write(csvMap, header, processors);
			}
		} catch(GenericEntityException e) {
			Debug.logError(e, "Error trying load address book", module);
			EnvUtil.reportError(e);
		} finally {
			if(mapWriter != null) {
				mapWriter.close();
			}
		}

		return "succes";
	}

	//download address book
	public static String downloadNewAddressBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		String dataGroupId = request.getParameter("id");

		String format = "csv";
		if(UtilValidate.isNotEmpty(request.getParameter("format"))){
			format = "xlsx";
		}

		ICsvMapWriter mapWriter = null;
		XSSFWorkbook workBook = null;
		try {
			GenericValue variableDataGroup = delegator.findOne("VariableDataGroup", UtilMisc.toMap("variableDataGroupId", dataGroupId), false);

			String variableDataGroupName = variableDataGroup.getString("name");

			final String[] header = new Gson().fromJson(variableDataGroup.getString("attributes"), String[].class);

			List<GenericValue> variableDataList = delegator.findByAnd("VariableData", UtilMisc.toMap("variableDataGroupId", dataGroupId), Arrays.asList("sequenceNum"), false);

			if(format.equals("xlsx")) {
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=" + variableDataGroupName + ".xlsx");

				workBook = new XSSFWorkbook();
				XSSFSheet sheet = workBook.createSheet();
				int rowNum = sheet.getLastRowNum();
				Row row = sheet.createRow(rowNum++);
				int cellNum = 0;
				for(String headerCell : header) {
					org.apache.poi.ss.usermodel.Cell cell = row.createCell(cellNum++);
					cell.setCellValue(headerCell);
				}

				for (GenericValue variableData : variableDataList) {
					row = sheet.createRow(rowNum++);
					cellNum = 0;
					String[] rowData = new Gson().fromJson(variableData.getString("data"), String[].class);
					for (int i = 0; i < header.length; i++) {
						org.apache.poi.ss.usermodel.Cell cell = row.createCell(cellNum++);
						cell.setCellValue(rowData[i]);
					}
				}
				workBook.write(response.getOutputStream());

			} else {

				response.setContentType(EnvConstantsUtil.RESPONSE_CSV);
				response.setCharacterEncoding(EnvConstantsUtil.ENV_CHAR_ENCODE);

				mapWriter = new CsvMapWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
				final CellProcessor[] processors = getProcessors();

				// write the header
				mapWriter.writeHeader(header);

				// write data
				response.setHeader("Content-Disposition", "attachment; fileName=" + variableDataGroupName + ".csv");

				for (GenericValue variableData : variableDataList) {
					String[] rowData = new Gson().fromJson(variableData.getString("data"), String[].class);
					Map<String, Object> csvMap = new HashMap<String, Object>();
					for (int i = 0; i < header.length; i++) {
						csvMap.put(header[i], rowData[i]);
					}
					mapWriter.write(csvMap, header, processors);
				}

			}
		} catch (GenericEntityException e) {
			Debug.logError(e, "Error trying load new address book", module);
			EnvUtil.reportError(e);
		} finally {
			if (mapWriter != null) {
				mapWriter.close();
			}
		}
		return "success";
	}

	public static String getAddressBookIdByParent(Delegator delegator, String scene7DesignId) {
		String addressBookId = null;
		if(UtilValidate.isNotEmpty(scene7DesignId)) {
			try {
				GenericValue scene7Design = delegator.findOne("Scene7Design", UtilMisc.toMap("scene7DesignId", scene7DesignId), false);
				if(UtilValidate.isNotEmpty(scene7Design) && UtilValidate.isNotEmpty(scene7Design.getString("data"))) {
					HashMap<String, Object> jsonMap = new Gson().fromJson(scene7Design.getString("data"), HashMap.class);
					Map product = (Map) ((ArrayList) ((Map) jsonMap.get("settings")).get("product")).get(0);
					if(UtilValidate.isNotEmpty(product.get("addressingData"))) {
						addressBookId = Integer.toString(Double.valueOf((Double) ((Map)product.get("addressingData")).get("dataGroupId")).intValue());
					}
				}
			} catch (GenericEntityException e) {
				Debug.logError(e, "Error trying to retreive scene7 design data", module);
			} catch (Exception e) {
				Debug.logError(e, "Error trying to retreive scene7 design data", module);
			}
		}

		return addressBookId;
	}

	public static String getAddressBookId(Delegator delegator, String scene7DesignId, String side) {
		String addressBookId = null;
		if(UtilValidate.isNotEmpty(scene7DesignId)) {
			try {
				GenericValue scene7Design = delegator.findOne("Scene7Design", UtilMisc.toMap("scene7DesignId", scene7DesignId), false);
				if(UtilValidate.isNotEmpty(scene7Design) && UtilValidate.isNotEmpty(scene7Design.getString("data"))) {
					HashMap<String, Object> jsonMap = new Gson().fromJson(scene7Design.getString("data"), HashMap.class);
					addressBookId = (String) ((Map) ((Map) jsonMap.get("designs")).get(side)).get("addressBookId");
				}
			} catch (GenericEntityException e) {
				Debug.logError(e, "Error trying to retreive scene7 design data", module);
			} catch (Exception e) {
				Debug.logError(e, "Error trying to retreive scene7 design data", module);
			}
		}

		return addressBookId;
	}

	private static CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] {
				new Optional(), // name1
				new Optional(), // name2
				new Optional(), // address1
				new Optional(), // address2
				new Optional(), // city
				new Optional(), // state
				new Optional(), // zip
				new Optional() // country
		};
		return processors;
	}
}