package com.envelopes.addressbook;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Manu on 11/24/2015.
 */
public class AddressBookUtil {
	public static final String module = AddressBookUtil.class.getName();

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyMMddHHmmss");

	public static final String DEFAULT_ADDRESS_BOOK_NAME = "AddressBook-";

	static String getDefaultAddressBookName() {
		return DEFAULT_ADDRESS_BOOK_NAME + DATE_FORMAT.format(new Date());
	}

	static AddressBook getAddressBook(long addressBookId) {
		return null;
	}
}
