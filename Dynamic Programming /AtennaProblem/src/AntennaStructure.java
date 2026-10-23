import java.util.Arrays;

public class AntennaStructure{
		public int weight;
		public int[] solution;

		public AntennaStructure(int weight, int[] solution) {
			this.weight = weight;
			this.solution = Arrays.copyOf(solution, solution.length);
		}

		@Override
		public String toString() {
			return weight +" "+Arrays.toString(solution);
		}
		
		@Override
		public boolean equals(Object obj) {
			boolean ok = this == obj;
			if (!ok && obj instanceof AntennaStructure) {
				AntennaStructure aux = (AntennaStructure)obj;
				ok = toString().equals(aux.toString());
			}
			return ok;
		}
		
		@Override
		public int hashCode() {
			return Integer.hashCode(this.weight);
		}
	}