public class BoardTraversal {
    private int[][] board; 
    private int[][] solution;
    private int row; // Row of the starting cell
    private int col; // Column of the starting cell
    private int n;   
    private int m;

    public BoardTraversal(int[][] t, int row, int col) {
        board = t;
        n = board.length;
        m = board[0].length;
        this.row = row;
        this.col = col;
        solution = null;
    }

    public int solveMemo() {
        // Create the auxiliary table
        solution = new int[n][m]; // -1 represents an unvisited/empty cell.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                solution[i][j] = -1;
            }
        }
        // Fill the table starting from the specified cell
        return solve(row, col);
    }

    private int maxOf3(int a, int b, int c) {
        int res = a;
        if (b > res) {
            res = b;
        }
        if (c > res) {
            res = c;
        }
        return res;
    }
    
    private int solve(int i, int j) {
        
        //***Complete Implementation****
    }


    public BoardPath reconstructSolution() {
        if (solution == null) {
            throw new RuntimeException("The problem must be solved first");
        }
        
        //***Complete Implementation***
    }

}