

import java.util.ArrayList;
import java.util.List;

import es.uma.ada.problem.sorting.SortingAlgorithm;

/**
 * Mergesort
 * 
 * @author ccottap
 * @version 1.0
 *
 */
public class MergeSort extends SortingAlgorithm {

	/*
	 * (non-Javadoc)
	 * 
	 * @see sortingAlgorithms.SortingAlgorithm#sort(int[],int,int)
	 */
	@Override
	protected <E extends Comparable<? super E>> void _sort(List<E> A, int l, int r) {
		// TODO complete this method
		if (l>=r) {
			return;
		}
		int m = (l+r)/2;
		List<E> B = new ArrayList<>(A.subList(l, m));
		List<E> C = new ArrayList<>(A.subList(m+1, r));
		merge(A,B,C);
		
		
				

	}

	/**
	 * Merges two sorted lists
	 * 
	 * @param <E> the class of the elements in the list
	 * @param A   the merged list
	 * @param B   a list to be merged
	 * @param C   another list to be merged
	 */
	private <E extends Comparable<? super E>> void merge(List<E> A, List<E> B, List<E> C) {
		// TODO complete this method	
		int i,j,k =1;
		int n = A.size();
		int p = B.size();
		int q = C.size();
		while((j<=p) && (k<=q)) {
			if (B.get(j)<=C.get(k)) {
				A.set(i, B.get(j));
				j++;
			} else {
				A.set(i, C.get(k));
				k++;
			}
			i++;
		} //endwhile
		if(j>p) {
			A = C.subList(k, q);
		}else {
			A = B.subList(j, p);
		}//Endif
		
				
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sortingAlgorithms.SortingAlgorithm#getName()
	 */
	@Override
	public String getName() {
		return "MergeSort";
	}


}
