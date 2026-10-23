import java.util.List;
import java.io.IOException;

public class TariffDelivery {
	public static void main(String[] a) {
		
		try
		{
			List<PhoneTariffTest> tests = PhoneTariffTest.ReadTests();
		
			for(PhoneTariffTest test : tests)
			{
			  	    
				PhoneTariff p = new PhoneTariff(test.F,test.K,test.M,test.E);
				int minCost = p.solveBottomUp();
				List<Integer> solution = p.reconstructSolution();
    	    
				
					        
				if(test.Cost == minCost && solution.toString().equals(test.Solution.toString()))
					System.out.println(test.Name + " -> Passed");
				else
				{
					System.out.println(test.Name + " -> Failed");
					System.out.println("      Output: ");
					System.out.println("Cost= " + minCost); 
					System.out.println("Plan= " + solution.toString());
					System.out.println("      Expected: ");
					System.out.println("Cost= " + test.Cost); 
					System.out.println("Plan= " + test.Solution.toString());
				}
			}
		}
		catch(IOException e) {}
	}
	

	
	

}