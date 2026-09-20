import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MatrixMultiplicationTest {
	public String Name;
	public Matrix A;
	public Matrix B;
	public Matrix Output;
	
	public MatrixMultiplicationTest() 
	{
		Name ="";
	}
	
	public static List<MatrixMultiplicationTest> ReadTests() throws IOException
	{
		List<MatrixMultiplicationTest> tests = new ArrayList<MatrixMultiplicationTest>();
		try (Scanner sc = new Scanner(Path.of("tests", "tests.txt"))) {
            // Delimiter splits input by spaces, commas, brackets, equals sign, and newlines
            sc.useDelimiter("[ =,\r\n\\[\\]]+");

            while (sc.hasNext()) {
                String token = sc.next();

                // Detect start of a new test case (e.g., "Test", "0")
                if (token.equals("Case")) {
                	MatrixMultiplicationTest test = new MatrixMultiplicationTest();
                    test.Name = sc.next() + "_" + sc.next(); // Read the test number

                    // Read K
                    sc.next();//skip Input
                    sc.next();//skip matrix name
                    test.A = new Matrix(sc.nextInt(),sc.nextInt()); // size
                    readMatrix(test.A,sc);
                    sc.next();//skip matrix name
                    test.B = new Matrix(sc.nextInt(),sc.nextInt()); // size
                    readMatrix(test.B,sc);
                    sc.next();//skip matrix name
                    test.Output = new Matrix(sc.nextInt(),sc.nextInt()); // size
                    readMatrix(test.Output,sc);                 

                    tests.add(test);
                }
            }
        } catch (FileNotFoundException e) {}
		return tests;
	}
	
	public static void readMatrix(Matrix a, Scanner sc)
	{
		int line = 0;
        int column = 0;
        // 
        while (sc.hasNext() && line < a.m) {
            a.data[line][column++] = Double.parseDouble(sc.next());
            if(column%a.n == 0)
            {
            	line++;
            	column = 0;
            }
        }
	}
	
}
