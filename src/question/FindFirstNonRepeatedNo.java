package question;

import java.util.HashMap;
import java.util.Set;

public class FindFirstNonRepeatedNo {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int [] a = {1,2,3,2,5,1};
		int [] T = new int[3*a.length];
		int value = 1;
		
		HashMap<Integer, Integer> h = new HashMap<>();
		
		for (int i=0;i<a.length;i++) {
			if(h.containsKey(a[i])){
				//value = value+1;
				h.put(a[i], ++value);
			}else {
				h.put(a[i], 1);
			}
		}
		Set<Integer> keySet = h.keySet();
		for (Integer integer : keySet) {
			if(h.get(integer)==1){
				System.out.println(integer);
				//break;
			}
		}
		
/*		for (int i=0;i<a.length;i++) {
			int x = a[i];
			T[x] = T[x]+1;
		}
		for (int i=0;i<a.length;i++) {
			int x = a[i];
			if(T[x]==1){
				System.out.println(x);
				break;
			}
		}*/

	}

}
