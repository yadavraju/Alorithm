package lecture_10.fastfib;

public class FastFib {
	int[] table;
	public int fib(int n) {
		if(n < 0) return -1;
		table = new int[n+1];
		for(int i = 0; i <= n; ++i) {
			table[i]--;
		}
		return subprobFib(n);
	}
	
	private int subprobFib(int m) {
		if(m == 0 || m == 1) { 
			table[m] = m;
			return table[m];
		}
		if(table[m-2] < 0) {
			table[m-2] = subprobFib(m-2);
			System.out.println("call to subprobFib on "+(m-2));
		}
		if(table[m-1] < 0) {
			table[m-1] = subprobFib(m-1);
			System.out.println("call to subprobFib on "+(m-1));
		}
		table[m] = table[m-1] + table[m-2];
		return table[m];
	}
	
	public static void main(String[] args) {
		FastFib f = new FastFib();
		System.out.println(f.fib(6));
	}
}
