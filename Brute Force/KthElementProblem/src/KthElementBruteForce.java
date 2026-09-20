public class KthElementBruteForce {

	public static int FindKth(Integer[] v, int k) {
		//Complete your code here
		int n = v.length;
		if(k<0 || k>=n) {
			throw new IndexOutOfBoundsException("k out of rage");
		}
		for (int i = 0; i<n; i++){
			int candidate = v[i];
			int less = 0;
			int equal = 0;

			for (int j = 0; j<n; j++){
				int x = v[j];
				if(x < candidate) {
					less++;
				} else if (x == candidate) {
					equal++;
				}
			}
			if(k >= less && k < less + equal) {
				return candidate;
			}
		}
		throw new IllegalStateException("No k-th element found");
	}
	
}