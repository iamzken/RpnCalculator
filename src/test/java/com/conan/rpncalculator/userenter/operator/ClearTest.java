package com.conan.rpncalculator.userenter.operator;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.EmptyStackException;
import java.util.Optional;

import com.conan.rpncalculator.fixture.RpnCalculatorTestFixture;
import com.conan.rpncalculator.history.record.OperationRecord;
import com.conan.rpncalculator.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;

public class ClearTest {

	private Clear testInstance;
	
	@Before
	public void setUp() {
		this.testInstance = new Clear();
	}
	
	@After
	public void tearDown() {
		this.testInstance = null;
	}

	@Test
	public void when2ElementsProvidedThenStorageShouldClear() {
		Storage mockStorage = PowerMockito.mock(Storage.class);
		final Boolean[] flags = {true, true};
		PowerMockito.when(mockStorage.popDigit()).thenAnswer(new Answer<BigDecimal>() {

			@Override
			public BigDecimal answer(InvocationOnMock invocation) throws Throwable {
				Boolean flag0 = flags[0];
				Boolean flag1 = flags[1];
				if (flag0) {
					flags[0] = false;
					return BigDecimal.TEN;
				}
				if (flag1) {
					flags[1] = false;
					return BigDecimal.ONE;
				}
				throw new EmptyStackException();
			}
			
		});
		
		testInstance.execute(mockStorage);
		verify(mockStorage, times(3)).popDigit();
		verify(mockStorage).pushOperationRecord(Matchers.any(OperationRecord.class));
	}
	
	@Test
	public void when2ElementsProvidedThenAnOperationRecordShouldReturn() {
		Optional<OperationRecord> recordOptional = this.testInstance.getOperationRecord(RpnCalculatorTestFixture.get2OperationParameters());
		assertThat(recordOptional, is(notNullValue()));
		assertTrue(recordOptional.isPresent());
		OperationRecord record = recordOptional.get();
		assertThat(record.getParameters().size(), is(equalTo(2)));
		assertThat(this.testInstance, is(equalTo(record.getOperator())));
		assertThat(record.getParameters().get(0), is(equalTo(new BigDecimal(2))));
		assertThat(record.getParameters().get(1), is(equalTo(new BigDecimal(6))));
	}
	
	@Test
	public void shouldReturnCorrectErrorMessage() {
		int counter = 1;
		String message = this.testInstance.getEmptyStackErrorMessage(counter);
		assertThat(message, is(notNullValue()));
		assertThat(message, is(equalTo("Operator: clear (position: 1): insufficient parameters")));
	}
}
