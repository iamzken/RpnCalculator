package com.conan.rpncalculator.userenter.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

import com.conan.rpncalculator.fixture.RpnCalculatorTestFixture;
import com.conan.rpncalculator.userenter.UserEntry;
import com.conan.rpncalculator.userenter.operator.Addition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;



public class DefaultUserEnterFactoryTest {

	private DefaultUserEnterFactory testInstance;
	private Scanner mockScanner;

	@Before
	public void setUp() {
		this.testInstance = new DefaultUserEnterFactory();
	}

	@After
	public void tearDown() {
		this.testInstance = null;
		this.mockScanner = null;
	}


	@Test
	public void whenUserEnterNullThanOptionalOfEmptyUserEntryShouldReturn() {
		String userEntered = null;
		Optional<UserEntry> userEntry = testInstance.constructUserEntry(userEntered);
		assertNotNull(userEntry);
		assertFalse(userEntry.isPresent());
	}
	
	@Test
	public void whenUserEnteredEmptyStringThenAnEmptyOptionalObjectShouldReturn() {
		String userEntered = "";
		Optional<UserEntry> userEntry = testInstance.constructUserEntry(userEntered);
		assertNotNull(userEntry);
		assertFalse(userEntry.isPresent());
	}
	
	@Test
	public void whenUserEnteredInvalidStringThenAnEmptyOptionalObjectShouldReturn() {
		String userEntered = "abc";
		Optional<UserEntry> userEntry = testInstance.constructUserEntry(userEntered);
		assertNotNull(userEntry);
		assertFalse(userEntry.isPresent());
	}
	
	@Test
	public void whenUserEnteredValidStringThenAnDigitalUserEntryOptionalObjectShouldReturn() {
		String userEntered = "123";
		Optional<UserEntry> userEntry = testInstance.constructUserEntry(userEntered);
		assertNotNull(userEntry);
		assertTrue(userEntry.isPresent());
	}
	
	@Test
	public void whenUserEnteredValidNegativeStringThenAnDigitalUserEntryOptionalObjectShouldReturn() {
		String userEntered = "-123";
		Optional<UserEntry> userEntry = testInstance.constructUserEntry(userEntered);
		assertNotNull(userEntry);
		assertTrue(userEntry.isPresent());
		BigDecimal retrieve = Whitebox.getInternalState(userEntry.get(), "digits");
		assertEquals(new BigDecimal(-123), retrieve);
	}
	
	@Test
	public void whenUserEnteredAnAdditionStringThenAnDigitalUserEntryOptionalObjectShouldReturn() {
		String userEntered = "+";
		Optional<UserEntry> userEntry = testInstance.constructUserEntry(userEntered);
		assertNotNull(userEntry);
		assertTrue(userEntry.isPresent());
		UserEntry userEntryObject = userEntry.get();
		assertTrue(userEntryObject instanceof Addition);
	}
	
	@Test
	public void whenUserEnteredValidStringThenAnDigitalOptionalObjectShouldReturn() {
		String userEntered = "123";
		Optional<UserEntry> userEntry = testInstance.getDigitalUserEntry(userEntered);
		assertNotNull(userEntry);
		assertTrue(userEntry.isPresent());
	}
	
	@Test
	public void whenUserEnteredInvalidStringThenAnEmptyDigitalOptionalObjectShouldReturn() {
		String userEntered = "abc";
		Optional<UserEntry> userEntry = testInstance.getDigitalUserEntry(userEntered);
		assertNotNull(userEntry);
		assertFalse(userEntry.isPresent());
	}
	
	@Test
	public void whenUserEnteredValidStringThenAnOperatorOptionalObjectShouldReturn() {
		String userEntered = "+";
		Optional<UserEntry> userEntry = testInstance.getOperatorUserEntry(userEntered);
		assertNotNull(userEntry);
		assertTrue(userEntry.isPresent());
	}
	
	@Test
	public void whenUserEnteredInvalidStringThenAnEmptyOperatorOptionalObjectShouldReturn() {
		String userEntered = "abc";
		Optional<UserEntry> userEntry = testInstance.getOperatorUserEntry(userEntered);
		assertNotNull(userEntry);
		assertFalse(userEntry.isPresent());
	}
	
	@Test
	public void whenUserEnteredInvalidStringThenEmptyListShouldReturn() throws Exception {
		String userEntered = "abc";
		DefaultUserEnterFactory partialMockFactory = givenPartialMockFactory(userEntered);
		PowerMockito.doCallRealMethod().when(partialMockFactory, "constructUserEntry", Matchers.anyString());
		PowerMockito.doCallRealMethod().when(partialMockFactory, "getOperatorUserEntry", Matchers.anyString());
		
		assertNotNull(partialMockFactory.getScanner());
		
		List<UserEntry> userEntries = partialMockFactory.getUserInput();
		assertNotNull(userEntries);
		assertTrue(userEntries.isEmpty());
	}
	
	@Test
	public void whenUserEnteredEmptyStringThenExceptionShouldRaised() throws Exception {
		String userEntered = "";
		DefaultUserEnterFactory partialMockFactory = givenPartialMockFactory(userEntered);
		
		assertNotNull(partialMockFactory.getScanner());
		
		try {
			partialMockFactory.getUserInput();
			fail("Program reached unexpected point!");
		}
		catch (NoSuchElementException e) {
			accessNoLineFoundException(e);
		}
	}
	
	@Test
	public void whenUserEnteredCtrlCThenExist() throws Exception {
		String userEntered = "\u0003";
		DefaultUserEnterFactory partialMockFactory = givenPartialMockFactory(userEntered);
		
		assertNotNull(partialMockFactory.getScanner());
		
		try {
			partialMockFactory.getUserInput();
			fail("Program reached unexpected point!");
		}
		catch (NoSuchElementException e) {
			accessNoLineFoundException(e);
		}
		
	}

	private DefaultUserEnterFactory givenPartialMockFactory(String userEntered) throws Exception {
		DefaultUserEnterFactory partialMockFactory = PowerMockito.mock(DefaultUserEnterFactory.class);
		givenMockByteArrayInputStreamScanner(userEntered);
		Whitebox.setInternalState(partialMockFactory, "scanner", mockScanner);
		PowerMockito.doCallRealMethod().when(partialMockFactory, "getUserInput");
		PowerMockito.doCallRealMethod().when(partialMockFactory, "getScanner");
		return partialMockFactory;
	}
	
	private void accessNoLineFoundException(NoSuchElementException e) {
		String errorMessage = e.getMessage();
		assertNotNull(errorMessage);
		assertEquals("No line found", errorMessage);
	}
	
	private void givenMockByteArrayInputStreamScanner(String input) {
		InputStream in = RpnCalculatorTestFixture.givenByteArrayInputStream(input);
	    System.setIn(in);
	    mockScanner = new Scanner(System.in);
	}
}
