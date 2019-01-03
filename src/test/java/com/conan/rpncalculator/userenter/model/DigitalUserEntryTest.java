package com.conan.rpncalculator.userenter.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.List;

import com.conan.rpncalculator.RpnCalculator;
import com.conan.rpncalculator.history.record.OperationRecord;
import com.conan.rpncalculator.storage.Storage;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;


public class DigitalUserEntryTest {


	@Test
	public void whenADigitalStringProvidedThanADigitalUserEntryObjectShouldConstruct() {
		String userEntered = "123";
		DigitalUserEntry userEntry = new DigitalUserEntry(userEntered);
		assertNotNull(userEntry);
	}

	@Test
	public void whenAnInvalidStringprovidedThenAnExceptionRaised() {
		String userEntered = "abc";
		try {
			new DigitalUserEntry(userEntered);
			fail("Program reached unexpected point!");
		} catch (Exception e) {
			assertTrue(e instanceof NumberFormatException);
		}
	}

	@Test
	public void whenAnEmptyStringprovidedThenAnExceptionRaised() {
		String userEntered = "";
		try {
			new DigitalUserEntry(userEntered);
			fail("Program reached unexpected point!");
		} catch (Exception e) {
			assertTrue(e instanceof NumberFormatException);
		}
	}

	@Test
	public void whenANullStringprovidedThenAnExceptionRaised() {
		String userEntered = null;
		try {
			new DigitalUserEntry(userEntered);
			fail("Program reached unexpected point!");
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
		}
	}
	
	@Test
	public void whenMockCalculatorProvidedThenThePushMethodOfStackCalled() throws Exception {
		RpnCalculator mockCalculator = PowerMockito.mock(RpnCalculator.class);
		Storage mockStorage = PowerMockito.mock(Storage.class);
		Whitebox.setInternalState(mockCalculator, "storage", mockStorage);
	
		DigitalUserEntry testInstance = new DigitalUserEntry("6");
		
		testInstance.execute(mockStorage);
		ArgumentCaptor<BigDecimal> digitCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockStorage);
		mockStorage.pushDigit(digitCaptor.capture());
		List<BigDecimal> allValues = digitCaptor.getAllValues();
		assertNotNull(allValues);
		assertTrue(1 == allValues.size());
		BigDecimal retrieve = allValues.get(0);
		assertNotNull(retrieve);
		assertEquals(new BigDecimal(6), retrieve);
	}
	
	@Test
	public void whenBigDecimalProvidedThenAnOperationRecordShouldReturn() {
		BigDecimal  digit = BigDecimal.ONE;
		OperationRecord record = DigitalUserEntry.toOperationRecord().apply(digit);
		assertNotNull(record);
		assertEquals(digit, record.getParameters().get(0));
		assertNull(record.getOperator());
	}
}
