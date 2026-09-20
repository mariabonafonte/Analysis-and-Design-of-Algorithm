public class MatrixMultiplicationBruteForce {
	
	public static Matrix Multiply(Matrix a, Matrix b) {
		//Complete your code here
		if(a.n != b.m){
			throw new IllegalArgumentException("Incompatible matrix dimensions");
		}
		Matrix c = new Matrix(a.m, b.n);
		for(int i = 0; i < a.m; i++){
			for(int j = 0; j < b.n; j++){
				double sum = 0;

				 for(int k = 0; k < a.n; k++){
					sum += a.data[i][k] * b.data[k][j];
				 }
				 c.data[i][j] = sum;
				}
			}
		return c;
		
	}
	
}