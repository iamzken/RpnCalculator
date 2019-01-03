package com.conan.rpncalculator.userenter.operator;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import com.conan.rpncalculator.history.record.OperationRecord;
import com.conan.rpncalculator.storage.Storage;
import com.conan.rpncalculator.userenter.UserEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;

public class SquareRootTest {

	private SquareRoot testInstance;
	
	@Before
	public void setUp() {
		this.testInstance = new SquareRoot();
	}
	
	@After
	public void tearDown() {
		this.testInstance = null;
	}
	
	@Test
	public void whenElementProvidedThenStorageUpdateWithOneResult() {
		Storage mockStorage = PowerMockito.mock(Storage.class);
		PowerMockito.when(mockStorage.popDigit()).thenReturn(new BigDecimal(9));
		
		testInstance.execute(mockStorage);
		verify(mockStorage).pushDigit(Matchers.eq(new BigDecimal(3).setScale(UserEntry.DECIMAL_PLACES)));
		verify(mockStorage, times(1)).popDigit();
	}
	
	@Test
	public void whenNegativeElementProvidedThenStorageUpdateWithOneResult() {
		Storage mockStorage = PowerMockito.mock(Storage.class);
		PowerMockito.when(mockStorage.popDigit()).thenReturn(new BigDecimal(-9));
		
		testInstance.execute(mockStorage);
		verify(mockStorage, times(1)).pushDigit(Matchers.eq(new BigDecimal(-9)));
		verify(mockStorage, times(1)).popDigit();
	}
	
	
	@Test
	public void when1ElementProvidedThenAnOperationRecordShouldReturn() {
		OperationRecord record = this.testInstance.getOperationRecord(new BigDecimal(6));
		assertThat(record, is(notNullValue()));
		assertThat(record.getParameters().size(), is(equalTo(1)));
		assertThat(this.testInstance, is(equalTo(record.getOperator())));
		assertThat(record.getParameters().get(0), is(equalTo(new BigDecimal(6))));
	}
	
	@Test
	public void shouldReturnCorrectErrorMessage() {
		int counter = 1;
		String message = this.testInstance.getEmptyStackErrorMessage(counter);
		assertThat(message, is(notNullValue()));
		assertThat(message, is(equalTo("Operator: sqrt (position: 1): insufficient parameters")));
	}
}
