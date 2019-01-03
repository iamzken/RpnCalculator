package com.conan.rpncalculator.userenter.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.conan.rpncalculator.userenter.enums.OperatorsEnum;
import org.junit.Test;


public class OperatorsEnumTest {


	@Test
	public void whenNullStringProvidedThenIllegalArgumentExceptionWillRaised() {
		String userEntered = null;
		try {
			OperatorsEnum.fromString(userEntered);
			fail("Program reached unexpected point");
		}
		catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}
	
	@Test
	public void whenAnEmptyStringProvidedThenIllegalArgumentExceptionWillRaised() {
		String userEntered = "";
		try {
			OperatorsEnum.fromString(userEntered);
			fail("Program reached unexpected point");
		}
		catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}
	
	@Test
	public void whenAnInvalidStringProvidedThenIllegalArgumentExceptionWillRaised() {
		String userEntered = "abc";
		try {
			OperatorsEnum.fromString(userEntered);
			fail("Program reached unexpected point");
		}
		catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}
	
	@Test
	public void whenAnAdditionStringProvidedThenAnAdditionOperatorShouldReturn() {
		String userEntered = "+";
		OperatorsEnum operator =	OperatorsEnum.fromString(userEntered);
		assertTrue(OperatorsEnum.ADDITION == operator);
	}
	
	@Test
	public void whenAsubtractionStringProvidedThenASubtractionOperatorShouldReturn() {
		String userEntered = "-";
		OperatorsEnum operator =	OperatorsEnum.fromString(userEntered);
		assertTrue(OperatorsEnum.SUBTRACTION == operator);
	}
	
	@Test
	public void whenAMultiplicationStringProvidedThenAMultiplicationOperatorShouldReturn() {
		String userEntered = "*";
		OperatorsEnum operator =	OperatorsEnum.fromString(userEntered);
		assertTrue(OperatorsEnum.MULTIPLICATION == operator);
	}
	
	@Test
	public void whenADivisionStringProvidedThenADivisionOperatorShouldReturn() {
		String userEntered = "/";
		OperatorsEnum operator =	OperatorsEnum.fromString(userEntered);
		assertTrue(OperatorsEnum.DIVISION == operator);
	}
	
	@Test
	public void whenASquareRootStringProvidedThenASquareRootOperatorShouldReturn() {
		String userEntered = "sqrt";
		OperatorsEnum operator =	OperatorsEnum.fromString(userEntered);
		assertTrue(OperatorsEnum.SQUAREROOT == operator);
	}
	
	@Test
	public void whenAnUndoStringProvidedThenAnUndoOperatorShouldReturn() {
		String userEntered = "undo";
		OperatorsEnum operator =	OperatorsEnum.fromString(userEntered);
		assertTrue(OperatorsEnum.UNDO == operator);
	}
	
	@Test
	public void whenAClearStringProvidedThenAClearOperatorShouldReturn() {
		String userEntered = "clear";
		OperatorsEnum operator =	OperatorsEnum.fromString(userEntered);
		assertTrue(OperatorsEnum.CLEAR == operator);
	}
}
