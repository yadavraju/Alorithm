package lab2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class removeDublicate {
	
	private static Set<Integer> keySet;

	public static void removeDublicate(List<Integer> l){
		HashMap<Integer, Integer> h = new HashMap<>();
		for (Integer integer : l) {
			h.put(integer, 0);
		}
		
//		keySet = h.keySet();
//		Iterator<Integer> iterator = keySet.iterator();
//		while(iterator.hasNext()){
//			System.out.println(iterator.next());
//		}
		List<Integer> a = new ArrayList<>();
		int count = 0;
		for (Integer ll : l) {
			if(h.containsKey(ll)){
				
				h.remove(ll);
				
			}else {
				a.add(ll);
				
			}
		}
		
		System.out.println(a);


	}
	
	
	
	public static void main(String[] args){
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(1);
		list.add(2);
		list.add(7);
		removeDublicate(list);
	}

}
