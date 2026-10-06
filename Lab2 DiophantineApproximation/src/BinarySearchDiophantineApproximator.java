

import es.uma.eda.problem.algebra.diophantine.RationalNumber;
import es.uma.eda.problem.algebra.diophantine.approximation.DiophantineApproximator;

/**
 * Generic procedure for conducting binary search to find the rational approximation
 * of a floating-point number. The actual computation of the middle point is delegated
 * to derived classes.
 * @author ccottap
 *
 */
public class BinarySearchDiophantineApproximator extends DiophantineApproximator {		
	/**
	 * rational number 0
	 */
	protected static final RationalNumber zero = new RationalNumber(0,1);
	/**
	 * rational number 1
	 */
	protected static final RationalNumber one = new RationalNumber(1,1);

	@Override
	public String getName() {
		return "Mediant-Approximation";
	}
	
	@Override
	protected RationalNumber _approximate(double x, double epsilon) {
		RationalNumber L, R;				// rational numbers that enclose x
		RationalNumber M;					// the middle-ish point (a rational number between L and R).
		double Mfp;							// floating-point value of the middle-ish point
		double diff;						// difference between the middle-ish point and |x|

		if (x < epsilon)					// Optional: to have integers being approximated
			M = zero;						// in an exact way, and save computations in boundary
		else if (x > (1-epsilon))			// cases (x being too close to 0 or to 1). 
			M = one;
		else {
			L=zero;
			R=one;
			boolean found = false;
			M = getMiddlePoint(L,R);
			
			while(!found){
			    M.reduce();
			    Mfp = M.asDouble();
			    diff = Math.abs(x - Mfp);
			    
			    if(diff < epsilon){ 
			        found = true;
			    } else if (Mfp<x) {
			        M = getMiddlePoint(M,R);
			    } else{ 
			        M = getMiddlePoint(L,M);
			    }
		    }
		}
		return M;
	}
	
	/**
	 * Returns a rational number in between l and r
	 * @param l the left rational number
	 * @param r the right rational number
	 * @return a rational number between l and r
	 */
	//protected abstract RationalNumber getMiddlePoint(RationalNumber l, RationalNumber r);
	protected RationalNumber getMiddlePoint(RationalNumber l, RationalNumber r) {
		// TODO
		// Return the mediant of l and r.
		long a = l.numerator();
		long b = r.numerator();
		long c = l.denominator();
		long d = r.denominator();
		
		return new RationalNumber(a+b, c+d);

	}

}
