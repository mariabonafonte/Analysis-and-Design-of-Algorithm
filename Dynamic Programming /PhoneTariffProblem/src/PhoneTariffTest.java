import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PhoneTariffTest {
	public String Name;
	public int F;
	public int K;	
	public int M;
	public List<Integer> E;
	public int Cost;
	public List<Integer> Solution;
	
	public PhoneTariffTest() 
	{
		Name ="";		
	}
	
	public static List<PhoneTariffTest> ReadTests() throws IOException
	{
		List<PhoneTariffTest> tests = new ArrayList<PhoneTariffTest>();
		try (Scanner sc = new Scanner(Path.of("tests", "tests.txt"))) {
            // Delimiter splits input by spaces, commas, brackets, equals sign, and newlines
			sc.useDelimiter("[ ,=\r\n\\[\\]]+");

            while (sc.hasNext()) {
                String token = sc.next();

                    	           	        
                // Detect start of a new test case (e.g., "Test", "0")
                if (token.equals("Case")) {
                	PhoneTariffTest test = new PhoneTariffTest();
                    test.Name = sc.next() + "_" + sc.next(); // Read the test number
                    
                    sc.next(); // Skip "Input" label
                    sc.next(); // Skip "M" label                    
                    test.M = sc.nextInt();
                    sc.next(); // Skip "F" label
                    test.F = sc.nextInt();
                    sc.next(); // Skip "K" label
                    test.K = sc.nextInt();                    
                    test.E = new ArrayList<>();
                    sc.next(); // Skip "d" label
                    while (sc.hasNextInt()) {
                        test.E.add(sc.nextInt());
                    }
        	        
                    sc.next(); // Skip "Output" label
                    sc.next(); // Skip "Cost" label
        	        test.Cost = sc.nextInt();
        	        sc.next(); // Skip "Plan" label
        	        test.Solution = new ArrayList<Integer>();
        	        while (sc.hasNextInt()) {
                        test.Solution.add(sc.nextInt());
                    }
        	        
        	        tests.add(test);
                }
            }
        } catch (FileNotFoundException e) {}
		return tests;
	}
}
