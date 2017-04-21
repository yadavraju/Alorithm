package lecture_10.subsetsum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * No memoization. Base case is when size of S is 0.
 * No intermediate sum computations. No print statements.
 * 
 * This is the recursive version that is exponentially slow.
 *
 */
public class RecursiveSS_Clean {
	static int[] test = { 2, 5, 123, 48, 29, 19, 34, 10, 20, 88, 47, 19, 21,
			42, 4, 8, 3, 7, 5, 9, 14, 26, 25, 31, 33, 18, 44, 35, 48, 53, 51,
			61, 69, 72 };
	
	public static void main(String[] args) {
		int k = 611;
		RecursiveSS_Clean ss = new RecursiveSS_Clean();
		LinkedList<Integer> result = ss.subsetsum(test, k);
		System.out.println("Input values: " + Arrays.toString(test) + ", k = "
				+ k);
		System.out.println("Indices: " + result.toString());
		System.out
				.println("Values: " + Arrays.toString(valuesOf(test, result)));
	}

	public LinkedList<Integer> subsetsum(int[] S, int k) {
		if(S == null) 
			throw new IllegalArgumentException("Null input");
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

		LinkedList<Integer> t1 
		    = recSubsetSum(S, newLen, k);
		if (t1 != ConstantsLinkedList.NULL) {
			return t1;
		}
		LinkedList<Integer> t2 
		    = recSubsetSum(S, newLen, k - last);
		if (t2 != ConstantsLinkedList.NULL) {
			return adjoin(t2, newLen);
		}
		return ConstantsLinkedList.NULL;
	}
	LinkedList<Integer> adjoin(LinkedList<Integer> T, int i) {
		if (T == ConstantsLinkedList.NULL)
			return T;
		T.add(i);
		return T;
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

}
