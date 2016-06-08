package algorithms;

import java.util.ArrayList;
import java.util.Collections;

import entity.Job;

public class GeneticAlgorithm {

	//GA Parameter
	private static final double crossoverRate=0.6;
    private static final double mutationRate = 0.001;
    private static final double reverseMutationRate = 0;
    private static final int tournamentSize = 13;
    private static final boolean elitism = true;
    

    
    // Evolves a population over one generation
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.getPopulationSize());

        // Keep our best individual if elitism is enabled
        int elitismOffset = 0;
        if (elitism) {
            newPopulation.saveSchedule(0, pop.getFittest());
          //  System.out.println("Fittest of pop  "+newPopulation+" is"+newPopulation.getScheduleAt(0).getObjectiveFunctionValue());
            elitismOffset = 1;
        }

        // Crossover population
        // Loop over the new population's size and create individuals from
        // Current population
        for (int i = elitismOffset; i < newPopulation.getPopulationSize(); i++) {
            // Select parents
            Schedule parent1 = scheduleSelection(pop);
            Schedule parent2 = scheduleSelection(pop);
            // Crossover parents
            if(Math.random()<crossoverRate){
            Schedule child = crossover(parent1, parent2);            
            // Add child to new population
            newPopulation.saveSchedule(i, child);
            }
            else{
            	Schedule newParent1 = new Schedule(parent1.getPermutation());
            	Schedule newParent2 = new Schedule(parent2.getPermutation());
            	newPopulation.saveSchedule(i, newParent1);
            	if(i<newPopulation.getPopulationSize()-1){
            		i++;
            		newPopulation.saveSchedule(i, newParent2);
            	}
            }
        }

        // Mutate the new population a bit to add some new genetic material
        for (int i = elitismOffset; i < newPopulation.getPopulationSize(); i++) {
            mutate(newPopulation.getScheduleAt(i));
            mutate2(newPopulation.getScheduleAt(i));
        }

        return newPopulation;
    }
    
    
    
    /**
     * Crossover function of Genetic Algorithm
     * @param parent1
     * @param parent2
     * @return new created child( permutation of job)
     */
    
    public static Schedule crossover(Schedule parent1, Schedule parent2){
    	if(parent1.equals(parent2)){
    		Schedule child = new Schedule(parent1.getPermutation());
    	//	System.out.println("Two parents have been chosen the same");
    		return child;
    	}
    	Schedule child = new Schedule(parent1.permutationLenght());
    	
        // Get start and end sub schedule positions for parent1's schedule
        int startPos = (int) (Math.random() * parent1.permutationLenght());
        int endPos = (int) (Math.random() * parent1.permutationLenght());
    	
        // Loop and add the sub schedule from parent1 to our child
        for (int i = 0; i < child.permutationLenght(); i++) {
            // If our start position is less than the end position
            if (startPos < endPos && i > startPos && i < endPos) {
                child.setJobAt(i, parent1.getJobAt(i));
            } // If our start position is larger
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.setJobAt(i, parent1.getJobAt(i));
                }
            }
        }
        
        
        // Loop through parent2's city schedule
        for (int i = 0; i < parent2.permutationLenght(); i++) {
            // If child doesn't have the city add it
            if (!child.containsJob(parent2.getJobAt(i))) {
                // Loop to find a spare position in the child's schedule
                for (int ii = 0; ii < child.permutationLenght(); ii++) {
                    // Spare position found, add city
                    if (child.getJobAt(ii) == null) {
                        child.setJobAt(ii, parent2.getJobAt(i));
                        break;
                    }
                }
            }
        }
        
    	return child;
    }
	
    // Mutate a permutation of job schedule using swap mutation
    private static void mutate(Schedule schedule) {
        // Loop through all jobs
        for(int schedulePos1=0; schedulePos1 < schedule.permutationLenght(); schedulePos1++){
            // Apply mutation rate
            if(Math.random() < mutationRate){
                // Get a second random position in the schedule
                int schedulePos2 = (int) (schedule.permutationLenght() * Math.random());

                // Get the jobs at target position in schedule
                Job job1 = schedule.getJobAt(schedulePos1);
                Job job2 = schedule.getJobAt(schedulePos2);

                // Swap them around
                schedule.setJobAt(schedulePos2, job1);
                schedule.setJobAt(schedulePos1, job2);
            }
        }
    }
    
    private static void mutate2(Schedule schedule){
    	if(Math.random()<reverseMutationRate){
	    	for(int i=1, last=schedule.permutationLenght()-1; i<schedule.permutationLenght()/2; i++, last--){ 
	    		Job temp = schedule.getJobAt(i);
	    		schedule.setJobAt(i, schedule.getJobAt(last));
	    		schedule.setJobAt(last, temp);
	    	}
    	}
    	
    }
    
    private static void mutate3(Schedule schedule){

        int middlePos = schedule.permutationLenght()/2;
        int lastPos = schedule.permutationLenght()-1;
        
        for(int i = 0; i<schedule.permutationLenght(); i=i+2,  lastPos=lastPos-2){        	
        	Job temp = schedule.getJobAt(i);
        	schedule.setJobAt(i,schedule.getJobAt(lastPos));
        	schedule.setJobAt(lastPos, temp);
        }
        
    }
    
    // Selects candidate Schedule for crossover
    private static Schedule scheduleSelection(Population pop) {
        // Create a Schedule population
        Population tournementSubPopulation = new Population(tournamentSize);
        // For each place in the tournament get a random candidate tour and
        // add it
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.getPopulationSize());
            tournementSubPopulation.saveSchedule(i, pop.getScheduleAt(randomId));
        }
        // Get the fittest Schedule
        Schedule fittest = tournementSubPopulation.getFittest();
        return fittest;
    }
    
}
