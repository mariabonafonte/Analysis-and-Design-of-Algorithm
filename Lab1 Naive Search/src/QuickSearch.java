/**
 * 
 */
import es.uma.ada.problem.combinatorial.sequence.search.StringSearchingAlgorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Quick search<br>
 * <a href="https://doi.org/10.1145/79173.79184">A very fast substring search algorithm</a>, Daniel M. Sunday, Communications of the ACM 33(8):132–142, 1990
 * @author ccottap
 *
 */
public class QuickSearch extends StringSearchingAlgorithm {

	/**
	 * Default constructor
	 */
	public QuickSearch() {
		super();
	}

	@Override
	protected List<Integer> _run(String t, String w) {
		List<Integer> positions = new LinkedList<Integer>();

		// TODO
		// Complete this function
		
		int m = w.length();
		int n = t.length();
		int last = n - m;
		
		HashMap<Character, Integer >shift = new HashMap<Character, Integer>();
		for (int i=0; i<m; i++) 
			shift.put(w.charAt(i), m-i);
				
		int i = 0;
		while (i <= last) {
			if (verbosity) {
				System.out.println("Checking at position " + i + ": ");
				System.out.println(t);
				for (int k=0; k<i; k++)
					System.out.print(" ");
				System.out.println(w);
			}
			int j = 0;
			while ((j<m) && (t.charAt(i+j) == w.charAt(j))) {
				j++;
			}
			comparisons += j; 
			if (j >= m) {
				if (verbosity)
					System.out.println("Match found at " + i + " (" + comparisons + " comparisons so far)");
				positions.add(i);
			}
			else {
				comparisons++; // to count the last comparison that made the loop terminate
				if (verbosity)
					System.out.println((j+1) + " comparisons (" + comparisons + " so far)");
			}
			if (i < last) {
				Integer d = shift.get(t.charAt(i+m));
				if (d != null)
					i += d;
				else 
					i += m+1;
			}
			else
				break;
		}
	
		
		return positions;
	}

	@Override
	public String getName() {
		return "QuickSearch";
	}

}
