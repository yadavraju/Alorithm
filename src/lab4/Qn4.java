package lab4;

public class Qn4 {
	
	public static boolean checkIndex(int[] a)
	{
		for (int i = 0;i<=a.length-1;i++) {
			if(i==a[i]){
				return true;
			}
		}
		return false;
	}
	public static boolean middle(int [] a , int lower, int upper){
		
		if(lower > upper){
			return false;
		}
		int mid = (lower + upper)/2;
		if(a[mid] == mid){
			return true;
		}
		else if(mid < a[mid]){
			return middle(a,mid+1,upper);
		}else{
			return middle(a,lower,mid-1);
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [] a = {1,1};
		System.out.println(middle(a,0,a.length-1));

	}

}
