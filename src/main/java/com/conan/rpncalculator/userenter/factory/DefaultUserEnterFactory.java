package com.conan.rpncalculator.userenter.factory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

import com.conan.rpncalculator.userenter.UserEnter;
import com.conan.rpncalculator.userenter.UserEntry;
import org.apache.commons.lang3.StringUtils;

import com.conan.rpncalculator.userenter.model.DigitalUserEntry;

public class DefaultUserEnterFactory implements UserEnter {

	private static final String CTRL_C = "\u0003";
	private static final String REGEX_DIGIT_PATTERN = "^-*\\d+$";

	private Scanner scanner;

	public DefaultUserEnterFactory() {
		this(System.in);
	}

	public DefaultUserEnterFactory(InputStream in) {
		this.scanner = new Scanner(in);
	}

	@Override
	public List<UserEntry> getUserInput() {
		List<UserEntry> userEntries = new ArrayList<>();
		//用户交互
		String userEntered = scanner.nextLine();
		if (CTRL_C.equalsIgnoreCase(userEntered))
			throw new NoSuchElementException("No line found");
		
		if (StringUtils.isNoneBlank(userEntered)) {
			String[] strings = userEntered.split(UserEnter.SPACE);
			for (String string : strings) {
				Optional<UserEntry> userEntry = this.constructUserEntry(string);
				if (userEntry.isPresent()) {
					userEntries.add(userEntry.get());
				}
			}
		}
		return userEntries;
	}

	public Optional<UserEntry> constructUserEntry(String userEntered) {
		Optional<UserEntry> userEntry = Optional.empty();
		
		if (StringUtils.isNotBlank(userEntered)) {
			if (userEntered.matches(REGEX_DIGIT_PATTERN)) {
				userEntry = getDigitalUserEntry(userEntered);
			}
			else {
				userEntry = getOperatorUserEntry(userEntered);
			}
		}
		return userEntry;
	}

	protected Optional<UserEntry> getOperatorUserEntry(String userEntered) {
		Optional<UserEntry> userEntry = Optional.empty();
		
		try {
			userEntry = OperatorFactory.getOperator(userEntered);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return userEntry;
	}

	protected Optional<UserEntry> getDigitalUserEntry(final String userEntered) {
		Optional<UserEntry> userEntry = Optional.empty();
		
		try {
			userEntry = Optional.of(new DigitalUserEntry(userEntered));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return userEntry;
	}

	public Scanner getScanner() {
		return scanner;
	}

}
