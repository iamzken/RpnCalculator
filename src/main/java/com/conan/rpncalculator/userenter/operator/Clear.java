package com.conan.rpncalculator.userenter.operator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import com.conan.rpncalculator.history.record.OperationRecord;
import com.conan.rpncalculator.storage.Storage;
import org.apache.commons.collections4.CollectionUtils;

import com.conan.rpncalculator.userenter.UserEntry;
import com.conan.rpncalculator.userenter.enums.OperatorsEnum;

public class Clear implements UserEntry {

	@Override
	public void execute(Storage storage) {
		List<BigDecimal> elements = new ArrayList<>();
		BigDecimal digit;
		
		try {
			while (null != (digit = storage.popDigit())) {
				elements.add(digit);
			}
		}
		catch (EmptyStackException e) {
			Consumer<OperationRecord> consumer = record -> storage.pushOperationRecord(record);
			Optional<OperationRecord> record = this.getOperationRecord(elements);
			record.ifPresent(consumer);
		}
	}

	protected Optional<OperationRecord> getOperationRecord(List<BigDecimal> elements) {
		if (CollectionUtils.isNotEmpty(elements)) {
			Collections.reverse(elements);
			return Optional.of(new OperationRecord(elements, this));
		}
		return Optional.empty();
	}

	@Override
	public String getEmptyStackErrorMessage(int counter) {
		StringBuilder buf = new StringBuilder("Operator: ");
		
		buf.append(OperatorsEnum.CLEAR.getCode());
				
		buf.append(" (position: ");
		buf.append(counter);
		buf.append("): insufficient parameters");
		
		return buf.toString();
	}

	@Override
	public String toString(){
		return "clear";
	}

}
