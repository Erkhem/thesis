package algorithms;

import java.util.ArrayList;
import java.util.Collections;

import entity.Job;

/**
 * Class which holds information about permutation of jobs and it's value of Objective Function
 * @author Erkhem Narangerel
 *
 */
public class Schedule {
	
	/**
	 * Permutation solution which jobs needs to be scheduled
	 */
	private ArrayList<Job> permutation = new ArrayList<>();
	
	private int objectiveFunctionValue;
	
	private int numberOfJobs = 0;
	
	//Constructors	
	public Schedule(ArrayList<Job> jobs){
		this.permutation = new ArrayList<>(jobs);		
	}

    // Constructs a blank tour
    public Schedule(int numberOfJobs){
        for (int i = 0; i < numberOfJobs; i++) {
            permutation.add(null);
        }
    }

	private int calculate() {		
		int weightSum = 0;
		int currentNeededTime=0;
		
		for (Job i : permutation) {
			currentNeededTime += i.getProcessingTime();		//adding current needed time
			if (currentNeededTime > i.getDeadline()) {
				weightSum += i.getWeight();
			}
		}
		currentNeededTime = 0;
		return weightSum;
	}
	
	public boolean containsJob(Job job){
		return permutation.contains(job);
	}
	
	public Job getJobAt(int position){
		return permutation.get(position);
	}
	public void shuffle(){
		Collections.shuffle(permutation);
/*		System.out.println("Newly shiffled permutations obj func is:   "+getObjectiveFunctionValue());
		for(int i =0; i<permutationLenght(); i++){
			System.out.print(" "+permutation.get(i).getId());
		}*/
	}
	
	public void setJobAt(int position, Job job){
		permutation.set(position, job);
	}
	
	public int getObjectiveFunctionValue() {
		 objectiveFunctionValue = calculate();
		 return objectiveFunctionValue;
	}
	
	public int permutationLenght(){
		return permutation.size();
	}
	public ArrayList<Job> getPermutation(){
		return permutation;
	}


}
