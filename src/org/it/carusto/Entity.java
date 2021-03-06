package org.it.carusto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

public class Entity {	
	private int total;	
	private Map<Integer, Integer> currMap = null; 	 
	
	public Entity() {		
		currMap = new TreeMap<Integer,Integer>();
		total = 0;
	}
				
	public void addNote(int value, int number) {		
		if(currMap.containsKey(value)) {			
			int nNum = currMap.get(value) + number;				
			currMap.put(value, nNum);			
		} else 
			currMap.put(value, number);		
		total += (value*number);
	}
	
	public Set<Entry<Integer,  Integer>> getEntrySet () {
		return  currMap.entrySet();				
	}
	
	public void getCash(int amount) throws IOException {		
		if(amount > total) 
			throw new IOException();
		else {	
			int currValue, 
				currNum,   
				needNum,   				 
				rest;      
			
			Map<Integer, Integer> tmpMap = new TreeMap<Integer,Integer>(currMap);
			Properties tactic = new Properties();
			
			List<Integer> list = new ArrayList<Integer>(currMap.keySet()); 
			Collections.reverse(list); 								       
			Iterator<Integer> litr = list.listIterator();
						
			while(litr.hasNext()) {				
				currValue = (Integer) litr.next();
				rest = amount % currValue;
				amount -= rest;
				needNum = amount / currValue;
				currNum = tmpMap.get(currValue);									
					
				if(needNum == currNum) {
					tmpMap.remove(currValue);					
					amount -= (currValue*currNum);
					tactic.put(currValue, currNum);
											
				} else if(needNum < currNum) {
					tmpMap.put(currValue, currNum-needNum);
					amount -= (currValue*needNum);
					tactic.put(currValue, needNum);
				
				} else {
					tmpMap.remove(currValue);
					amount -= (currValue*currNum);						 						
					tactic.put(currValue, currNum);
					
				} 				
				if(rest != 0) amount +=rest;
				
				if(!litr.hasNext()) {
					if(amount !=0) 
						throw new IOException();
					else {
						currMap = tmpMap;											
						for(Object obj : tactic.keySet()) 
							System.out.println(obj + " " + tactic.get(obj));													
					}
				}				
			}				
		}		
	}	
}
