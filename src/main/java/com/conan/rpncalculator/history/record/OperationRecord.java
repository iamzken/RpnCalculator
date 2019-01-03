package com.conan.rpncalculator.history.record;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.conan.rpncalculator.userenter.UserEntry;

public class OperationRecord {

	private List<BigDecimal> parameters;
	private UserEntry operator;
	
	public List<BigDecimal> getParameters() {
		return parameters;
	}

	public UserEntry getOperator() {
		return operator;
	}

	public OperationRecord(List<BigDecimal> parameters, UserEntry operator) {
		if (CollectionUtils.isEmpty(parameters)) {
			throw new IllegalArgumentException("parameters cannot be null");
		}
		this.parameters = parameters;
		this.operator = operator;
	}
}
