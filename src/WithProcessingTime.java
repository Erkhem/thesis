import java.util.ArrayList;
import java.util.Random;

import entity.Job;


public class WithProcessingTime {

	private ArrayList<Job> jobs;
	private int currentNeededTime = 0, weightSum = 0;
	private int globalSumWeight=0;
	
	WithProcessingTime(int jobCount) {
		jobs = new ArrayList<Job>();

		for (int j = 0; j < jobCount; j++){
			jobs.add(new Job());
		}
	}
	
	int calculate(ArrayList<Job> jobArray) {		
		weightSum = 0;
		
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
	
	void findOptimum() {
		int calculationResult = calculate(jobs);
		int exchangeThis = 0;
		int exchangeWith = 0;
		boolean someCondition = true;
		Random generator = new Random();
		ArrayList<Job> copyArray = jobs;
		Job tmp;
		while(someCondition){
			if(calculationResult > globalSumWeight) {
				globalSumWeight = calculationResult;
			}
			 exchangeThis = generator.nextInt(jobs.size());			//exchange random two jobs
			 exchangeWith = generator.nextInt(jobs.size());			 
			 
			 tmp = copyArray.get(exchangeThis);
			 copyArray.set(exchangeThis, copyArray.get(exchangeWith));
			 copyArray.set(exchangeWith, tmp);
			 
			 if (calculate(copyArray) >= calculationResult) {
				 jobs = copyArray;
			 }
		}
	}
}

