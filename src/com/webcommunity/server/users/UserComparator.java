package com.webcommunity.server.users;

import java.util.Comparator;

import com.webcommunity.shared.users.UserEntry;

public class UserComparator implements Comparator<UserEntry> {
	
	@Override
	public int compare(UserEntry arg0, UserEntry arg1) {
		String address0 = combineAddressSplit(prependZeroes(splitAddres(arg0)));
		String address1 = combineAddressSplit(prependZeroes(splitAddres(arg1)));
		
		if (address0.toLowerCase().startsWith("søholm") && !address1.toLowerCase().startsWith("søholm")) {
			return -1;
		}

		if (!address0.toLowerCase().startsWith("søholm") && address1.toLowerCase().startsWith("søholm")) {
			return 1;
		}
		
		return address0.compareTo(address1);
	}
	
	private String[] splitAddres(UserEntry userEntry) {
		String address = userEntry.getAddress();
		return (address != null) ? address.split(" ") : new String[] {""};
	}
	
	private String[] prependZeroes(String[] addressSplit) {
		for (int i = 0; i < addressSplit.length; i++) {
			String number = addressSplit[i];
			if (isInteger(number)) {
				while (number.length() < 5) {
					number = "0" + number;
				}
			}
			addressSplit[i] = number;
		}

		return addressSplit;
	}
	
	private String combineAddressSplit(String[] addressSplit) {
		String address = "";
		for (int i = 0; i < addressSplit.length; i++) {
			address += addressSplit[i];
		}
		
		return address;
	}
	
	private boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (Exception ex){
		}
		
		return false;
	}
}
