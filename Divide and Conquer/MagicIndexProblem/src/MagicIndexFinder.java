public class MagicIndexFinder {
    
    public static int FindMagicIndex(Integer[] v) {
		return findMagicIndex_Rec(v,0,v.length-1);
	}
	
	private static int findMagicIndex_Rec(Integer[] v, int left, int right) {
		//Complete your code here
		if(left>right){
		    return -1;
		}
		int mid = (left+right)/2;
		if(v[mid]==mid){
		    int better = findMagicIndex_Rec(v, left, mid - 1);
            return (better != -1) ? better : mid;
		}else if (v[mid] > mid){
		    return findMagicIndex_Rec(v, left, mid-1);
		}else{
		    return findMagicIndex_Rec(v, mid+1, right);
		}
	}
}