package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import entity.Job;

public class SimulatedAnnealing {

	private float endConditionTemperature=1.5F;
	private float begginingTemperature;
	float alpha = 0.9F;
	int size = 0;
	
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
		//System.out.println("Simulated Annealing WTW:   " + weightSum);
		currentNeededTime = 0;
		return weightSum;
	}
	
	public int findOptimalSolution(ArrayList<Job> currentSolution){
		int currentCost = calculate(currentSolution);
		int replaceWith2 = 0;
		int replaceWith1 = 0;
		int alternativeCost = 0;
		size = currentSolution.size();
		
		Random random = new Random();
		float temperature = (float) assignBeginningTemperature(currentSolution);
		begginingTemperature = temperature;
		
		Job temp = null;
		ArrayList<Job> copyJobs = new ArrayList<>(currentSolution); 
		
		while(temperature>endConditionTemperature){
		
		int rounds =(int) (size*temperature);
		for(int i=0;i<rounds;i++){
			for(int j=0; j<rounds/10;j++){
				int startPos= random.nextInt(size);
				int endPos = random.nextInt(size);
				if(startPos<endPos){
				List<Job> subList = copyJobs.subList(startPos, endPos);
				Collections.reverse(subList);
				
				}				
			}
			
			//creating new neighboring random solution
			replaceWith1 = random.nextInt(currentSolution.size());
			replaceWith2 = random.nextInt(currentSolution.size());
			
			temp = copyJobs.get(replaceWith1);
			copyJobs.set(replaceWith1, copyJobs.get(replaceWith2));
			copyJobs.set(replaceWith2, temp);		
			
		}
			
			//Calculating alternative Jobs
			alternativeCost = calculate(copyJobs);
			
			if(currentCost > alternativeCost){
				currentSolution = copyJobs;
				currentCost = alternativeCost;
			}
			else 
				if(random.nextInt(2)<powerFunction(alternativeCost, currentCost, temperature)){
				currentSolution = copyJobs;
			}
			//temperature = changeTemperature(temperature);
			temperature = changeTempWithAlphaB(temperature);
			//System.out.println("Current Cost: "+currentCost);
		}
		System.out.println("Simulated Annealing: "+currentCost);
		return currentCost;
	}
	
/*	
	private void findNeighbour(ArrayList<Job> jobs){
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
	}*/
	
	private double powerFunction(int newValue, int oldValue, float temperature){
		return Math.pow(Math.E, -(newValue-oldValue)/temperature);
	}
	
	private float changeTemperature(float currentTemp){
		return currentTemp*alpha;
	}
	
	private double alphaB() {
		return (float)(begginingTemperature - endConditionTemperature)/(Math.pow((float)begginingTemperature*endConditionTemperature,2.7));
	}
	private float changeTempWithAlphaB(float currentTemp) {
		return (float) ((0.00001 + currentTemp) / (1 + (alphaB() * (currentTemp))));
	}
	
	private double alphaC() {
		return Math.pow((float)begginingTemperature, (float)(-1/(Math.pow((float)size, 3.5))));
	}
	private float changeTempWithAlphaC(float currentTemp) {
		return (float) (currentTemp*alphaC());
	}
	
	private double assignBeginningTemperature(ArrayList<Job> currentSolutionJobs){
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
