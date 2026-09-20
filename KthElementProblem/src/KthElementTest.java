import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KthElementTest {
	public String Name;
	public int K;
	public List<Integer> V;
	public int Output;
	
	public KthElementTest() 
	{
		Name ="";
		K = 0;
		V = new ArrayList<Integer>();
		Output = 0;
	}
	
	public static List<KthElementTest> ReadTests() throws IOException
	{
		List<KthElementTest> tests = new ArrayList<KthElementTest>();
		try (Scanner sc = new Scanner(Path.of("tests", "tests.txt"))) {
            // Delimiter splits input by spaces, commas, brackets, equals sign, and newlines
            sc.useDelimiter("[ =,\r\n\\[\\]]+");

            while (sc.hasNext()) {
                String token = sc.next();

                // Detect start of a new test case (e.g., "Test", "0")
                if (token.equals("Case")) {
                    KthElementTest test = new KthElementTest();
                    test.Name = sc.next() + "_" + sc.next(); // Read the test number

                    // Read K
                    sc.next(); // Skip "Input" label
                    sc.next(); // Skip "K" label
                    test.K = sc.nextInt();

                    // Read array V
                    sc.next(); // Skip "v" label
                    while (sc.hasNextInt()) {
                        test.V.add(sc.nextInt());
                    }

                    // Read Expected Output
                    sc.next(); // Skip "Output" label
                    // Strip extra quotes surrounding the output string if present (e.g., "-9" -> -9)
                    test.Output = Integer.parseInt(sc.next().replace("\"", ""));

                    tests.add(test);
                }
            }
        } catch (FileNotFoundException e) {}
		return tests;
	}
}
