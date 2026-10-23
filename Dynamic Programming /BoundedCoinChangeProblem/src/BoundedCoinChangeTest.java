import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoundedCoinChangeTest {
	public String Name;
	public int[] values;
	public int[] quantities;
	public int M;
	public CoinChangeResult Output;
	
	public BoundedCoinChangeTest() 
	{
		Name ="";		
	}
	
	public static List<BoundedCoinChangeTest> ReadTests() throws IOException
	{
		List<BoundedCoinChangeTest> tests = new ArrayList<BoundedCoinChangeTest>();
		try (Scanner sc = new Scanner(Path.of("tests", "tests.txt"))) {
            // Delimiter splits input by spaces, commas, brackets, equals sign, and newlines
			sc.useDelimiter("[ =\r\n\\[\\]]+");

            while (sc.hasNext()) {
                String token = sc.next();

                    	           	        
                // Detect start of a new test case (e.g., "Test", "0")
                if (token.equals("Case")) {
                	BoundedCoinChangeTest test = new BoundedCoinChangeTest();
                    test.Name = sc.next() + "_" + sc.next(); // Read the test number
                    
                    sc.next(); // Skip "Input" label
                    
                    String aux = sc.next();//skip v=
        	        String[] svalue = sc.next().split(",");
            	    aux = sc.next();//skip q=
        	        String[] squantity = sc.next().split(",");
            	    aux = sc.next();//skip M=
            	    test.M = Integer.parseInt(sc.next());
        	        
        	        test.values = new int[svalue.length];
        	        test.quantities = new int[squantity.length];
            	    
            	    for (int i = 0 ; i < test.values.length; i++) test.values[i] = Integer.parseInt(svalue[i]);
            	    for (int i = 0 ; i < test.quantities.length; i++) test.quantities[i] = Integer.parseInt(squantity[i]);
            	    
                    // Read Expected Output
                    sc.next(); // Skip "Output" label
                    int val = sc.nextInt();
                    String[] results = sc.next().split(",");
                    int[] solution = new int[results.length];
                    for (int i = 0 ; i < solution.length; i++) solution[i] = Integer.parseInt(results[i]);
                    test.Output = new CoinChangeResult(val,solution);

                    tests.add(test);
                }
            }
        } catch (FileNotFoundException e) {}
		return tests;
	}
}
