package algorithms;

import java.util.ArrayList;

import entity.Job;

public class Evolutionary {
	
	
	public int calculate(ArrayList<Job> jobArray) {		
		int weightSum = 0;
		int currentNeededTime=0;
		
		for (Job i : jobArray) {
			System.out.println(i);
			currentNeededTime += i.getProcessingTime();		//adding current needed time
			System.out.println(currentNeededTime + "  current time");
			if (currentNeededTime < i.getDeadline()) {
				weightSum += i.getWeight();
			}
		}
		System.out.println("WTW:   " + weightSum);
		currentNeededTime = 0;
		return weightSum;
	}
	
	public int calculateObjectiveFunction(){
		
		
		return 0;
	}
	
	//Create valid permutation of jobs
	
	
}
