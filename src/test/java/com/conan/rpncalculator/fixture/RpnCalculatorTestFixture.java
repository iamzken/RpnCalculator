package com.conan.rpncalculator.fixture;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.conan.rpncalculator.storage.Storage;
import org.powermock.api.mockito.PowerMockito;

public class RpnCalculatorTestFixture {

	public static InputStream givenByteArrayInputStream(String input) {
		InputStream in = new ByteArrayInputStream(input.getBytes());
		return in;
	}

	public static List<BigDecimal> getOperationParameters() {
		return Arrays.asList(new BigDecimal(6));
	}

	public static List<BigDecimal> get2OperationParameters() {
		return Arrays.asList(new BigDecimal(6), new BigDecimal(2));
	}

	public static Storage givenMockStorage() {
		Storage mockStorage = PowerMockito.mock(Storage.class);
		PowerMockito.when(mockStorage.popDigit()).thenReturn(new BigDecimal(2), new BigDecimal(6));
		PowerMockito.when(mockStorage.getDigitsStackSize()).thenReturn(2);
		return mockStorage;
	}
}
