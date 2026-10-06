

import java.util.List;

import es.uma.ada.problem.sorting.SortingAlgorithm;

/**
 * Quicksort
 * 
 * @author ccottap
 * @version 1.0
 *
 */
public class QuickSort extends SortingAlgorithm {

	/*
	 * (non-Javadoc)
	 * 
	 * @see sortingAlgorithms.SortingAlgorithm#sort(int[],int,int)
	 */
	@Override
	protected <E extends Comparable<? super E>> void _sort(List<E> A, int l, int r) {
		// TODO complete this method
		
	}

	/**
	 * Rearranges the elements in A so that all elements A[l...m-1] {@literal <=}
	 * A[m] {@literal<=} A[m+1...r]
	 * 
	 * @param <E> the class of the elements in the list
	 * @param A   the list
	 * @param l   left index
	 * @param r   right index
	 * @return the index m at which the list is divided
	 */
	protected <E extends Comparable<? super E>> int divide(List<E> A, int l, int r) {
		// TODO complete this method
				
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sortingAlgorithms.SortingAlgorithm#getName()
	 */
	@Override
	public String getName() {
		return "quicksort";
	}

}
