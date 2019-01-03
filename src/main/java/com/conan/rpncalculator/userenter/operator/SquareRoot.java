package com.conan.rpncalculator.userenter.operator;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.conan.rpncalculator.history.record.OperationRecord;
import com.conan.rpncalculator.storage.Storage;
import org.omg.PortableInterceptor.USER_EXCEPTION;

import com.conan.rpncalculator.userenter.UserEntry;
import com.conan.rpncalculator.userenter.enums.OperatorsEnum;

public class SquareRoot implements UserEntry, USER_EXCEPTION {

	@Override
	public void execute(Storage storage) {
		BigDecimal first = storage.popDigit();
		if (first.equals(first.abs())) {
			//平方根仅仅能作用于非负数
			BigDecimal result = new BigDecimal(Math.sqrt(first.doubleValue())).setScale(UserEntry.DECIMAL_PLACES, BigDecimal.ROUND_DOWN);
			storage.pushDigit(result);
			OperationRecord record = this.getOperationRecord(first);
			storage.pushOperationRecord(record);
		}
		else {
			storage.pushDigit(first);
			System.out.println("Square root cannot be applied to " + first.stripTrailingZeros());
		}
	}

	protected OperationRecord getOperationRecord(BigDecimal digit) {
		List<BigDecimal> params = Arrays.asList(digit);
		return new OperationRecord(params, this);
	}

	@Override
	public String getEmptyStackErrorMessage(int counter) {
		StringBuilder buf = new StringBuilder("Operator: ");
		
		buf.append(OperatorsEnum.SQUAREROOT.getCode());
				
		buf.append(" (position: ");
		buf.append(counter);
		buf.append("): insufficient parameters");
		
		return buf.toString();
	}

	@Override
	public String toString(){
		return "sqrt";
	}

}
