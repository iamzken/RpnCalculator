package com.conan.rpncalculator.history.record;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.List;

import com.conan.rpncalculator.userenter.UserEntry;
import com.conan.rpncalculator.userenter.operator.SquareRoot;
import org.junit.Assert;
import org.junit.Test;

import com.conan.rpncalculator.fixture.RpnCalculatorTestFixture;

public class OperationRecordTest {

	@Test
	public void whenContructorCalledThenObjectShouldReturn() {

		List<BigDecimal> params = RpnCalculatorTestFixture.getOperationParameters();
		UserEntry operator = new SquareRoot();

		OperationRecord record = new OperationRecord(params, operator);

		assertNotNull(record);
		assertEquals(params, record.getParameters());
		Assert.assertEquals(operator, record.getOperator());
	}


	@Test
	public void whenContructorCalledWithoutOperatorThenObjectShouldReturn() {

		List<BigDecimal> params = RpnCalculatorTestFixture.getOperationParameters();
		UserEntry operator = null;

		OperationRecord record = new OperationRecord(params, operator);
		assertNotNull(record);
		assertEquals(params, record.getParameters());
		assertNull(record.getOperator());
	}
	

	@Test
	public void whenContructorCalledWithNullParamsThenExceptionRaised() {
		List<BigDecimal> params = null;
		UserEntry operator = null;
		try {
			new OperationRecord(params, operator);
			fail("Program reached unexpecte point!");
		}
		catch (IllegalArgumentException e) {
			String message = e.getMessage();
			assertNotNull(message);
			assertEquals("parameters cannot be null", message);
		}
	}
}
