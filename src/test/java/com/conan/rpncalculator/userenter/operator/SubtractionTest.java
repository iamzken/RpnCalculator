package com.conan.rpncalculator.userenter.operator;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import com.conan.rpncalculator.fixture.RpnCalculatorTestFixture;
import com.conan.rpncalculator.history.record.OperationRecord;
import com.conan.rpncalculator.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

public class SubtractionTest {

	private Subtraction testInstance;
	
	@Before
	public void setUp() {
		this.testInstance = new Subtraction();
	}

	@After
	public void tearDown() {
		this.testInstance = null;
	}
	
	@Test
	public void when2ElementsProvidedThenStorageUpdateWithOneResult() {
		Storage mockStorage = RpnCalculatorTestFixture.givenMockStorage();
		
		testInstance.execute(mockStorage);
		verify(mockStorage).pushDigit(Matchers.eq(new BigDecimal(4)));
		verify(mockStorage, times(2)).popDigit();
		verify(mockStorage).pushOperationRecord(Matchers.any(OperationRecord.class));
	}
	
	@Test
	public void when2ElementsProvidedThenAnOperationRecordShouldReturn() {
		OperationRecord record = this.testInstance.getOperationRecord(new BigDecimal(2), new BigDecimal(6));
		assertThat(record, is(notNullValue()));
		assertThat(record.getParameters().size(), is(equalTo(2)));
		assertThat(this.testInstance, is(equalTo(record.getOperator())));
		assertThat(record.getParameters().get(0), is(equalTo(new BigDecimal(6))));
		assertThat(record.getParameters().get(1), is(equalTo(new BigDecimal(2))));
	}
	
	@Test
	public void shouldReturnCorrectErrorMessage() {
		int counter = 1;
		String message = this.testInstance.getEmptyStackErrorMessage(counter);
		assertThat(message, is(notNullValue()));
		assertThat(message, is(equalTo("Operator: - (position: 1): insufficient parameters")));
	}
	
}
