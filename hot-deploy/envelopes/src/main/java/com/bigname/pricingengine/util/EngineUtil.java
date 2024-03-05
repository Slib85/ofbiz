package com.bigname.pricingengine.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class EngineUtil {

    public static int[] toIntArray(String value) {
        if(isEmpty(value)) {
            return new int[0];
        }
        String[] valueArray = value.split("\\|");
        int[] intArray = new int[valueArray.length];
        for(int i = 0; i < valueArray.length; i ++) {
            intArray[i] = Integer.parseInt(valueArray[i]);
        }
        return intArray;
    }

    public static BigDecimal[] toBigDecimalArray(String value) {
        if(isEmpty(value)) {
            return new BigDecimal[0];
        }
        String[] valueArray = value.split("\\|");
        BigDecimal[] bigDecimalArray = new BigDecimal[valueArray.length];
        for(int i = 0; i < valueArray.length; i ++) {
            bigDecimalArray[i] = new BigDecimal(valueArray[i]);
        }
        return bigDecimalArray;
    }

    public static String[] validateQuantityBreakValues(String qtyBreaks, String values) {
        if(isEmpty(qtyBreaks)) {
            return new String[] {"", ""};
        }
        String[] _qtyBreaks = qtyBreaks.split("\\|");
        String[] _values = !EngineUtil.isEmpty(values) ? values.split("\\|") : new String[0];

        qtyBreaks = "";
        values = "";

        for(int i = 0; i < _qtyBreaks.length; i++) {
            qtyBreaks += (isEmpty(qtyBreaks) ? "" : "|") + _qtyBreaks[i];
            BigDecimal value;
            if(_values.length > i) {
                value = convertToBigDecimal(_values[i]);
            } else {
                value = BigDecimal.ZERO;
            }
            values += (isEmpty(values) ? "" : "|") + value.setScale(2, BigDecimal.ROUND_UP).toString();
        }

        return new String[] {qtyBreaks, values};
    }

    public static BigDecimal convertToBigDecimal(String value, BigDecimal... defaultValue) {
        BigDecimal convertedValue = null;
        try {
            convertedValue = new BigDecimal(value.trim());
        } catch(Exception e) {}

        return isEmpty(convertedValue) ? !isEmpty(defaultValue) ? defaultValue[0] : BigDecimal.ZERO : convertedValue;

    }

    public static boolean isEmpty(Object value, boolean... trim) {
        if (value == null) return true;

        if (value instanceof String) return trim!= null && trim.length > 0 && trim[0] ? ((String) value).trim().length() == 0 : ((String) value).length() == 0;
        if (value.getClass().isArray()) return ((Object[])value).length == 0;
        if (value instanceof Collection) return ((Collection<? extends Object>) value).size() == 0;
        if (value instanceof Map) return ((Map<? extends Object, ? extends Object>) value).size() == 0;
        if (value instanceof CharSequence) return ((CharSequence) value).length() == 0;
        if (value instanceof Boolean) return false;
        if (value instanceof Number) return false;
        if (value instanceof Character) return false;
        if (value instanceof java.util.Date) return false;


        return false;
    }

    public static List<Map<String, String>> parseDataFile(String filePath) throws IOException {
        String ext = FilenameUtils.getExtension(filePath);
        List<Map<String, String>> data = new ArrayList<>();
        if(ext.equalsIgnoreCase("xls") || ext.equalsIgnoreCase("xlsx")) {
            data = readXLSAndXLSX(filePath);
        }
        return data;
    }

    public static List<Map<String, String>> readXLSAndXLSX(String filePath) {
        final Map<Integer, String> headerColumns = new LinkedHashMap<>();
        List<Map<String, String>> gridData = new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(filePath)));
            Sheet sheet = workbook.getSheetAt(0);

            for(int i = 0; i <= sheet.getLastRowNum(); i ++) {
                Row row = sheet.getRow(i);
                Map<String, String> rowData = new LinkedHashMap<>();
                if (row != null) {
                    boolean emptyRow = true;
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
                        if (!emptyRow) {
                            if(i == 0) {
                                headerColumns.put(j, cellData.toUpperCase());
                            } else if(headerColumns.containsKey(j)){
                                rowData.put(headerColumns.get(j), cellData);
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
}
