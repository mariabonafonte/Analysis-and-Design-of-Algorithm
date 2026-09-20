import java.util.List;
import java.io.IOException;


public class MatrixMultiplicationProblem {
	public static void main(String[] a) {
		
		try
		{
			List<MatrixMultiplicationTest> tests = MatrixMultiplicationTest.ReadTests();
		
			for(MatrixMultiplicationTest test : tests)
			{
				Matrix res = MatrixMultiplicationBruteForce.Multiply(test.A,test.B);
	        
				if(res.equals(test.Output))
					System.out.println(test.Name + " -> Passed");
				else
				{
					System.out.println(test.Name + " -> Failed");
					System.out.println("      Output: ");
					System.out.println(res.toString());
					System.out.println("      Expected: ");
					System.out.println(test.Output.toString());
				}
			}
		}
		catch(IOException e) {}
	}
	

	
	

}