import java.io.IOException;


/**
 * Lab Project for Unit 1: Brute Force
 * @author ccottap
 *
 */
public class LabProject1 {

	/**
	 * Main method to run the lab project.
	 * @param args command-line arguments. The options are:
	 * <ul>
	 * <li>algorithm : the string search algorithm (naive, quick)</li>
	 * <li>-s <i>text pattern</i> : searches a pattern in the text.</li>
	 * <li>-r <i>alphabetSize textSizeInitial textNumSteps patternSizeInitial patternNumSteps numTests</i> : runs tests of the algorithm considering texts and patterns of increasing size.
	 * For each size of text and pattern <i>numTests</i> random text/patterns are generated, and searches are done. The number of character comparisons is measured.</li>
	 * </ul>
	 * @throws IOException if data cannot be read/written from/to disk.
	 */
	public static void main(String[] args) throws IOException {
		TestBruteForceStringSearch.run(args);
		try
		{
			AnalysisResult.createCharts();			
		}
		catch (Exception e) {}
	}

}
