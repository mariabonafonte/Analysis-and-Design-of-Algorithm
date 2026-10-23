import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BoardPath {
    private List<String> path;
    
    public BoardPath() {
        path = new LinkedList<String>();
    }
        
    public void add(int i, int j) {
        path.add("(" + i + "," + j + ")");
    }
    
    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (path.size() > 0) {
            Iterator<String> it = path.iterator();
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(" -> ");
                sb.append(it.next());
            }    
        }    
        return sb.toString();
    }
}