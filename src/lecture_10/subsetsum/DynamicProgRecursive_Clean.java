package lecture_10.subsetsum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * This is the same as DynamicProgRecursive, but without
 * all the print statements. Also uses LinkedList instead of arrays.
 * Also cleans up the recursion.
 *
 */
public class DynamicProgRecursive_Clean {
	/** memoization table */
	static HashMap<Key, LinkedList<Integer>> table = new HashMap<>();
	
//	static int[] test = { 2, 5, 123, 48, 29, 19, 34, 10, 20, 88, 47, 19, 21,
//			42, 4, 8, 3, 7, 5, 9, 14, 26, 25, 31, 33, 18, 44, 35, 48, 53, 51,
//			61, 69, 72 };
	static int[] test = {2,3,4};
	public static void main(String[] args) {
		//int k = 611;
		int k = 5;
		DynamicProgRecursive_Clean ss = new DynamicProgRecursive_Clean();
		LinkedList<Integer> result = ss.subsetsum(test, k);
		System.out.println("Input values: " + Arrays.toString(test) + ", k = " + k);
		System.out.println("Indices: " + result.toString());
		System.out.println("Values: " + Arrays.toString(valuesOf(test, result)));
	}
	
	LinkedList<Integer> subsetsum(int[] S, int k) {
		if(S == null) throw new IllegalArgumentException("Null input");
		return recSubsetSum(S, S.length, k);
	}

	LinkedList<Integer> recSubsetSum(int[] S, int len, int k) {
		if (k < 0)
			return ConstantsLinkedList.NULL;
		if (len == 1) {
			if (k == 0)
				return ConstantsLinkedList.EMPTY_SET;
			else if (k == S[0])
				return ConstantsLinkedList.ZERO_ELEMENT;
			else 
				return ConstantsLinkedList.NULL;	
		}
				
		int last = S[len-1];
		int newLen = len - 1;
		
		//Case 1: last element is not in T
		//if possible, read stored computation
		Key key = new Key(newLen,k);
		LinkedList<Integer> t1 = table.get(key);
		
		if(t1 == null) { // not found in table
			t1 = recSubsetSum(S, newLen, k);
			table.put(key, t1);
		}
		if (t1 != ConstantsLinkedList.NULL) {
			return t1;
		}
		
		//Case 2: last element is in T
		//if possible, read stored computation
		key = new Key(newLen, k - last);
		LinkedList<Integer> t2 = table.get(key);
		
		if(t2 == null) { // not found in table
			t2 = recSubsetSum(S, newLen, k - last);
			table.put(key, t2);
		}
		
		if (t2 != ConstantsLinkedList.NULL) {
			return adjoin(t2, newLen);
		}
		return ConstantsLinkedList.NULL;
		
	}
	/**
	 * Support utility that appends i to the end of the LinkedList.
	 */
	LinkedList<Integer> adjoin(LinkedList<Integer> T, int i) {
		if (T == ConstantsLinkedList.NULL)
			return T;
		T.add(i);
		return T;
	}
	
	private static Integer[] valuesOf(int[] originalArray, 
			LinkedList<Integer> indices) {
		if(indices.equals(ConstantsLinkedList.NULL)) return null;
		List<Integer> retval = new ArrayList<Integer>();
		for(int i = 0; i < indices.size(); ++i) {
			retval.add(originalArray[(int)indices.get(i)]);
		}
		return retval.toArray(new Integer[0]);
	}
	
	/**
	 * This serves as a key to the DP table of stored values
	 */
	static class Key {
		Key(int index, int k) {
			this.index = index;
			this.k = k;
		}
		int index;
		int k;
		public boolean equals(Object ob) {
			if(ob == null) return false;
			if(!(ob instanceof Key)) return false;
			Key key = (Key)ob;
			return k == key.k && index == key.index;
		}
		public int hashCode() {
			return 3 * k + 5 * index;
		}
	}
}
