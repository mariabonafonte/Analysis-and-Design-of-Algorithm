import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhoneTariff {

    private int flatRate, commitmentPeriod, dataRate;
    private List<Integer> estimation;
    private List<Integer> payment; // Minimum payment to be made during months i...n

    public PhoneTariff(int flatRate, int commitmentPeriod, int dataRate, List<Integer> estimation) {
        this.flatRate = flatRate;
        this.commitmentPeriod = commitmentPeriod;
        this.dataRate = dataRate;
        this.estimation = estimation;
        this.payment = null;
    }

    public int solveBottomUp() {

    	 //***Complete implementation***
    }

    public List<Integer> reconstructSolution() {
        if (payment == null) {
            throw new RuntimeException("The problem must be solved first");
        }

        //***Complete implementation***
    }

    public void printSolutionVector() {
        System.out.println(payment);
    }
}