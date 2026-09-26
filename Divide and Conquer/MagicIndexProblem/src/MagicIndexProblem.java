import java.util.List;
import java.io.IOException;

public class MagicIndexProblem {
	public static void main(String[] a) {
		
		try
		{
			List<MagicIndexTest> tests = MagicIndexTest.ReadTests();
		
			for(MagicIndexTest test : tests)
			{
				Integer[] v = new Integer[test.V.size()];
				test.V.toArray(v);
		    
				int res = MagicIndexFinder.FindMagicIndex(v);
	        
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