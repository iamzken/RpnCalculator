package com.conan.rpncalculator.userenter.operator;

import java.math.BigDecimal;

import com.conan.rpncalculator.history.record.OperationRecord;
import com.conan.rpncalculator.storage.Storage;
import com.conan.rpncalculator.userenter.UserEntry;
import com.conan.rpncalculator.userenter.enums.OperatorsEnum;

public class Division extends BiOperator {

	@Override
	protected void performDetailOperation(Storage storage) {
		BigDecimal first = storage.popDigit();
		//除数为0时
		if (BigDecimal.ZERO.equals(first)) {
			storage.pushDigit(first);
			System.out.println("Divisor cannot be ZERO!");
			return;
		}
		BigDecimal second = storage.popDigit();
		BigDecimal total = second.divide(first, UserEntry.DECIMAL_PLACES, BigDecimal.ROUND_DOWN);
		storage.pushDigit(total);
		OperationRecord record = this.getOperationRecord(first, second);
		storage.pushOperationRecord(record);
	}

	@Override
	public String getEmptyStackErrorMessage(int counter) {
		StringBuilder buf = new StringBuilder("Operator: ");
		
		buf.append(OperatorsEnum.DIVISION.getCode());
				
		buf.append(" (position: ");
		buf.append(counter);
		buf.append("): insufficient parameters");
		
		return buf.toString();
	}
	@Override
	public String toString(){
		return "/";
	}
}
