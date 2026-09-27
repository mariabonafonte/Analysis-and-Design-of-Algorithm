public class InversionsCounter {
	
	public static int CountInversions(Integer[] v) {
		return countInv(v,0,v.length-1);
	}
	
	private static int countInv(Integer[] a, int left, int right) {
		//Complete your code here
		if(left>=right) {
			return 0;
		}
		int mid = left + (right- left)/2;
		int count = 0;
		
		// Divide: recursively count inversions in the left and right halves
		count += countInv(a, left, mid);
		count += countInv(a, mid + 1, right);
		
		// Conquer / Combine: merge the two sorted halves and count split inversions
		Integer[] temp = new Integer[right - left + 1];
		int i = left;      // Pointer for the left half
		int j = mid + 1;   // Pointer for the right half
		int k = 0;         // Pointer for the temporary array

		while (i <= mid && j <= right) {
			if (a[i] <= a[j]) {
				temp[k++] = a[i++];
			} else {
				temp[k++] = a[j++];
		// If a[j] is smaller than a[i], it's smaller than all remaining elements in the left half
				count += (mid - i + 1);
			}
		}

		// Copy any remaining elements from the left half
		while (i <= mid) {
			temp[k++] = a[i++];
		}

		// Copy any remaining elements from the right half
		while (j <= right) {
			temp[k++] = a[j++];
		}

		// Transfer the sorted elements back into the original array
		for (i = left, k = 0; i <= right; i++, k++) {
			a[i] = temp[k];
		}

		return count;
	}
	
}