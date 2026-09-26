public class KthElement {

	public static int FindKth(Integer[] v, int k) {
		return findKth(v, k, 0, v.length - 1);
	}

	private static int findKth(Integer[] v, int k, int left, int right) {
	 // Best Case: bounds are the same
		if(left==right) {
			return v[left];
		}
		int p = left;
		int l = left;
		int r = right;

		while (l <= r) {
			while (l <= r && v[l] <= v[p]) l++;
			while (l <= r && v[r] > v[p]) r--;
					
			if (l < r) {
				int temp = v[l];
				v[l] = v[r];
				v[r] = temp;
			}
		}

		int temp = v[p];
		v[p] = v[r];
		v[r] = temp;

		if (k == r) {
			return v[k];
		} else if (k < r) {
			return findKth(v, k, left, r - 1);
		} else {
			return findKth(v, k, r + 1, right);
		}
	}    	 	
}