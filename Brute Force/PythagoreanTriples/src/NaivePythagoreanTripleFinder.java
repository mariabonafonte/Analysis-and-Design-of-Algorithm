

import java.util.LinkedList;
import java.util.List;

import es.uma.ada.problem.algebra.diophantine.DiophantineUtil;
import es.uma.ada.problem.algebra.diophantine.pythagorean.PythagoreanTriple;
import es.uma.ada.problem.algebra.diophantine.pythagorean.PythagoreanTripleFinder;

/**
 * Finds primitive Pythagorean triples by naive brute force, i.e., by considering all possible values
 * under the limit 
 * @author ccottap
 * @version 1.0
 */
public class NaivePythagoreanTripleFinder extends PythagoreanTripleFinder {


	@Override
	protected List<PythagoreanTriple> _run(long l) {
		LinkedList<PythagoreanTriple> lp = new LinkedList<PythagoreanTriple>();
		// TODO Complete this function
		long limit2 = l*l;

		for(long a=1; 2*a*a < limit2; a++) {
			for(long b=a+1; a*a+b*b <= limit2; b+=2) {
				long h = a*a + b*b;
				long c = (long) Math.sqrt(h);
				if(c*c == h && DiophantineUtil.gcd(a, b) == 1) {
					lp.add(new PythagoreanTriple(a, b, c));
				}
			}
		}

		return lp;
	}

	@Override
	public String getName() {
		return "NaivePythagoreanTripleFinder";
	}

}