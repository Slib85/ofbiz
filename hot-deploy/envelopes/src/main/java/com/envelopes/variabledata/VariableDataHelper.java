package com.envelopes.variabledata;

import com.envelopes.session.SessionHelper;
import com.envelopes.util.EnvUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.model.ModelReader;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
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
 * Created by Manu on 11/27/2015.
 */
public class VariableDataHelper {
	public static final String module = VariableDataHelper.class.getName();

	public static final String DEFAULT_ATTRIBUTE_SET = "address";

	public static final int MAX_COLUMNS_ALLOWED = 100;

	public static final Map<String, String[]> attributeSetMap = new HashMap<>();

	static {
		attributeSetMap.put(DEFAULT_ATTRIBUTE_SET, new String [] {"Name Line 1", "Name Line 2", "Address Line 1", "Address Line 2", "City", "State", "Zip", "Country"});
		attributeSetMap.put("tableCard", new String [] {"Table Number", "Name"});
	}

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyMMddHHmmss");

	public static final Map<String, String> defaultNameMap = new HashMap<>();

	static {
		defaultNameMap.put(DEFAULT_ATTRIBUTE_SET, "AddressBook-");
		defaultNameMap.put("tableCard", "TableCards-");
	}

	protected static Map<String, String> getVariableDataGroupNames(List<String> sessionIds, Delegator delegator) throws GenericEntityException {
		Map<String, String> dataGroupNameMap  = new LinkedHashMap<>();

		List<EntityCondition> conditions = new ArrayList<EntityCondition>();
		conditions.add(EntityCondition.makeCondition("sessionId", EntityOperator.IN, sessionIds));

		List<GenericValue> variableDataGroupGVs = EntityUtil.filterByDate(delegator.findList("VariableDataGroup", EntityCondition.makeCondition(conditions), null, UtilMisc.toList("name ASC"), null, false));

		for(GenericValue variableDataGroupGV : variableDataGroupGVs) {
			dataGroupNameMap.put(variableDataGroupGV.getString("variableDataGroupId"), variableDataGroupGV.getString("name"));
		}
		return dataGroupNameMap;
	}

	public static Map<String, String> getVariableDataGroupNames(HttpServletRequest request) throws GenericEntityException {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		String partyId = (String) context.get("partyId");
		String clientId = (String) context.get("clientId");
		String sessionId = (String) context.get("sessionId");
		List <String> sessionIds = new ArrayList<>();
		if(UtilValidate.isEmpty(partyId)) {
			sessionIds.add(sessionId);
		} else {
			try {
				List<GenericValue> sessionGVs = delegator.findByAnd("EnvSession", UtilMisc.toMap("clientId", clientId, "partyId", partyId), null, false);
				for(GenericValue sessionGV : sessionGVs) {
					sessionIds.add(sessionGV.getString("sessionId"));
				}
			} catch (GenericEntityException e) {
				sessionIds.add(sessionId);
				e.printStackTrace();
			}
		}
		return getVariableDataGroupNames(sessionIds, delegator);
	}

	public static String[][] parseVariableDataFile(String filePath, boolean hasHeader) throws IOException {
		String ext = FilenameUtils.getExtension(filePath);
		String[][] data = new String[0][];
		if(ext.equalsIgnoreCase("xls") || ext.equalsIgnoreCase("xlsx")) {
			data = convertListToArray(readXLSAndXLSX(filePath, hasHeader));
		} else if(ext.equalsIgnoreCase("csv") || ext.equalsIgnoreCase("txt")) {
			data = convertListToArray(readCSVAndTABDelimited(filePath, ext.equalsIgnoreCase("txt"), hasHeader));
		}
		return data;
	}

	protected static boolean hasDefaultTemplate(Row headerRow) {
		String[] defaultColumnHeaders = attributeSetMap.get(DEFAULT_ATTRIBUTE_SET);
		if(headerRow.getLastCellNum() != defaultColumnHeaders.length) {
			return false;
		}
		for(int i = 0; i < headerRow.getLastCellNum(); i ++) {
			Cell cell = headerRow.getCell(i);
			String cellData = "";
			if(cell != null) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					cellData = cell.getStringCellValue();
				} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					cellData = Long.toString((long) cell.getNumericCellValue());
				}
			}
			if(cellData.trim().isEmpty() || !cellData.trim().equals(defaultColumnHeaders[i])) {
				return false;
			}
		}
		return true;
	}

	protected static boolean hasDefaultTemplate(String[] headerRow) {
		String[] defaultColumnHeaders = attributeSetMap.get(DEFAULT_ATTRIBUTE_SET);
		if(headerRow.length != defaultColumnHeaders.length) {
			return false;
		}
		for(int i = 0; i < headerRow.length; i ++) {
			String cellData = headerRow[i];
			if(cellData.trim().isEmpty() || !cellData.trim().equals(defaultColumnHeaders[i])) {
				return false;
			}
		}
		return true;
	}

	public static List<List<Object>> readXLSAndXLSX(String filePath, boolean hasHeader) {
		String[] defaultColumnHeaders = attributeSetMap.get(DEFAULT_ATTRIBUTE_SET);
		List<List<Object>> gridData = new ArrayList<>();
		try {
			Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(filePath)));
			Sheet sheet = workbook.getSheetAt(0);

			for(int i = 0; i <= sheet.getLastRowNum(); i ++) {
				Row row = sheet.getRow(i);
				List<Object> rowData = new ArrayList<>();
				if (row != null) {
					boolean emptyRow = true;
					boolean hasDefaultTemplate = i == 0 && hasHeader && hasDefaultTemplate(row);
					for (int j = 0; j < row.getLastCellNum(); j++) {
						Cell cell = row.getCell(j);
						String cellData = "";
						if (cell != null) {
							if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
								cellData = cell.getStringCellValue();
							} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								cellData = Long.toString((long) cell.getNumericCellValue());
							}
						}
						if (!cellData.trim().isEmpty()) {
							emptyRow = false;
						}
						if(i == 0) {
							emptyRow = false;
							if(hasDefaultTemplate && !cellData.trim().isEmpty() && cellData.trim().equals(defaultColumnHeaders[j])) {
								rowData.add(Integer.toString(j));
							} else {
								rowData.add("-1");
							}
						} else {
							if (!emptyRow) {
								rowData.add(cellData);
							}
						}
					}
				}

				if (!rowData.isEmpty()) {
					gridData.add(rowData);
				}
			}

		} catch (IOException | InvalidFormatException e) {
			e.printStackTrace();
		}

		return gridData;
	}

	public static List<List<Object>> readCSVAndTABDelimited(String filePath, boolean tabDelimited, boolean hasHeader) throws IOException {
		List<List<Object>> gridData = new ArrayList<>();
		ICsvListReader listReader = null;
		try {
			listReader = new CsvListReader(new FileReader(filePath), tabDelimited ? CsvPreference.TAB_PREFERENCE : CsvPreference.STANDARD_PREFERENCE);
			boolean hasDefaultTemplate = false;
			if(hasHeader) {
				String[] defaultColumnHeaders = attributeSetMap.get(DEFAULT_ATTRIBUTE_SET);
				List<Object> rowData = new ArrayList<>();
				String[] headerColumns = listReader.getHeader(true);
				hasDefaultTemplate = hasDefaultTemplate(headerColumns);
				for(int i = 0; i < headerColumns.length; i ++) {
					if (hasDefaultTemplate && !headerColumns[i].trim().isEmpty() && headerColumns[i].trim().equals(defaultColumnHeaders[i])) {
						rowData.add(Integer.toString(i));
					} else {
						rowData.add("-1");
					}
				}
				gridData.add(rowData);
			}

			while( (listReader.read()) != null ) {
				// use different processors depending on the number of columns
				final CellProcessor[] processors = getProcessors(listReader.length());

				List<Object> rowData = listReader.executeProcessors(processors);
				if(!isEmptyOrNull(rowData)) {
					gridData.add(rowData);
				}
			}
		}
		finally {
			if( listReader != null ) {
				listReader.close();
			}
		}
		return gridData;
	}

	public static Map<String, Object> getNewVariableDataGroup(String attributeSet) {
		Map<String, Object> variableDataGroup = new HashMap<>();

		if(!attributeSetMap.containsKey(attributeSet)) {
			attributeSet = DEFAULT_ATTRIBUTE_SET;
		}

		variableDataGroup.put("id", 0);
		variableDataGroup.put("name", getDefaultAddressBookName(attributeSet));
		variableDataGroup.put("variableData", "[[]]");
		variableDataGroup.put("attributeSet", attributeSet);
		variableDataGroup.put("attributes", attributeSetMap.get(attributeSet));
		return variableDataGroup;
	}

	public static Map<String, Object> getVariableDataGroup(String groupId, Delegator delegator) throws GenericEntityException {
		Map<String, Object> variableDataGroup = new HashMap<>();

		List<GenericValue> variableDataGroupGVs = EntityUtil.filterByDate(delegator.findByAnd("VariableDataGroup", UtilMisc.toMap("variableDataGroupId", groupId), null, false));

		if(UtilValidate.isNotEmpty(variableDataGroupGVs)) {
			GenericValue variableDataGroupGV = variableDataGroupGVs.get(0);
			variableDataGroup.put("id", variableDataGroupGV.getString("variableDataGroupId"));
			variableDataGroup.put("name", variableDataGroupGV.getString("name"));
			variableDataGroup.put("created", variableDataGroupGV.getString("createdStamp"));
			variableDataGroup.put("attributeSet", variableDataGroupGV.getString("attributeSet"));
			String attributeSet = variableDataGroupGV.getString("attributeSet");
			variableDataGroup.put("attributes", attributeSetMap.containsKey(attributeSet) ? attributeSetMap.get(attributeSet) : new String[0]);

			List<String[]> variableData = new ArrayList<>();
			List<GenericValue> variableDataGVs = delegator.findByAnd("VariableData", UtilMisc.toMap("variableDataGroupId", groupId), Arrays.asList("sequenceNum"), false);
			for(GenericValue variableDataGV : variableDataGVs) {
				String[] data = new Gson().fromJson(variableDataGV.getString("data"), String[].class);
				data[0] = variableDataGV.getString("variableDataId") + "::rowid::" + data[0];
				variableData.add(data);
			}
			variableDataGroup.put("rowData", variableData.toArray(new String[][]{}));
		}

		return variableDataGroup;
	}

	public static Map<String, Object> removeVariableData(HttpServletRequest request) throws GenericEntityException {
		Map<String, Object> context  = EnvUtil.getParameterMap(request);
		String variableDataGroupId   = (String)context.get("variableDataGroupId");
		String variableDataId   = (String)context.get("variableDataId");
		String partyId = (String)context.get("partyId");
		String sessionId = (String)context.get("sessionId");

		//TODO - check permission before deleting the variable data record

		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue variableData = delegator.findOne("VariableData", UtilMisc.toMap("variableDataId", variableDataId), false);
		variableData.remove();
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("success", true);
		return returnMap;
	}

	public static Map<String, Object> saveVariableDataGroup(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
		Map<String, Object> context  = EnvUtil.getParameterMap(request);
		String variableDataGroupId   = (String)context.get("id");
		String partyId				 = (String)context.get("partyId");
		String variableDataGroupName = (String)context.get("name");
		String attributeSet          = (String)context.get("attributeSet");
		String attributes            = (String)context.get("attributes");
		String sessionId			 = (String)context.get("sessionId");
		Map<String, Long> variableDataIds = new HashMap<>();


		Delegator delegator = (Delegator) request.getAttribute("delegator");

		if(NumberUtils.toLong((String)context.get("id"), 0L) == 0) {
			variableDataGroupId = delegator.getNextSeqId("VariableDataGroup");
			Map<String, Object> variableDataGroup = UtilMisc.<String, Object>toMap("variableDataGroupId", variableDataGroupId);
			variableDataGroup.put("name", variableDataGroupName);
			variableDataGroup.put("attributeSet", attributeSet);
			variableDataGroup.put("attributes", attributes);
			variableDataGroup.put("sessionId", sessionId);
			variableDataGroup.put("fromDate", UtilDateTime.nowTimestamp());

			GenericValue variableDataGroupGV = delegator.makeValue("VariableDataGroup", variableDataGroup);
			delegator.create(variableDataGroupGV);
			context.put("mode", "add");
		} else {
			GenericValue variableDataGroupGV = delegator.findOne("VariableDataGroup", UtilMisc.toMap("variableDataGroupId", variableDataGroupId), false);
			if(variableDataGroupGV != null) {
				boolean variableDataGroupModified = false;
				if(context.containsKey("name")) {
					variableDataGroupGV.set("name", variableDataGroupName);
					variableDataGroupModified = true;
				}
				if(!variableDataGroupGV.getString("attributeSet").equals(attributeSet)) {
					variableDataGroupGV.set("attributeSet", attributeSet);
					variableDataGroupModified = true;
				}
				if(!variableDataGroupGV.getString("attributes").equals(attributes)) {
					variableDataGroupGV.set("attributes", attributes);
					variableDataGroupModified = true;
				}
				if(variableDataGroupModified) {
					variableDataGroupGV.store();
				}
				context.put("mode", "update");
			}
		}

		if(NumberUtils.toLong(variableDataGroupId, 0L) > 0 && context.containsKey("mode")) {
			context.put("dataGroupId", variableDataGroupId);
			variableDataIds = saveVariableData(variableDataGroupId, context, delegator);
		}

		if(UtilValidate.isNotEmpty(partyId)) {
			saveDataGroupForLater(request, response);
		}

		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("dataGroupId", NumberUtils.toLong(variableDataGroupId, 0L));
		returnMap.put("rowDataIds", variableDataIds);

		return returnMap;
	}

	protected static Map<String, Long> saveVariableData(String variableDataGroupId, Map<String, Object> context, Delegator delegator) throws GenericEntityException {
		Map<String, Long> variableDataIds = new HashMap<>();
		String[] variableDataSequenceNumArray = new Gson().fromJson((String)context.get("rowDataIndices"), String[].class);
		String[][] variableDataArray      = new Gson().fromJson((String)context.get("rowData"), String[][].class);

		if((variableDataSequenceNumArray.length == 0 && variableDataArray.length > 0) || (variableDataSequenceNumArray.length > 0 && variableDataSequenceNumArray.length == variableDataArray.length)) {

			for(int i = 0; i < variableDataArray.length; i ++) {
				String variableDataId = "0";
				try {
					String idCell = variableDataArray[i][0];

					if(idCell.contains("::rowid::")) {
						variableDataArray[i][0] = idCell.substring(idCell.indexOf("::rowid::") + "::rowid::".length());
						variableDataId = idCell.substring(0, idCell.indexOf("::rowid::"));
					}
					String variableDataStr = stringifyDataArray(variableDataArray[i]);
					GenericValue variableDataGV = null;
					if(UtilValidate.isNotEmpty(variableDataId) && !variableDataId.equals("0")) {
						variableDataGV = delegator.findOne("VariableData", UtilMisc.toMap("variableDataId", variableDataId), false);
					}

					if(variableDataGV == null) {
						variableDataGV = EntityUtil.getFirst(delegator.findByAnd("VariableData", UtilMisc.toMap("variableDataGroupId", variableDataGroupId, "sequenceNum", new Long(variableDataSequenceNumArray[i])), null, false));
					}

					if(variableDataGV == null) {
						variableDataId = delegator.getNextSeqId("VariableData");
						Map<String, Object> variableData = UtilMisc.<String, Object>toMap("variableDataId", variableDataId, "variableDataGroupId", variableDataGroupId);
						variableData.put("sequenceNum", new Long(variableDataSequenceNumArray[i]));
						variableData.put("data", variableDataStr);
						delegator.create(delegator.makeValue("VariableData", variableData));
					} else {
						variableDataGV.put("sequenceNum", new Long(variableDataSequenceNumArray[i]));
						variableDataGV.set("data", variableDataStr);
						variableDataGV.store();
					}
				} catch (Exception e) {
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to save variable data. " + e + " : " + e.getMessage(), module);
					variableDataId = "0";
				}
				variableDataIds.put(variableDataId, new Long(variableDataSequenceNumArray[i]));
			}
		}
		return variableDataIds;
	}

	protected static String stringifyDataArray(String[] dataArray) {
		for(int i = 0; i < dataArray.length; i ++) {
			dataArray[i] = dataArray[i].trim();
		}
		return new Gson().toJson(dataArray);
	}

	public static boolean saveDataGroupForLater(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
		return SessionHelper.setSession(request, response);
	}



	private static CellProcessor[] getProcessors(int columns) {
		CellProcessor[] processors = new CellProcessor[columns];
		for(int i = 0; i < columns; i ++) {
			processors[i] = new Optional();
		}
		return processors;
	}

	private static String getDefaultAddressBookName(String attributeSet) {
		return defaultNameMap.get(attributeSet) + DATE_FORMAT.format(new Date());
	}

	private static String[][] convertListToArray(List<List<Object>> listData) {
		int columnSize = findNonEmptyColumnSize(listData);
		String[][] gridArrayData = new String[listData.size()][];
		int i = 0;
		for (List<Object> listRowData : listData) {
			String[] rowArrayData = new String[columnSize];
			for (int j = 0; j < columnSize; j ++) {
				if(j < listRowData.size()) {
					rowArrayData[j] = (String) listRowData.get(j);
				} else {
					rowArrayData[j] = "";
				}
			}
			gridArrayData[i++] = rowArrayData;
		}
		return gridArrayData;
	}

	private static int findNonEmptyColumnSize(List<List<Object>> listData) {
		int size = 0;

		for (int i = 1; i <listData.size(); i ++) {
			int emptyCells = 0;
			List<Object> listRowData = listData.get(i);
			int numOfNonEmptyColumns = 0;
			for (Object columnData : listRowData) {
				if(UtilValidate.isNotEmpty(columnData)) {
					numOfNonEmptyColumns  = numOfNonEmptyColumns  + 1 + emptyCells;
					emptyCells = 0;
				} else {
					emptyCells ++;
				}
			}
			if(numOfNonEmptyColumns > size) {
				size = numOfNonEmptyColumns;
			}
		}
		return size;
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
