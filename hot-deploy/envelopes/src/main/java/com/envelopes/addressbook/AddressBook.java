package com.envelopes.addressbook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manu on 11/24/2015.
 */
public class AddressBook {
	private long addressBookId = 0;
	private String addressBookName;
	private List<List<String>> addressingData = new ArrayList<>();

	public AddressBook() {
		this.addressBookName = AddressBookUtil.getDefaultAddressBookName();
	}

	public AddressBook(long addressBookId) {

	}



}
