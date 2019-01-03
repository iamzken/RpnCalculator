package com.conan.rpncalculator;

import java.io.InputStream;
import java.util.EmptyStackException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.conan.rpncalculator.storage.DefaultStorage;
import com.conan.rpncalculator.storage.Storage;
import com.conan.rpncalculator.userenter.UserEnter;
import com.conan.rpncalculator.userenter.UserEntry;
import com.conan.rpncalculator.userenter.factory.DefaultUserEnterFactory;

//程序入口类
public class RpnCalculator {

	private static final int ONE = 1;
	private UserEnter userEnter;
	private Storage storage;
	
	public RpnCalculator() {
		this(System.in);
	}
	
	public RpnCalculator(InputStream in) {
		if (null == in) {
			throw new IllegalArgumentException("InputStream cannot be null!");
		}
		
		this.userEnter = new DefaultUserEnterFactory(in);
		this.storage = new DefaultStorage();
	}
	
	public void run() {
		List<UserEntry> userEntries = null;
		AtomicInteger counter = new AtomicInteger(ONE);
		while( null != (userEntries = this.userEnter.getUserInput())) {		
			for(UserEntry e : userEntries) {
				try {
					e.execute(this.storage);
					counter.incrementAndGet();
				}
				catch (EmptyStackException ese) {
					System.out.println("\u001B[31m" + formatErrorMessage(e, counter.get()) + "\u001B[0m");
					storage.printStack();
					if(counter.get() < userEntries.size()){
						List<UserEntry> subList = userEntries.subList(counter.get(), userEntries.size());
						System.out.print("\u001B[31m(The " + subList.get(0));
						StringBuffer sb = new StringBuffer("");
						for(int i = 1; i < subList.size(); i++){
							if(i == subList.size() - 1){
								sb.append(" and " + subList.get(i));
							}else{
								sb.append("、" + subList.get(i));
							}
						}
						System.out.print(sb + " were not pushed on to the stack due to the previous error)\u001B[0m");
						System.out.println();
					}
					break;
				}
			}

		}
	}

	protected String formatErrorMessage(UserEntry e, int counter) {
		return e.getEmptyStackErrorMessage(counter);
	}

	public Storage getStorage() {
		return storage;
	}

	public static void main(String[] argv) {
		new RpnCalculator().run();
	}
}
