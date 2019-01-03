package com.conan.rpncalculator.userenter.operator;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;

import com.conan.rpncalculator.history.record.OperationRecord;
import com.conan.rpncalculator.storage.Storage;
import com.conan.rpncalculator.userenter.UserEntry;

public abstract class BiOperator implements UserEntry {

	private static final int TWO = 2;

	protected OperationRecord getOperationRecord(BigDecimal first, BigDecimal second) {
		//逆序
		List<BigDecimal> params = Arrays.asList(second, first);
		return new OperationRecord(params, this);
	}

	protected boolean isValidOperation(Storage storage) {
		if (storage.getDigitsStackSize() < TWO) {
			throw new EmptyStackException();
		}
		return true;
	}
	
	@Override
	public void execute(Storage storage) {
		if (isValidOperation(storage)) {
			performDetailOperation(storage);
		}
	}

	protected abstract void performDetailOperation(Storage storage);
}
