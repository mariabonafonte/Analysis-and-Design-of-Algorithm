import java.util.List;
import java.io.IOException;

public class RepeatedElementProblem {
	public static void main(String[] a) {
		
		try
		{
			List<RepeatedElementTest> tests = RepeatedElementTest.ReadTests();
		
			for(RepeatedElementTest test : tests)
			{
				Integer[] v = new Integer[test.V.size()];
				test.V.toArray(v);
		    
				int res = RepeatedElementsArray.FindElement(v);
	        
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