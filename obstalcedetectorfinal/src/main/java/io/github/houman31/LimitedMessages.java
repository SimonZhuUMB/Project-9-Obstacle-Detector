package io.github.houman31;

import java.util.LinkedList;

public class LimitedMessages extends LinkedList<String>{

	final int max;	//creates variable as final
	
	public LimitedMessages(int max) {
		this.max = max;
	}
	
	@Override
	public boolean add(String item) {		//add item to string
		
		if (size() == max) {				//if size of item is max, it will remove zeros
			this.remove(0);
		} 
			
		return super.add(item);
					
	}
	
	@Override
	public String toString() {				//store output value of detection in string			
		
		StringBuilder sb = new StringBuilder();
		
		for (String item : this) {
			
			if (!sb.isEmpty()) {
				sb.append(System.lineSeparator());
			}
			sb.append(item);
		}
		
		return sb.toString();
	}
}
