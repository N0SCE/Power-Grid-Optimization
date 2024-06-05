import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Main class
 */
// FREE CODE HERE
public class Main {
    static int  energyDemandSize;
    public static void main(String[] args) throws IOException {

       /** MISSION POWER GRID OPTIMIZATION BELOW **/
        ReadFile readFile = new ReadFile();
        System.out.println("##MISSION POWER GRID OPTIMIZATION##");
        // TODO: Your code goes here
        // You are expected to read the file given as the first command-line argument to read 
        // the energy demands arriving per hour. Then, use this data to instantiate a 
        // PowerGridOptimization object. You need to call getOptimalPowerGridSolutionDP() method
        // of your PowerGridOptimization object to get the solution, and finally print it to STDOUT.

        String firstFileName = args[0];
        String[] firstFile = readFile.readFile(firstFileName); //demandSchedule.dat
        String[] energyDemands = firstFile[0].split(" ");
        energyDemandSize = energyDemands.length;
        ArrayList<Integer> energyDemansPerHour = new ArrayList<>();

        int maxdemanded = 0;
        for(String element : energyDemands){
            energyDemansPerHour.add(Integer.parseInt(element));
            maxdemanded += Integer.parseInt(element);
        }

        PowerGridOptimization powerGridOptimization = new PowerGridOptimization(energyDemansPerHour);
        OptimalPowerGridSolution optimalPowerGridSolution = powerGridOptimization.getOptimalPowerGridSolutionDP();


        System.out.println("The total of energy demanded gigawatts: " + maxdemanded);
        System.out.println("Maximum number of satisfied gigawatts: " + optimalPowerGridSolution.getmaxNumberOfSatisfiedDemands());
        System.out.print("Hours at which the battery bank should be discharged: ");
        for(int i = 0; i < optimalPowerGridSolution.getHoursToDischargeBatteriesForMaxEfficiency().size(); i++){
            System.out.print(optimalPowerGridSolution.getHoursToDischargeBatteriesForMaxEfficiency().get(i));
            if(i < optimalPowerGridSolution.getHoursToDischargeBatteriesForMaxEfficiency().size()-1){
                System.out.print(", ");
            }
        }
        System.out.println();
        System.out.println("The number of unsatisfied gigawatts: " + (maxdemanded - optimalPowerGridSolution.getmaxNumberOfSatisfiedDemands()));
        System.out.println("##MISSION POWER GRID OPTIMIZATION COMPLETED##");

        /** MISSION ECO-MAINTENANCE BELOW **/

        System.out.println("##MISSION ECO-MAINTENANCE##");
        // TODO: Your code goes here
        // You are expected to read the file given as the second command-line argument to read
        // the number of available ESVs, the capacity of each available ESV, and the energy requirements 
        // of the maintenance tasks. Then, use this data to instantiate an OptimalESVDeploymentGP object.
        // You need to call getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity) method
        // of your OptimalESVDeploymentGP object to get the solution, and finally print it to STDOUT.

        String secondFileName = args[1];
        String[] secondFile = readFile.readFile(secondFileName);
        int maxAvailable = Integer.parseInt(secondFile[0].split(" ")[0]);
        int maxCapacity = Integer.parseInt(secondFile[0].split(" ")[1]);
        ArrayList<Integer> maintenanceTaskEnergyDemands = new ArrayList<>();
        for(String element : secondFile[1].split(" ")){
            maintenanceTaskEnergyDemands.add(Integer.parseInt(element));
        }
        OptimalESVDeploymentGP optimalESVDeploymentGP = new OptimalESVDeploymentGP(maintenanceTaskEnergyDemands);
        int numberESV = optimalESVDeploymentGP.getMinNumESVsToDeploy(maxAvailable, maxCapacity);

        if(numberESV == -1){
            System.out.println("Warning: Mission Eco-Maintenance Failed.");

        }else{
            ArrayList<ArrayList<Integer>> ESVs = optimalESVDeploymentGP.getMaintenanceTasksAssignedToESVs();
            System.out.println("The minumum number of ESVs to deploy: " + numberESV);
            for(int i = 0; i < ESVs.size(); i++){
                int number = i+1;
                System.out.println("ESV " + number + " tasks: " + ESVs.get(i));
            }
        }
        System.out.println("##MISSION ECO-MAINTENANCE COMPLETED##");
    }
}
