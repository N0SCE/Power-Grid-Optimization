import java.util.ArrayList;
import java.util.Collections;

/**
 * This class accomplishes Mission Eco-Maintenance
 */
public class OptimalESVDeploymentGP
{
    private ArrayList<Integer> maintenanceTaskEnergyDemands;

    /*
     * Should include tasks assigned to ESVs.
     * For the sample input:
     * 8 100
     * 20 50 40 70 10 30 80 100 10
     * 
     * The list should look like this:
     * [[100], [80, 20], [70, 30], [50, 40, 10], [10]]
     * 
     * It is expected to be filled after getMinNumESVsToDeploy() is called.
     */
    private ArrayList<ArrayList<Integer>> maintenanceTasksAssignedToESVs = new ArrayList<>();

    ArrayList<ArrayList<Integer>> getMaintenanceTasksAssignedToESVs() {
        return maintenanceTasksAssignedToESVs;
    }

    public OptimalESVDeploymentGP(ArrayList<Integer> maintenanceTaskEnergyDemands) {
        this.maintenanceTaskEnergyDemands = maintenanceTaskEnergyDemands;
    }

    public ArrayList<Integer> getMaintenanceTaskEnergyDemands() {
        return maintenanceTaskEnergyDemands;
    }

    /**
     *
     * @param maxNumberOfAvailableESVs the maximum number of available ESVs to be deployed
     * @param maxESVCapacity the maximum capacity of ESVs
     * @return the minimum number of ESVs required using first fit approach over reversely sorted items.
     * Must return -1 if all tasks can't be satisfied by the available ESVs
     */
    public int getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity)
    {
        // TODO: Your code goes here
        Collections.sort(maintenanceTaskEnergyDemands, Collections.reverseOrder());
        ArrayList<Integer> ESVs = new ArrayList<>();

        for(int i = 0; i <  maintenanceTaskEnergyDemands.size(); i++){

            int energy = maintenanceTaskEnergyDemands.get(i);
            if(energy > maxESVCapacity){
                return -1;
            }

            if(ESVs.size() != 0){
                boolean isAvailablePlaceFound = false;
                int index = 0;
                for(int remainings : ESVs){
                    if(ESVs.get(index) + energy <= maxESVCapacity){
                        isAvailablePlaceFound = true;
                        break;
                    }else{
                        if(ESVs.size() >= maxNumberOfAvailableESVs && index == ESVs.size()-1){
                            return -1;// check if you need to break
                        }else if(ESVs.size() < maxNumberOfAvailableESVs && index == ESVs.size()-1){
                            ESVs.add(energy);
                            ArrayList<Integer> list = new ArrayList<>();
                            list.add(energy);
                            maintenanceTasksAssignedToESVs.add(list);
                            break;
                        }
                    }
                    index++;
                }

                if(isAvailablePlaceFound){
                    ESVs.set(index, energy + ESVs.get(index));
                    maintenanceTasksAssignedToESVs.get(index).add(energy);
                }
            }else {
                ESVs.add(energy);
                ArrayList<Integer> list = new ArrayList<>();
                list.add(energy);
                maintenanceTasksAssignedToESVs.add(list);
            }
        }

        if(ESVs.size() > maxNumberOfAvailableESVs){
            return -1;
        }else {
            return ESVs.size();
        }

    }

}
