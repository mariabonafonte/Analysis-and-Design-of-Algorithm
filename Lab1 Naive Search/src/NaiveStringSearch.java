import es.uma.ada.problem.combinatorial.sequence.search.StringSearchingAlgorithm;
import java.util.LinkedList;
import java.util.List;

/**
 * Brute-force search of a substring
 * @author ccottap
 *
 */
public class NaiveStringSearch extends StringSearchingAlgorithm {

	/**
	 * Default constructor
	 */
	public NaiveStringSearch() {
		super();
	}

	@Override
	protected List<Integer> _run(String t, String w) {
		List<Integer> positions = new LinkedList<Integer>();
		
		// TODO
		// Complete this function
		//Remember to count the number of comparisons using the inherited "comparisons" variable
		
		return positions;
	}

	@Override
	public String getName() {
		return "NaiveStringSearch";
	}

}
