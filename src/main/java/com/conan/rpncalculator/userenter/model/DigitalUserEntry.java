package com.conan.rpncalculator.userenter.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.conan.rpncalculator.history.record.OperationRecord;
import com.conan.rpncalculator.storage.Storage;
import com.conan.rpncalculator.userenter.UserEntry;

public class DigitalUserEntry implements UserEntry {

	private BigDecimal digits;
	
	public DigitalUserEntry(final String userEntered) {
		this.digits = new BigDecimal(userEntered);
	}
	
	@Override
	public void execute(Storage storage) {
		storage.pushDigit(digits);
		OperationRecord record = toOperationRecord().apply(digits);
		storage.pushOperationRecord(record);
	}

	static Function<BigDecimal, OperationRecord> toOperationRecord() {
		return d -> {List<BigDecimal> params = Arrays.asList(d);
			return new OperationRecord(params, null);
		};
	}

	@Override
	public String getEmptyStackErrorMessage(int counter) {
		return "";
	}

	@Override
    public String toString(){
	    return digits.toString();
    }
}
