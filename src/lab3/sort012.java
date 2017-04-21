package lab3;

public class sort012 {
	static int[] sort(int arr[], int n) {
		int temp;
		for (int i = 1; i < n; i++) {
			System.out.println("N" + i);

			if (arr[i] < arr[i - 1]) {
				temp = arr[i];
				arr[i] = arr[i - 1];
				arr[i - 1] = temp;
				i = 0;

			}
		}
		return arr;

	}

	// Sort the input array, the array is assumed to
	// have values in {0, 1, 2}
	static void sort012(int arr[], int arr_size) {

	    int zero = 0;
	    int one =0;

	    for(int i=0;i<arr_size; i++){
	        if(arr[i]==0 ) {
	        	zero++;
	        }if(arr[i]==1) {
				one++;
			} 
	    }
	    for(int j=0;j<arr_size; j++){
			if(j<zero)
				arr[j]=0;
			else if(j<(zero+one)){
				arr[j]=1;
			}else{
				arr[j] =2;
			}
		}

	}
	
	 public static void sort(int arr[]){
	       // boolean flag = true;
	        for(int i=0; i < arr.length; ++i ){
	        	 boolean flag = true;
	            for(int j=0; j<arr.length-1-i; ++j){
	                if(arr[j]>arr[j+1]){
	                    int temp = arr[j];
	                    arr[j] = arr[j+1];
	                    arr[j+1] = temp;
	                    flag=false;
	                }
	            }
	 
	            if(flag){
	                break;
	            }
	        }
	    }

	/* Utility function to print array arr[] */
	static void printArray(int arr[], int arr_size) {
		int i;
		for (i = 0; i < arr_size; i++)
			System.out.print(arr[i] + " ");
		System.out.println("");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = { 0, 1, 2, 0, 1, 2,2,1,0,1 };
		//sort012(a, a.length);
		sort(a);
		printArray(a, a.length);

	}

}
