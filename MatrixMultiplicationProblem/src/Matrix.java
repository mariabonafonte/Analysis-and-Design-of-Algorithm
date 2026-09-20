
public class Matrix {

	public int m; //lines
	public int n; //columns
	public double[][] data;
	
	public Matrix(int lines, int columns)
	{
		m = lines;
		n = columns;
		data = new double[m][n]; 
	}
	
	public boolean equals(Matrix a)
	{
		if(a.m != m || a.n != n)
			return false;
		
		for(int i = 0; i <m; ++i)
			for(int j = 0; j <n; ++j)
				if(data[i][j] != a.data[i][j])
					return false;
		return true;
	}
	
	public String toString()
	{
		String result = "";
		for(int i = 0; i <m; ++i)
		{
			result += "[ ";
			for(int j = 0; j <n; ++j)
					result += data[i][j] + " ";				
			result += "]\n";
		}
		return result;
	}
}
