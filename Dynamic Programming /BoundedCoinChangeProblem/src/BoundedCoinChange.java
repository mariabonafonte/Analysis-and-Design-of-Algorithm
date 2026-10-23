public class BoundedCoinChange {
    private int M;
    private int[] v; // Coin values / denominations
    private int[] q; // Quantity of coins of each type
    private int[][] table; // Minimum number of coins. Since valid values are always non-negative,
                            // -1 will be used to represent infinity.
    private int[][] option; // Chosen k for the first decision of the subproblem.
    
    public BoundedCoinChange(int M, int[] v, int[] q) {
        this.M = M;
        this.v = v;
        this.q = q;
        table = null;
        option = null;
    }
    
    /**
     * Method to fill the table 
     */
    public int solveBottomUp() {
        table = new int[v.length][M + 1];
        option = new int[v.length][M + 1]; 
        // We fill the table top-to-bottom and left-to-right
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j <= M; j++) {
                if (j == 0) {
                    table[i][j] = 0;
                    option[i][j] = 0; // 0 coins used
                } else if (i == 0) {
                    if (j % v[i] != 0 || j / v[i] > q[i]) {
                        table[i][j] = -1;
                        option[i][j] = -1; // Infinite coins used (unreachable)
                    } else { // j % v[i] == 0 && j / v[i] <= q[i]

                        //**** Complete *******

                    }
                } else { // i > 0
                    // In M[i,j] we will keep storing the minimum found
                    table[i][j] = table[i - 1][j]; // 0 + M(i-1, j-0)
                    option[i][j] = 0;
                    int maxCoins = (int) Math.min(q[i], Math.floor((double) j / v[i]));
                    for (int k = 1; k <= maxCoins; k++) {
                        
                        //*** Complete *******

                    } // for k
                } // else
            } // for j
        } // for i
        return table[v.length - 1][M];
    }

    /**
     * Returns the sum of two numbers, considering that -1 represents infinity.
     */
    private int sumInf(int a, int b) {
        int sum = -1;
        if (a != -1 && b != -1) {
            sum = a + b;
        }
        return sum;
    }

    /**
     * Returns a < b, considering that -1 represents infinity.
     */
    private boolean lessThanInf(int a, int b) {
        boolean res = false;
        if (a != -1) {
            if (b == -1) {
                res = true;
            } else { // neither is infinity
                res = a < b;
            }
        }
        return res;
    }
    
    /**
     * Reconstructs the solution using the information in the "option" table.
     * @return an array s that for each coin type i returns the number of coins used s[i] 
     */
    public int[] reconstructSolution() {
        if (option == null) {
            throw new RuntimeException("The problem must be solved first");
        }
        
        int[] s = new int[v.length]; // Initially 0 coins of each type
        
        //******** Complete the implementation *****************


        return s;
    }

    public void displayData() {
        System.out.println("Minimum Number of Coins");
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("Options");
        for (int i = 0; i < option.length; i++) {
            for (int j = 0; j < option[i].length; j++) {
                System.out.print(option[i][j] + "\t");
            }
            System.out.println();
        }
    }
}