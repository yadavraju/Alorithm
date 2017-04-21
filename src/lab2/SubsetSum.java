package lab2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubsetSum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(subsetsum(new int[]{1,2,8,7,6},6));
		"hello".contains("5");
	}
	
	static boolean subsetsum(int[] s, int k){
		Set<Integer> sets = new HashSet<Integer>();
		for(int i =0; i< s.length;i++)
			sets.add(s[i]);
		Set<Set<Integer>> powers = powerSet(sets); 
		for (Set<Integer> subSet : powers) {
			if(sumSubSet(subSet)==k)
				return true;
		}		
		return false;
	}
	
	static int sumSubSet(Set<Integer> subSet){
		int sum=0;
		for (Integer elem : subSet){
			sum+=elem;
		}
		return sum;
	}
	
	public static Set<Set<Integer>> powerSet(Set<Integer> X) {
        Set<Set<Integer>> P = new HashSet<Set<Integer>>();
        if (X.isEmpty()) {
        	P.add(new HashSet<Integer>());
            return P;
        }
        List<Integer> list = new ArrayList<Integer>(X);
        Integer f = list.get(0);
        Set<Integer> rest = new HashSet<Integer>(list.subList(1, list.size()));
        for (Set<Integer> x : powerSet(rest)) {
            Set<Integer> T = new HashSet<Integer>();
            T.add(f);
            T.addAll(x);
            P.add(T);
            P.add(x);
        }
        return P;
    }
	

}


