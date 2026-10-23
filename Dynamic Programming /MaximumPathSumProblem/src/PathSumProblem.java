import java.util.List;
import java.io.IOException;

public class PathSumProblem {
	public static void main(String[] a) {
		
		try
		{
			List<PathSumTest> tests = PathSumTest.ReadTests();
		
			for(PathSumTest test : tests)
			{
			  	    
				 BoardTraversal p = new BoardTraversal(test.M, test.Row, test.Col);
		         int sum = p.solveMemo();
		            
		         BoardPath solution =  p.reconstructSolution();
    	    
				
					        
				if(test.Sum == sum && solution.toString().equals(test.Solution.toString()))
					System.out.println(test.Name + " -> Passed");
				else
				{
					System.out.println(test.Name + " -> Failed");
					System.out.println("      Output: ");
					System.out.println("Cost= " + sum); 
					System.out.println("Plan= " + solution.toString());
					System.out.println("      Expected: ");
					System.out.println("Cost= " + test.Sum); 
					System.out.println("Plan= " + test.Solution.toString());
				}
			}
		}
		catch(IOException e) {}
	}
	

	
	

}