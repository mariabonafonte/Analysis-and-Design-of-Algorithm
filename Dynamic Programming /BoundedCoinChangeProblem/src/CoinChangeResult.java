import java.util.Arrays;

public class CoinChangeResult {

        public int numberOfCoins;
        public int[] solution;

        public CoinChangeResult(int numberOfCoins, int[] solution) {
            this.numberOfCoins = numberOfCoins;
            this.solution = Arrays.copyOf(solution, solution.length);
        }

        @Override
        public String toString() {
            return numberOfCoins + " " + Arrays.toString(solution);
        }
        
        
        @Override
        public int hashCode() {
            return Integer.hashCode(this.numberOfCoins);
        }
} // end CoinChangeResult
