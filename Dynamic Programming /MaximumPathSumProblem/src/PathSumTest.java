import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PathSumTest {
	public String Name;
	public int Row;
	public int Col;	
	public int[][] M;
	public int Sum;
	public BoardPath Solution;
	
	public PathSumTest() 
	{
		Name ="";		
	}
	
	public static List<PathSumTest> ReadTests() throws IOException
	{
		List<PathSumTest> tests = new ArrayList<PathSumTest>();
		try (Scanner sc = new Scanner(Path.of("tests", "tests.txt"))) {
            // Delimiter splits input by spaces, commas, brackets, equals sign, and newlines
			sc.useDelimiter("[ (),=\r\n\t\\[\\]]+");

            while (sc.hasNext()) {
                String token = sc.next();

                    	           	        
                // Detect start of a new test case (e.g., "Test", "0")
                if (token.equals("Case")) {
                	PathSumTest test = new PathSumTest();
                    test.Name = sc.next() + "_" + sc.next(); // Read the test number
                    
                    sc.next(); // Skip "Input" label
                    sc.next(); // Skip "Position" label                    
                    test.Row = sc.nextInt();
                    test.Col = sc.nextInt();
                    sc.next(); // Skip "Rows" label
                    int n = sc.nextInt();                    
                    sc.next(); // Skip "Cols" label
					int m = sc.nextInt();                    
					test.M = new int[n][m];
					for (int i = 0; i < n; i++) {
						for (int j = 0; j < m; j++) {
							// System.out.println(sc.next());
							test.M[i][j] = sc.nextInt();
						}
					}					
					
					sc.next(); // Skip "Output" label
                    sc.next(); // Skip "Value" label
					test.Sum = sc.nextInt();                    
        	        
        	        sc.next(); // Skip "PAth" label
        	        test.Solution = new BoardPath();
        	        for(int k= 0;k<n;++k)
        	        {
        	        	test.Solution.add(sc.nextInt(),sc.nextInt());
        	        	sc.next();//skip "->"
        	        }
                    
        	        
        	        tests.add(test);
                }
            }
        } catch (FileNotFoundException e) {}
		return tests;
	}
}
