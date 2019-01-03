package com.conan.rpncalculator.userenter.factory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import com.conan.rpncalculator.userenter.UserEntry;
import org.junit.Test;

import com.conan.rpncalculator.userenter.operator.Addition;
import com.conan.rpncalculator.userenter.operator.Clear;
import com.conan.rpncalculator.userenter.operator.Division;
import com.conan.rpncalculator.userenter.operator.Multiplication;
import com.conan.rpncalculator.userenter.operator.SquareRoot;
import com.conan.rpncalculator.userenter.operator.Subtraction;
import com.conan.rpncalculator.userenter.operator.Undo;

public class OperatorFactoryTest {


	@Test
	public void whenAnAdditionStringProvidedThenTheAdditionOperatorShouldReturn() {
		String userEntered = "+";
		Optional<UserEntry> userEntry = OperatorFactory.getOperator(userEntered);
		assertNotNull(userEntry);
		assertTrue(userEntry.isPresent());
		assertTrue(userEntry.get() instanceof Addition);
	}
	
	@Test
	public void whenASubtractStringProvidedThenTheSubtractionOperatorShouldReturn() {
		String userEntered = "-";
		Optional<UserEntry> userEntry = OperatorFactory.getOperator(userEntered);
		assertNotNull(userEntry);
		assertTrue(userEntry.isPresent());
		assertTrue(userEntry.get() instanceof Subtraction);
	}
	
	@Test
	public void whenAMultiplicationStringProvidedThenTheMultiplicationOperatorShouldReturn() {
		String userEntered = "*";
		Optional<UserEntry> userEntry = OperatorFactory.getOperator(userEntered);
		assertNotNull(userEntry);
		assertTrue(userEntry.isPresent());
		assertTrue(userEntry.get() instanceof Multiplication);
	}
	
	@Test
	public void whenADivisionStringProvidedThenTheDivisionOperatorShouldReturn() {
		String userEntered = "/";
		Optional<UserEntry> userEntry = OperatorFactory.getOperator(userEntered);
		assertNotNull(userEntry);
		assertTrue(userEntry.isPresent());
		assertTrue(userEntry.get() instanceof Division);
	}
	
	@Test
	public void whenASqrtStringProvidedThenTheSquareRootOperatorShouldReturn() {
		String userEntered = "sqrt";
		Optional<UserEntry> userEntry = OperatorFactory.getOperator(userEntered);
		assertNotNull(userEntry);
		assertTrue(userEntry.isPresent());
		assertTrue(userEntry.get() instanceof SquareRoot);
	}
	
	@Test
	public void whenAClearStringProvidedThenTheClearOperatorShouldReturn() {
		String userEntered = "clear";
		Optional<UserEntry> userEntry = OperatorFactory.getOperator(userEntered);
		assertNotNull(userEntry);
		assertTrue(userEntry.isPresent());
		assertTrue(userEntry.get() instanceof Clear);
	}

	@Test
	public void whenAUndoStringProvidedThenTheUndoOperatorShouldReturn() {
		String userEntered = "undo";
		Optional<UserEntry> userEntry = OperatorFactory.getOperator(userEntered);
		assertNotNull(userEntry);
		assertTrue(userEntry.isPresent());
		assertTrue(userEntry.get() instanceof Undo);
	}
	
	@Test
	public void whenAnInvalidStringProvidedThenEmptyOptionalShouldReturn() {
		String userEntered = "abc";
		Optional<UserEntry> userEntry = OperatorFactory.getOperator(userEntered);
		assertNotNull(userEntry);
		assertFalse(userEntry.isPresent());
	}
	
	@Test
	public void whenEmptyStringProvidedThenEmptyOptionalObjectShouldReturn() {
		String userEntered = "";
		Optional<UserEntry> userEntry =
			OperatorFactory.getOperator(userEntered);
		assertNotNull(userEntry);
		assertFalse(userEntry.isPresent());
	}
	
	@Test
	public void whenNullStringProvidedThenEmptyOptionalObjectShouldReturn() {
		String userEntered = null;
		Optional<UserEntry> userEntry = OperatorFactory.getOperator(userEntered);
		assertNotNull(userEntry);
		assertFalse(userEntry.isPresent());
	}
}
