package com.conan.rpncalculator.userenter.enums;

/**
 * 允许用户输入的操作符，一共有7个
 * @author conan
 */
public enum OperatorsEnum {

	ADDITION("+"), 
	SUBTRACTION("-"),
	MULTIPLICATION("*"),
	DIVISION("/"),
	SQUAREROOT("sqrt"),
	UNDO("undo"),
	CLEAR("clear");
	
	
	OperatorsEnum(final String code) {
		this.code = code;
	}
	
	private String code;
	
	public String getCode() {
		return this.code;
	}

	public static OperatorsEnum fromString(String userEntered) {
		for(OperatorsEnum operator : OperatorsEnum.values()) {
			if (operator.getCode().equalsIgnoreCase(userEntered)) {
				return operator;
			}
		}
		throw new IllegalArgumentException("userEntered cannot be " + userEntered);
	}
}
