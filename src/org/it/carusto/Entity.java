package org.it.carusto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Entity {	
	private int total;	
	Map<Integer, Integer> map;
	
	public Entity() {		
		map = new TreeMap<>();
		total = 0;
	}
				
	public void addNote(int value, int number) {		
		if(map.containsKey(value)) {			
			int nNum = map.get(value) + number;				
			map.put(value, nNum);			
		} else {
			map.put(value, new Integer(number));
		}
		total += (value*number);
	}
	
	public Set<Entry<Integer,  Integer>> getEntrySet () {
		return  map.entrySet();				
	}
	
	public void getCash(int amount) throws IOException {
		System.out.println("welcome to getCash ");
		if(amount > total) 
			throw new IOException();
		else {	
			int currValue, needNum, currNum, rest;
			System.out.println("amount less total ");
			List<Integer> list = new ArrayList<Integer>(map.keySet());
			Collections.reverse(list);
			Iterator<Integer> litr = list.listIterator();
			
			while(litr.hasNext()) {
				System.out.println("welcome to while ");
				currValue = (Integer) litr.next();
				rest = amount % currValue;
				if(rest == 0) {
					needNum = amount / currValue;
					currNum = map.get(currValue);
					if(needNum == currNum) {
						map.put(currValue, 0);
						break;
					} else if(needNum < currNum) {
						map.put(currValue, currNum-needNum);
						break;												
					} else if(needNum > currNum) {
						amount -= (currValue*currNum);
						System.out.println("amount = " + amount);
					}
				} else if(!litr.hasNext()) { 
					throw new IOException();
				} 				 				
			}				
		}		
	}	
}
