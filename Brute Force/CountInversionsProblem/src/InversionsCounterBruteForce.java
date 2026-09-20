public class InversionsCounterBruteForce {
	
	public static int CountInversions(Integer[] v) {
		//Complete your code here
		int count = 0;
		int n = v.length;

		for (int i = 0; i < n - 1; i++) {
			int a = v[i];
			for (int j = i + 1; j < n; j++) {
				if (a > v[j]) {
					count++;
				}
			}
		}
		return count;
	}
	
}