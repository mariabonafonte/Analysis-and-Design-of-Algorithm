public class KthElement {

	public static int FindKth(Integer[] v, int k) {
		return findKth(v, k, 0, v.length - 1);
	}

	private static int findKth(Integer[] v, int k, int left, int right) {
	 // Best Case: bounds are the same
		if(left==right) {
			return v[left];
		}
		int base = left;

		while (left <= right) {
			while (left <= right && v[left] <= v[base]) left++;
			while (left <= right && v[right] > v[base]) right--;
					
			if (left < right) {
				int temp = v[left];
				v[left] = v[right];
				v[right] = temp;
			}
		}

		int temp = v[base];
		v[base] = v[right];
		v[right] = temp;

		if (k == right) {
			return v[k];
		} else if (k < right) {
			return findKth(v, k, left, right - 1);
		} else {
			return findKth(v, k, right + 1, right);
		}
	}    	 	
}