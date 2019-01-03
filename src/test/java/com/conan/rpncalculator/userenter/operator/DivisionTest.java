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
import com.conan.rpncalculator.userenter.UserEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;

public class DivisionTest {

	private Division testInstance;
	
	@Before
	public void setUp() {
		this.testInstance = new Division();
	}
	
	@After
	public void tearDown() {
		this.testInstance = null;
	}

	@Test
	public void when2ElementsProvidedThenStorageUpdateWithOneResult() {
		Storage mockStorage = RpnCalculatorTestFixture.givenMockStorage();
		
		testInstance.execute(mockStorage);
		verify(mockStorage).pushDigit(Matchers.eq(new BigDecimal(3).setScale(UserEntry.DECIMAL_PLACES)));
		verify(mockStorage, times(2)).popDigit();
		verify(mockStorage).pushOperationRecord(Matchers.any(OperationRecord.class));
	}
	
	@Test
	public void whenDivisorIsZeroProvidedThenStorageShouldBeNotUpdate() {
		Storage mockStorage = PowerMockito.mock(Storage.class);
		PowerMockito.when(mockStorage.popDigit()).thenReturn(BigDecimal.ZERO, new BigDecimal(6));
		PowerMockito.when(mockStorage.getDigitsStackSize()).thenReturn(2);
		
		testInstance.execute(mockStorage);
		verify(mockStorage, times(1)).pushDigit(Matchers.eq(BigDecimal.ZERO));
		verify(mockStorage, times(1)).popDigit();
		verify(mockStorage, times(0)).pushOperationRecord(Matchers.any());
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
		assertThat(message, is(equalTo("Operator: / (position: 1): insufficient parameters")));
	}
}
