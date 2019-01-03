package com.conan.rpncalculator;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import com.conan.rpncalculator.history.record.OperationRecord;
import com.conan.rpncalculator.storage.Storage;
import com.conan.rpncalculator.userenter.UserEnter;
import com.conan.rpncalculator.userenter.UserEntry;
import com.conan.rpncalculator.userenter.model.DigitalUserEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import com.conan.rpncalculator.userenter.operator.Addition;
import com.conan.rpncalculator.userenter.operator.Clear;
import com.conan.rpncalculator.userenter.operator.Division;
import com.conan.rpncalculator.userenter.operator.Multiplication;
import com.conan.rpncalculator.userenter.operator.SquareRoot;
import com.conan.rpncalculator.userenter.operator.Subtraction;
import com.conan.rpncalculator.userenter.operator.Undo;

public class RpnCalculatorTest {

	private static final String TEST_MESSAGE = "+ (position: 1 \"): insufficient parameters";
	private RpnCalculator testInstance;
	
	@Before
	public void setUp() {
		this.testInstance = new RpnCalculator();
	}
	
	@After
	public void tearDown() {
		this.testInstance = null;
	}
	
	@Test
	public void whenNullInputStreamProvidedThenAnIllegalArgumentExceptionRaised() {
		InputStream in = null;
		try {
			new RpnCalculator(in);
		}
		catch (IllegalArgumentException e) {
			String message = e.getMessage();
			assertNotNull(message);
			assertEquals("InputStream cannot be null!", message);
		}
	}
	
	@Test
	public void whenAnInputStreamProvidedThenARpnCalculatorObjectShouldReturn() throws Throwable {
		InputStream in = new FileInputStream("src/test/resources/log4j.properties");
		RpnCalculator rpnCalculator = new RpnCalculator(in);
		assertNotNull(rpnCalculator);
	}
	
	@Test
	public void whenNoInputStreamProvidedThenARpnCalculatorObjectShouldReturn() throws Throwable {
		RpnCalculator rpnCalculator = new RpnCalculator();
		assertNotNull(rpnCalculator);
	}
	
	@Test
	public void whenAdditionUserEntryProvidedThenPlusSignShouldReturn() {
		Addition e = new Addition();
		int counter = 1;
		String message = testInstance.formatErrorMessage(e, counter);
		assertNotNull(message);
		assertEquals("Operator: + (position: 1): insufficient parameters", message);
	}
	
	@Test
	public void whenSubtractionUserEntryProvidedThenPlusSignShouldReturn() {
		Subtraction e = new Subtraction();
		int counter = 1;
		String message = testInstance.formatErrorMessage(e, counter);
		assertNotNull(message);
		assertEquals("Operator: - (position: 1): insufficient parameters", message);
	}
	
	@Test
	public void whenMultiplicationUserEntryProvidedThenPlusSignShouldReturn() {
		Multiplication e = new Multiplication();
		int counter = 1;
		String message = testInstance.formatErrorMessage(e, counter);
		assertNotNull(message);
		assertEquals("Operator: * (position: 1): insufficient parameters", message);
	}
	
	@Test
	public void whenDivisionUserEntryProvidedThenPlusSignShouldReturn() {
		Division e = new Division();
		int counter = 1;
		String message = testInstance.formatErrorMessage(e, counter);
		assertNotNull(message);
		assertEquals("Operator: / (position: 1): insufficient parameters", message);
	}
	
	@Test
	public void whenSquareRootUserEntryProvidedThenPlusSignShouldReturn() {
		SquareRoot e = new SquareRoot();
		int counter = 1;
		String message = testInstance.formatErrorMessage(e, counter);
		assertNotNull(message);
		assertEquals("Operator: sqrt (position: 1): insufficient parameters", message);
	}
	
	@Test
	public void whenClearUserEntryProvidedThenPlusSignShouldReturn() {
		Clear e = new Clear();
		int counter = 1;
		String message = testInstance.formatErrorMessage(e, counter);
		assertNotNull(message);
		assertEquals("Operator: clear (position: 1): insufficient parameters", message);
	}
	
	@Test
	public void whenUndoUserEntryProvidedThenPlusSignShouldReturn() {
		Undo e = new Undo();
		int counter = 1;
		String message = testInstance.formatErrorMessage(e, counter);
		assertNotNull(message);
		assertEquals("Operator: undo (position: 1): insufficient parameters", message);
	}
	
	@Test
	public void whenUserEntryUserEntryProvidedThenPlusSignShouldReturn() {
		UserEntry mockUserEntry = PowerMockito.mock(UserEntry.class);
		when(mockUserEntry.getEmptyStackErrorMessage(eq(1))).thenReturn(TEST_MESSAGE);
		int counter = 1;
		String message = testInstance.formatErrorMessage(mockUserEntry, counter);
		assertNotNull(message);
		assertThat(message, equalTo(TEST_MESSAGE));
	}
	
	@Test
	public void whenMockStorageAndInputProvidedThenStorageShouldBeUpdated() throws Throwable {
		RpnCalculator partialMockCalculator = PowerMockito.mock(RpnCalculator.class);
		UserEnter mockUserEnter = PowerMockito.mock(UserEnter.class);
		List<UserEntry> entries = new ArrayList<>();
		entries.add(new DigitalUserEntry("6"));
		PowerMockito.when(mockUserEnter.getUserInput()).thenReturn(entries, null);
		Whitebox.setInternalState(partialMockCalculator, "userEnter", mockUserEnter);
		Storage mockStorage = PowerMockito.mock(Storage.class);
		Whitebox.setInternalState(partialMockCalculator, "storage", mockStorage);
		PowerMockito.doCallRealMethod().when(partialMockCalculator, "run");
		
		partialMockCalculator.run();
		verify(mockStorage).pushDigit(Matchers.eq(new BigDecimal(6)));
		verify(mockStorage).pushOperationRecord(Matchers.any(OperationRecord.class));
	}
	
	@Test
	public void whenMockStorageAndInputProvidedWithExceptionThenWarningDisplay() throws Throwable {
		RpnCalculator partialMockCalculator = PowerMockito.mock(RpnCalculator.class);
		Storage mockStorage = PowerMockito.mock(Storage.class);
		Whitebox.setInternalState(partialMockCalculator, "storage", mockStorage);
		UserEnter mockUserEnter = PowerMockito.mock(UserEnter.class);
		List<UserEntry> entries = new ArrayList<>();
		UserEntry mockEntry = PowerMockito.mock(UserEntry.class);
		PowerMockito.doThrow(new EmptyStackException()).when(mockEntry, "execute", mockStorage);
		entries.add(mockEntry);
		PowerMockito.when(mockUserEnter.getUserInput()).thenReturn(entries, null);
		Whitebox.setInternalState(partialMockCalculator, "userEnter", mockUserEnter);
		
		PowerMockito.doCallRealMethod().when(partialMockCalculator, "run");
		
		partialMockCalculator.run();
		verify(mockStorage).printStack();
	}
	
	@Test
	public void whenRpnCalculatorObjectProvidedThenAnEmptyStorageShouldReturn() {
		Storage storage = testInstance.getStorage();
		assertNotNull(storage);
	}

}
