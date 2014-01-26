package org.it.carusto;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.*;

public class CashMachine {
	static Map<String, Entity> currencyMap = new HashMap<String, Entity>();
	

	public static void main(String[] args) {					
		try(Scanner scan = new Scanner(System.in)) {
			String input;	
			Entity entity = null;
			Set<Map.Entry<String, Entity>> currencySet = null;
			
			do {
				System.out.print("Command ('exit' for quit): ");			
				input = scan.nextLine().trim();
				
				if(!input.isEmpty()) {
					if(input.equalsIgnoreCase("exit")) break;
					else {					
						try {
							String result[] = input.split("\\s+");
							int value, number;
							switch(result[0]) {						
							case "+":  
									value = Integer.parseInt(result[2]);
									number = Integer.parseInt(result[3]);
							
									if(validCurrency(result[1]) && validValue(value) && number>0) {																	
										if(!currencyMap.containsKey(result[1])) 
											currencyMap.put(result[1], new Entity());
									
										currencyMap.get(result[1]).addNote(value, number);									
										System.out.println("OK");									
										break;
									}							
									else 
										throw new Exception();							
									
							case "-": 
									number = Integer.parseInt(result[2]);									
									if(validCurrency(result[1]) && number>0) {
										entity = currencyMap.get(result[1]); 
										if(entity != null) {											
											entity.getCash(number);
											System.out.println("OK");
											break;
										}										
									}  
									throw new Exception();													
							
							case "?": 
									currencySet = currencyMap.entrySet();
								
									for(Map.Entry<String, Entity> me : currencySet) {
										String key = me.getKey();
										Set<Entry<Integer, Integer>> tempSet = me.getValue().getEntrySet();
									
										for(Map.Entry<Integer, Integer> me2 : tempSet) 
											System.out.println(key + " " + me2.getKey() + " " + me2.getValue());									
									}																
									System.out.println("OK");
									break;
									
							default:
									throw new Exception();
							}
						}catch(Exception e) {
							System.out.println("ERROR");
						}					
					}
				}
			} while(true);									           			
		}
	}
	
	private static boolean validValue(int par) {		
		for(int n=0; n<=3; n++) 
			if(par == pow(10,n) || par==5*pow(10,n))
				return true;
		return false;		
	}
	
	private static boolean validCurrency(String currency){ 
        Pattern p = Pattern.compile("^([A-Z]{3})+$"); 
        Matcher m = p.matcher(currency); 
        return m.matches(); 
    }
}
