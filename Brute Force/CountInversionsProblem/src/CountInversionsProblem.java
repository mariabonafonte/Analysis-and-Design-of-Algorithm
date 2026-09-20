import java.util.List;
import java.io.IOException;


public class CountInversionsProblem {
	public static void main(String[] a) {
		
		try
		{
			List<CountInversionsTest> tests = CountInversionsTest.ReadTests();
		
			for(CountInversionsTest test : tests)
			{
				Integer[] v = new Integer[test.V.size()];
				test.V.toArray(v);
		    
				int res = InversionsCounterBruteForce.CountInversions(v);
	        
				if(res == test.Output)
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