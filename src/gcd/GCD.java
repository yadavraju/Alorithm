package gcd;

public class GCD {

	public static int gcd(int m, int n) {
		int temp;
		if (m < n) {
			temp = m;
			m = n;
			n = temp;
		}
		if (n == 0) {
			return m;
		}
		if (m % n == 0) {
			return n;
		} else {
			return gcd(n, m % n);
		}
	}

	/*
	 * * Java method to find GCD of two number using Euclid's method * @return
	 * GDC of two numbers in Java
	 */
	private static int findGCD(int number1, int number2) { // base case
		if (number2 == 0) {
			return number1;
		}
		return findGCD(number2, number1 % number2);
	}

	// Returns true if there is a subset of set[] with sum
	// equal to given sum
	static boolean isSubsetSum(int set[], int n, int sum) {
		// Base Cases
		if (sum == 0)
			return true;
		if (n == 0 && sum != 0)
			return false;

		// If last element is greater than sum, then ignore it
		if (set[n - 1] > sum)
			return isSubsetSum(set, n - 1, sum);

		/*
		 * else, check if sum can be obtained by any of the following (a)
		 * including the last element (b) excluding the last element
		 */
		return isSubsetSum(set, n - 1, sum) || isSubsetSum(set, n - 1, sum - set[n - 1]);
	}

	/* Driver program to test above function */
	public static void main(String args[]) {
		int set[] = { 3, 34, 4, 12, 5, 2 };
		int sum = 7;
		int n = set.length;
		if (isSubsetSum(set, n, sum) == true)
			System.out.println("Found a subset with given sum");
		else
			System.out.println("No subset with given sum");
	}

}
