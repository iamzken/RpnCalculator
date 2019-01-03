package com.conan.rpncalculator.userenter.operator;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Stack;

import com.conan.rpncalculator.fixture.RpnCalculatorTestFixture;
import com.conan.rpncalculator.history.record.OperationRecord;
import com.conan.rpncalculator.storage.DefaultStorage;
import com.conan.rpncalculator.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

public class UndoTest {

	private Undo testInstance;
	
	@Before
	public void setUp() {
		this.testInstance = new Undo();
	}
	
	@After
	public void tearDown() {
		this.testInstance = null;
	}
	
	@Test
	public void whenAnElmentProvidedIntoStorageThenUndoShouldSuccess() {
		Storage storage = new DefaultStorage();
		BigDecimal userEntry = new BigDecimal(6);
		storage.pushDigit(userEntry);
		OperationRecord record = new OperationRecord(RpnCalculatorTestFixture.getOperationParameters(), null);
		storage.pushOperationRecord(record);
		testInstance.execute(storage);
		Stack<BigDecimal> stack = (Stack<BigDecimal>) Whitebox.getInternalState(storage, "digitStack");
		assertNotNull(stack);
		assertTrue(stack.isEmpty());
	}
	
	@Test
	public void whenAnOperatorElmentProvidedIntoStorageThenUndoShouldSuccess() {
		Storage storage = new DefaultStorage();
		BigDecimal userEntry = new BigDecimal(8);
		storage.pushDigit(userEntry);
		OperationRecord record = new OperationRecord(RpnCalculatorTestFixture.get2OperationParameters(), new Addition());
		storage.pushOperationRecord(record);
		testInstance.execute(storage);
		Stack<BigDecimal> stack = (Stack<BigDecimal>) Whitebox.getInternalState(storage, "digitStack");
		assertNotNull(stack);
		assertTrue(2 == stack.size());
		assertEquals(new BigDecimal(6), stack.get(0));
		assertEquals(new BigDecimal(2), stack.get(1));
	}
	
	@Test
	public void whenAnOperatorProvidedThenFlagShouldReturn() {
		assertTrue(Undo.isNeedClearUpResult().test(null));
		assertTrue(Undo.isNeedClearUpResult().test(new Addition()));
		assertTrue(Undo.isNeedClearUpResult().test(new Subtraction()));
		assertTrue(Undo.isNeedClearUpResult().test(new Multiplication()));
		assertTrue(Undo.isNeedClearUpResult().test(new Division()));
		assertTrue(Undo.isNeedClearUpResult().test(new SquareRoot()));
		assertTrue(Undo.isNeedClearUpResult().test(new Undo()));
		assertFalse(Undo.isNeedClearUpResult().test(new Clear()));
	}
	
	@Test
	public void shouldReturnCorrectErrorMessage() {
		int counter = 1;
		String message = this.testInstance.getEmptyStackErrorMessage(counter);
		assertThat(message, is(notNullValue()));
		assertThat(message, is(equalTo("Operator: undo (position: 1): insufficient parameters")));
	}
}
