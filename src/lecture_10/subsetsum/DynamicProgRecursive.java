package lecture_10.subsetsum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This version uses a memoization table,
 * and self-calls are tracked and displayed to console
 * 
 * This is an implementation of the exponential (recursive) version of the
 * algorithm. This recursive version has the advantage of making evident what
 * the subproblems are.
 * 
 * Subproblems are finding the two possible summations over initial segments of
 * {0,1,...,n-1}, checking if either is equal to k.
 * 
 * Running time: In the worst case, k is the sum of all numbers in S. We can
 * count self-calls to show there are at least 2^n of them, and that therefore
 * running time is exponential.
 * 
 * In each self-call, two other self-calls are made. At least O(1) work is done
 * in each self-call. Self-calls continue testing sets {s_0, s_1...s_n-1}, {s_0,
 * s_1, ..., s _n-2}, {s_0, s_1,...s_n-3}, . . . , {s_0}. This gives us (in the
 * worst case) a completely filled binary tree; since height is n, number of
 * nodes is > 2^n. Each node represents a self-call. Therefore, T(n) is
 * Omega(2^n).
 */
public class DynamicProgRecursive {
	static int numSelfCalls = 0;
	static int[] test = { 2, 5, 123, 48, 29, 19, 34, 10, 20, 88, 47, 19, 21,
			42, 4, 8, 3, 7, 5, 9, 14, 26, 25, 31, 33, 18, 44, 35, 48, 53, 51,
			61, 69, 72 };
	// tracks how many times a subset of test array is used in a computation
	static HashMap<Integer, Integer> map = new HashMap<>();
	private static void incrementMap(int len) {
		if(map.containsKey(len)) {
			map.put(len, map.get(len) + 1);
		} else {
			map.put(len,  1);
		}
	}
	public static void main(String[] args) {
		int k = 611;
		DynamicProgRecursive ss = new DynamicProgRecursive();
		
		LinkedList<Integer> result = ss.subsetsum(test, k);
		System.out.println("Input values: " + Arrays.toString(test) + ", k = " + k);
		System.out.println("Indices: " + result);
		System.out.println("Values: " + Arrays.toString(valuesOf(test, result)));
		System.out.println();
		
		for(Integer key : map.keySet()) {
			String message = "Subset s0, . . ., s" + key
					+ " examined " + map.get(key) + " times";
			System.out.println(message);
		}
		System.out.println("Num self-calls: " + numSelfCalls);
		System.out.println("Num subproblems: " + (k+1)* test.length);
	}
	
	/** memoization table */
	static HashMap<Key, LinkedList<Integer>> table = new HashMap<>();

	public LinkedList<Integer> subsetsum(int[] S, int k) {
		if(S == null) throw new IllegalArgumentException("Null input");
		return recSubsetSum(S, S.length, k);
	}
	
	LinkedList<Integer> recSubsetSum(int[] S, int len, int k) {
		/* for tracking */
		++numSelfCalls;
		incrementMap(len);
		/* end tracking */
		
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
		
		//if possible, read stored computation
		Key key = new Key(newLen,k);
		LinkedList<Integer> t1 = table.get(key);
		
		if(t1 == null) { // not found in table
			t1 = recSubsetSum(S, newLen, k);
			table.put(key, t1);
		}
		//Don't return NULL unless neither of the self-calls 
		//produces a non-NULL result
		if (t1 != ConstantsLinkedList.NULL) {
			return t1;
		}
		
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

	private static Integer[] valuesOf(int[] originalArray,
			LinkedList<Integer> indices) {
		if(indices.equals(ConstantsLinkedList.NULL)) return null;
		List<Integer> retval = new ArrayList<Integer>();
		for (int i = 0; i < indices.size(); ++i) {
			retval.add(originalArray[(int) indices.get(i)]);
		}
		return retval.toArray(new Integer[0]);
	}

	
	LinkedList<Integer> adjoin(LinkedList<Integer> T, int i) {
		if (T == ConstantsLinkedList.NULL)
			return T;
		T.add(i);
		return T;
	}
	// for demo purposes
	static ArrayList<Integer> arrToList(int[] arr) {
		ArrayList<Integer> ls = new ArrayList<>();
		for (int i : arr) {
			ls.add(i);
		}
		return ls;

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
