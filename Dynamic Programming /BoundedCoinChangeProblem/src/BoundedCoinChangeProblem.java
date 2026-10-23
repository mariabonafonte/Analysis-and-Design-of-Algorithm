import java.util.List;
import java.io.IOException;

public class BoundedCoinChangeProblem {
	public static void main(String[] a) {
		
		try
		{
			List<BoundedCoinChangeTest> tests = BoundedCoinChangeTest.ReadTests();
		
			for(BoundedCoinChangeTest test : tests)
			{
			  	    
				BoundedCoinChange p = new BoundedCoinChange(test.M, test.values, test.quantities);
				int minNumCoins = p.solveBottomUp();
				int[] solution = p.reconstructSolution();
    	    
				String res = new CoinChangeResult(minNumCoins, solution).toString();
					        
				if(res.equals(test.Output.toString()))
					System.out.println(test.Name + " -> Passed");
				else
				{
					System.out.println(test.Name + " -> Failed");
					System.out.println("      Output: " + res);
					System.out.println("      Expected: " + test.Output);
				}
			}
		}
		catch(IOException e) {}
	}
	

	
	

}