import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AntennaTest {
	public String Name;
	public int[] weight;
	public int [] height;
	public int[] quantity;
	public int M;
	
	public List<AntennaStructure> Solutions;
	
	
	public AntennaTest() 
	{
		Name ="";		
	}
	
	public boolean isSolution(AntennaStructure antenna)
	{
		for(int i = 0;i< Solutions.size();++i)
			if(antenna.equals(Solutions.get(i)))
					return true;
			return false;
	}
	
	public static List<AntennaTest> ReadTests() throws IOException
	{
		List<AntennaTest> tests = new ArrayList<AntennaTest>();
		try (Scanner sc = new Scanner(Path.of("tests", "tests.txt"))) {
            // Delimiter splits input by spaces, commas, brackets, equals sign, and newlines
			sc.useDelimiter("[ ():=\r\n\t\\[\\]]+");

            while (sc.hasNext()) {
                String token = sc.next();

                    	           	        
                // Detect start of a new test case (e.g., "Test", "0")
                if (token.equals("Case")) {
                	AntennaTest test = new AntennaTest();
                    test.Name = sc.next() + "_" + sc.next(); // Read the test number
                    
                    sc.next(); // Skip "Input" label
                    
                    String[] sWeight = sc.next().split(",");
                    test.weight = new int[sWeight.length];
                    for (int i = 0; i < test.weight.length; i++) test.weight[i] = Integer.parseInt(sWeight[i]);
                    
                    String[] sHeight = sc.next().split(",");
                    test.height = new int[sHeight.length];
                    for (int i = 0; i < test.height.length; i++) test.height[i] = Integer.parseInt(sHeight[i]);
                    
                    String[] sQuantity = sc.next().split(",");
                    test.quantity = new int[sQuantity.length];                    
                    for (int i = 0; i < test.quantity.length; i++) test.quantity[i] = Integer.parseInt(sQuantity[i]);
                    
                    test.M = sc.nextInt();
                                               							
					sc.next(); // Skip "Output" label
					
                    int nbSol = sc.nextInt();
                    test.Solutions = new ArrayList<AntennaStructure>();
                    for(int k=0;k<nbSol;++k)
                    {
			            int w = sc.nextInt();
			            String[] ss = sc.next().split(",");
	                    int[] s  = new int[ss.length];
	                    for (int i = 0; i < s.length; i++) s[i] = Integer.parseInt(ss[i]);
                    	test.Solutions.add(new AntennaStructure(w,s));
                    }
        	                         
  
        	        tests.add(test);
                }
            }
        } catch (FileNotFoundException e) {}
		return tests;
	}
}
