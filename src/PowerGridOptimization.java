import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * This class accomplishes Mission POWER GRID OPTIMIZATION
 */
public class PowerGridOptimization {
    private ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour;

    public PowerGridOptimization(ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour){
        this.amountOfEnergyDemandsArrivingPerHour = amountOfEnergyDemandsArrivingPerHour;
    }

    public ArrayList<Integer> getAmountOfEnergyDemandsArrivingPerHour() {
        return amountOfEnergyDemandsArrivingPerHour;
    }
    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalPowerGridSolution
     */
    public OptimalPowerGridSolution getOptimalPowerGridSolutionDP() {
        int energyDemandSize = amountOfEnergyDemandsArrivingPerHour.size();

        int[] SOL = new int[energyDemandSize + 1];
        SOL[0] = 0;
        ArrayList<ArrayList<Integer>> HOURS = new ArrayList<ArrayList<Integer>>();
        HOURS.add(new ArrayList<>());


        for (int j = 1; j <= energyDemandSize; j++) {
            HashMap<Integer, Integer> solutions2 = new HashMap<>();
            for (int i = 0; i < j; i++) {
                int min = Math.min(D(j), E(j - i));
                int result = SOL[i] + min;
                solutions2.put(result, i);
            }
            int max = Collections.max(solutions2.keySet());
            int maxKey = solutions2.get(max);
            SOL[j] = max;

            ArrayList<Integer> list = new ArrayList<Integer>();
            list.addAll(HOURS.get(maxKey));// check
            list.add(j);
            HOURS.add(list);
        }

        int maxSatisfiedDemand = SOL[SOL.length - 1];

        return new OptimalPowerGridSolution(maxSatisfiedDemand, HOURS.get(HOURS.size() - 1));// return new
        // OptimalPowerGridSolution(maxSatisfiedDemand,
        // HOURS.get(HOURS.size()-1));
    }

    private int E(int number){
        return number * number;
    }

    private int D(int number){
        return amountOfEnergyDemandsArrivingPerHour.get(number - 1);
    }
}
