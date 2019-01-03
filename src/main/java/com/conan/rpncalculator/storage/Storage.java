package com.conan.rpncalculator.storage;

import java.math.BigDecimal;

import com.conan.rpncalculator.history.record.OperationRecord;

public interface Storage {

	public void pushDigit(BigDecimal userEntry);
	public BigDecimal popDigit();
	public void printStack();
	
	public void pushOperationRecord(OperationRecord record);
	public OperationRecord popOperationRecord();
	public int getDigitsStackSize();
}
