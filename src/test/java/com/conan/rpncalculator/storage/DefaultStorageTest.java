package com.conan.rpncalculator.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.EmptyStackException;
import java.util.Stack;

import com.conan.rpncalculator.fixture.RpnCalculatorTestFixture;
import com.conan.rpncalculator.history.record.OperationRecord;
import com.conan.rpncalculator.userenter.UserEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;


public class DefaultStorageTest {

	private Storage testInstance;
	
	@Before
	public void setUp() {
		this.testInstance = new DefaultStorage();
	}
	
	@After
	public void tearDown() {
		this.testInstance = null;
	}

	@Test
	public void whenConstructorCalledThenAObjectShouldReturn() {

		Storage storage = new DefaultStorage();
		assertNotNull(storage);
		assertEmptyStack(storage);
	}


	@Test
	public void whenStorageProvidedThenPushNPopShouldWork() {
		assertEmptyStack(this.testInstance);
		
		BigDecimal digit = BigDecimal.ZERO;
		
		testInstance.pushDigit(digit);
		BigDecimal retrieve = testInstance.popDigit();
		assertNotNull(retrieve);
		assertEquals(digit, retrieve);
		
		assertEmptyStack(testInstance);
	}
	
	@Test
	public void whenOperationRecordProvidedThenHistoryShouldHasTheRecord() {
		OperationRecord record = new OperationRecord(RpnCalculatorTestFixture.getOperationParameters(), null);
		this.assertEmptyHistory(testInstance);
		testInstance.pushOperationRecord(record);
		OperationRecord retrieve = testInstance.popOperationRecord();
		assertNotNull(retrieve);
		assertEquals(record, retrieve);
				
		assertEmptyStack(testInstance);
	}
	
	@Test
	public void whenMockStorageProvidedThenOputToConsole() throws Exception {
		DefaultStorage mockStorage = givenMockStorage();
		PowerMockito.doCallRealMethod().when(mockStorage, "printStack");
		mockStorage.printStack();
	}

	@Test
	public void whenMockStorageProvidedThen2ShouldReturn() throws Exception {
		DefaultStorage mockStorage = givenMockStorage();
		PowerMockito.doCallRealMethod().when(mockStorage, "getDigitsStackSize");
		int size = mockStorage.getDigitsStackSize();
		assertTrue(2 == size);
	}
	
	@Test
	public void whenABigDecimalProvidedThen10DigitsShouldReturn() {
		BigDecimal first = new BigDecimal(2);
		BigDecimal digit = new BigDecimal(Math.sqrt(first.doubleValue())).setScale(UserEntry.DECIMAL_PLACES, BigDecimal.ROUND_DOWN);
		String result = ((DefaultStorage)testInstance).format10Digits(digit);
		assertNotNull(result);
		assertEquals("1.4142135382", result);
	}
	
	@Test
	public void whenABigDecimalProvidedThen2DigitsShouldReturn() {
		BigDecimal digit = new BigDecimal(0.25);
		String result = ((DefaultStorage)testInstance).format10Digits(digit);
		assertNotNull(result);
		assertEquals("0.25", result);
	}
	
	private DefaultStorage givenMockStorage() {
		DefaultStorage mockStorage = PowerMockito.mock(DefaultStorage.class);
		Stack<BigDecimal> list = new Stack<>();
		list.addAll(RpnCalculatorTestFixture.get2OperationParameters());
		Whitebox.setInternalState(mockStorage, "digitStack", list);
		return mockStorage;
	}
	
	private void assertEmptyHistory(Storage storage) {
		try {
			storage.popOperationRecord();
			fail("Program reached unexpected point!");
		} catch (Exception e) {
			assertTrue(e instanceof EmptyStackException);
		}
	}
	
	private void assertEmptyStack(Storage storage) {
		try {
			storage.popDigit();
			fail("Program reached unexpected point!");
		} catch (Exception e) {
			assertTrue(e instanceof EmptyStackException);
		}
	}
}
