package lab6;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Qn2 {

	public static int[] qn2(int [] a){
		Arrays.sort(a);
		int n = a.length;
		int newA [] = new int[a.length];
		int j=0;
		for(int i =0;i<a.length/2;i++){
			newA[j++]=a[i];
			newA[j++] = a[n-1-i];
			newA[newA.length-1] = a[a.length/2];
		}

		return newA;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = { 1, 2, 17, -4, -6, 8, 25, 3};
		int[] b = qn2(a);
		System.out.println(Arrays.toString(b));

		// {-6, 17, -4, 8, 1, 2}.

	}

}
