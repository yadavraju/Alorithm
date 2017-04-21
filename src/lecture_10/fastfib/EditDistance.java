package lecture_10.fastfib;

/**
 * 
 * @author RAJU YADAV Algorithm EditDistance(A,B) Input: two strings A = a1 ....
 *         an and B = b1 ... bm Output: the edit distance for A and B initiate a
 *         two dimensional array D[n+1][m+1] D[0][0] = 0 for i ← 1 to n do
 *         D[i][0] ← i for j ← 1 to m do D[0][j] ← j for i ← 1 to n do for j ← 1
 *         to m do if A[i] = B[j] then D[i][j] ← D[i-1][j-1] else D[i][j] ← min(
 *         D[i–1][j] + 1, D[i][j-1] + 1, D[i–1][j–1] + 1) return D[n][m]
 *
 */
public class EditDistance {

	public static int editDis(String w1, String w2) {
		int len1 = w1.length();
		int len2 = w2.length();
		int[][] D = new int[len1 + 1][len2 + 1];
		// D[0][0] = 0;
		for (int i = 0; i <= len1; i++) {
			D[i][0] = i;
		}
		for (int j = 0; j <= len2; j++) {
			D[0][j] = j;
		}
		for (int i = 1; i <= len1; i++) {
			int c1 = w1.charAt(i - 1);
			for (int j = 1; j <= len2; j++) {
				int c2 = w2.charAt(j - 1);
				if (c1 == c2) {
					D[i][j] = D[i - 1][j - 1];
				} else {
					int replace = D[i - 1][j - 1] + 1;
					int insert = D[i - 1][j] + 1;
					int delete = D[i][j - 1] + 1;

					int min = Math.min(replace, insert);
					min = Math.min(min, delete);
					D[i][j] = min;
				}
			}

		}
		return D[len1][len2];
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(editDis("duck", "tug"));

	}

}
