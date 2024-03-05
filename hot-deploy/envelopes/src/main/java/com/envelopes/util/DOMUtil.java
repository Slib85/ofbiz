package com.envelopes.util;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;

/**
 * Created by Manu on 4/23/2015.
 */
public class DOMUtil {

	public static String getStringValue(String elementName, Document xml, String... defaultValue) {

		String _defaultValue = defaultValue != null && defaultValue.length > 0 ? defaultValue[0] : null;
		NodeList nodeList = null;
		return ((nodeList = xml.getElementsByTagName(elementName)) != null && nodeList.getLength() > 0) ? nodeList.item(0).getTextContent() : _defaultValue;
	}

	public static boolean getBooleanValue(String elementName, Document xml, boolean... defaultValue) {
		boolean _defaultValue = defaultValue != null && defaultValue.length > 0 && defaultValue[0];
		return Boolean.parseBoolean(getStringValue(elementName, xml, Boolean.toString(_defaultValue)));
	}

	public static BigDecimal getBigDecimalValue(String elementName, Document xml, BigDecimal... defaultValue) {
		BigDecimal _defaultValue = defaultValue != null && defaultValue.length > 0 ? defaultValue[0] : BigDecimal.ZERO;
		return new BigDecimal(getStringValue(elementName, xml, _defaultValue.toString()));
	}
}
