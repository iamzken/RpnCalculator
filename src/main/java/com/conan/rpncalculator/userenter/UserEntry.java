package com.conan.rpncalculator.userenter;

import com.conan.rpncalculator.storage.Storage;

public interface UserEntry {

	int DECIMAL_PLACES = 15;

	void execute(Storage storage);
	
	String getEmptyStackErrorMessage(final int counter);
}
