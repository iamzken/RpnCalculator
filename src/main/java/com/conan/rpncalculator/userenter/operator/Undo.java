package com.conan.rpncalculator.userenter.operator;

import java.math.BigDecimal;
import java.util.function.Predicate;

import com.conan.rpncalculator.history.record.OperationRecord;
import com.conan.rpncalculator.storage.Storage;
import com.conan.rpncalculator.userenter.UserEntry;
import com.conan.rpncalculator.userenter.enums.OperatorsEnum;

public class Undo implements UserEntry {

	@Override
	public void execute(Storage storage) {
		OperationRecord record = storage.popOperationRecord();
		
		UserEntry operator = record.getOperator();
		if (isNeedClearUpResult().test(operator)) {
			storage.popDigit();
		}
		if (null != record.getOperator()) {
			for(BigDecimal digit : record.getParameters()) {
				storage.pushDigit(digit);
			}
		}
	}


	static Predicate<UserEntry> isNeedClearUpResult() {
		return e -> ((null == e) || (!(e instanceof Clear)));
	}

	@Override
	public String getEmptyStackErrorMessage(int counter) {
		StringBuilder buf = new StringBuilder("Operator: ");
		
		buf.append(OperatorsEnum.UNDO.getCode());
				
		buf.append(" (position: ");
		buf.append(counter);
		buf.append("): insufficient parameters");
		
		return buf.toString();
	}

	@Override
	public String toString(){
		return "undo";
	}
}
