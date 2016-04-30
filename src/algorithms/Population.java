package algorithms;

import java.util.ArrayList;

import entity.Job;

public class Population {

    // Holds population of tours
    Schedule[] population;
    int populationSize;

    // Construct a population with read JSON array of Jobs
    public Population(ArrayList<Job> readJobs,int populationSize) 
    {
        population = new Schedule[populationSize];
        this.populationSize = populationSize;
            // Loop and create individuals
            for (int i = 0; i < populationSize; i++) {
                Schedule newSchedule = new Schedule(readJobs);
                newSchedule.shuffle();
                saveSchedule(i, newSchedule);
            }
    }
    //Construct empty population with certain size
    
    public Population(int size){
    	this.populationSize = size;
    	population = new Schedule[size];
    }
    
    // Saves a tour
    public void saveSchedule(int index, Schedule schedule) {
        population[index] = schedule;
    }
    
    // Gets a tour from population
    public Schedule getScheduleAt(int index) {
        return population[index];
    }

    // Gets the best tour in the population
    public Schedule getFittest() {
        Schedule best = population[0];
        // Loop through individuals to find fittest
        for (int i = 1; i < populationSize; i++) {
            if (best.getObjectiveFunctionValue() >= getScheduleAt(i).getObjectiveFunctionValue()) {
                best = getScheduleAt(i);
            }
        }
        return best;
    }
    public int getPopulationSize(){
    	return populationSize;
    }
}
