import java.util.List;
import java.io.IOException;

public class AntennaProblem {
	public static void main(String[] a) {
		
		try
		{
			List<AntennaTest> tests = AntennaTest.ReadTests();
		
			for(AntennaTest test : tests)
			{
			  	    
				 AntennaStructure sol = Antenna.antenna(test.weight,test.height , test.quantity, test.M);
			
					        
				if(test.isSolution(sol))
					System.out.println(test.Name + " -> Passed");
				else
				{
					System.out.println(test.Name + " -> Failed");
					System.out.println("      Output: "); 
					System.out.println(sol.toString());
					System.out.println("      Expected (one of the following): ");
					for(int i = 0; i < test.Solutions.size();++i)
						System.out.println("sol " + (i+1) + ": " + test.Solutions.get(i).toString());
				}
			}
		}
		catch(IOException e) {}
	}
	

	
	

}