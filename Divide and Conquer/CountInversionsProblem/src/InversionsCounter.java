public class InversionsCounter {
	
	public static int CountInversions(Integer[] v) {
		return countInv(v,0,v.length-1);
	}
	
	private static int countInv(Integer[] a, int left, int right) {
		//Complete your code here
				if(left>=right) return 0;
				//medium variable
				int mid = left + (right-left)/2;
				int count = countInv(a, left, mid) + countInv(a, mid+1, right);
				
				Integer[] L = new Integer[mid - left + 1];
		        Integer[] R = new Integer[right - mid];
		        for (int x = 0; x < L.length; x++) L[x] = a[left + x];
		        for (int x = 0; x < R.length; x++) R[x] = a[mid + 1 + x];
				
				int i = 0, j = 0, k = left;
				while (i < L.length && j < R.length) {
					if (L[i] <= R[j]) {
						a[k++] = L[i++];
					} else {
						a[k++] = R[j++];
						count += L.length - i; // everything left in L is > R[j]
					}
				}
				
				while (i < L.length) a[k++] = L[i++];
				while (j < R.length) a[k++] = R[j++];

				return count;
	}
	
}