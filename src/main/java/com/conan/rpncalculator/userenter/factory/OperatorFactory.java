package com.conan.rpncalculator.userenter.factory;

import java.util.Optional;

import com.conan.rpncalculator.userenter.UserEntry;
import com.conan.rpncalculator.userenter.enums.OperatorsEnum;
import com.conan.rpncalculator.userenter.operator.Addition;
import com.conan.rpncalculator.userenter.operator.Clear;
import com.conan.rpncalculator.userenter.operator.Division;
import com.conan.rpncalculator.userenter.operator.Multiplication;
import com.conan.rpncalculator.userenter.operator.SquareRoot;
import com.conan.rpncalculator.userenter.operator.Subtraction;
import com.conan.rpncalculator.userenter.operator.Undo;

public class OperatorFactory {

	public static Optional<UserEntry> getOperator(final String userEntered) {
		Optional<UserEntry> userEntry = Optional.empty();
		
		try {
			OperatorsEnum operator = OperatorsEnum.fromString(userEntered);
			switch (operator) {
				case ADDITION:
					userEntry = Optional.of(new Addition());
					break;
				case SUBTRACTION:
					userEntry = Optional.of(new Subtraction());
					break;
				case MULTIPLICATION:
					userEntry = Optional.of(new Multiplication());
					break;
				case DIVISION:
					userEntry = Optional.of(new Division());
					break;
				case SQUAREROOT:
					userEntry = Optional.of(new SquareRoot());
					break;
				case CLEAR:
					userEntry = Optional.of(new Clear());
					break;
				case UNDO:
					userEntry = Optional.of(new Undo());
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return userEntry;
	}
}
