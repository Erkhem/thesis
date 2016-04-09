package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import entity.Job;

public class SimulatedAnnealing {

	private float endConditionTemperature=2.5F;
	private int begginingTemperature;
	float alpha = 0.99F;
	
	ArrayList<Job> unsuccessfullyScheduled = new ArrayList<>();
	
	public int calculate(ArrayList<Job> jobArray) {		
		int weightSum = 0;
		int currentNeededTime=0;
		
		for (Job i : jobArray) {
		//	System.out.println(i);
			currentNeededTime += i.getProcessingTime();		//adding current needed time
		//	System.out.println(currentNeededTime + "  current time");
			if (currentNeededTime > i.getDeadline()) {
				weightSum += i.getWeight();
				unsuccessfullyScheduled.add(i);
			}
		}
		System.out.println("WTW:   " + weightSum);
		currentNeededTime = 0;
		return weightSum;
	}
	
	public int findOptimalSolution(ArrayList<Job> currentSolution){
		int currentCost = calculate(currentSolution);
		int replaceWith2 = 0;
		int replaceWith1 = 0;
		int alternativeCost = 0;
		
		Random random = new Random();
		float temperature = (float) assignBegginingTemperature(currentSolution);
		
		Job temp = null;
		ArrayList<Job> copyJobs = currentSolution; 
		
		while(temperature>endConditionTemperature){
			
			//creating new neighboring random solution
			replaceWith1 = random.nextInt(currentSolution.size());
			replaceWith2 = random.nextInt(currentSolution.size());
			
			temp = copyJobs.get(replaceWith1);
			copyJobs.set(replaceWith1, copyJobs.get(replaceWith2));
			copyJobs.set(replaceWith2, temp);
			
			//Calculating alternative Jobs
			alternativeCost = calculate(copyJobs);
			
			if(currentCost > alternativeCost){
				currentSolution = copyJobs;
				currentCost = alternativeCost;
			}
			else if(random.nextInt(2)<powerFunction(alternativeCost, currentCost, temperature)){
				currentSolution = copyJobs;
			}
			temperature = changeTemperature(temperature);
			System.out.println("Current Cost: "+currentCost);
		}
		
		return currentCost;
	}
	
	private double powerFunction(int newValue, int oldValue, float temperature){
		return Math.pow(Math.E, -(newValue-oldValue)/temperature);
	}
	
	private float changeTemperature(float currentTemp){
		return currentTemp*alpha;
	}
	
	private double assignBegginingTemperature(ArrayList<Job> currentSolutionJobs){
		int [] mediane = new int[currentSolutionJobs.size()+1];
		int j = 0;
		for(Job job:currentSolutionJobs){
			j++;
			mediane[j] = job.getDeadline();
		}
		Arrays.sort(mediane);
		return Math.sqrt(currentSolutionJobs.size()*mediane[mediane.length/2]);
	}
	
}
