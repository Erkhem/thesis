package algorithms;

import java.util.ArrayList;
import java.util.Collections;

import entity.Job;

/**
 * This algorithm solves scheduling problem by searching every possible combination and find global opitimal solution.
 * This solution is not suitable for larger number of Jobs to be scheduled.
 * @author Erkhem
 *
 */
public class Exhaustive {
	
	private long minObjectiveFunctionVal;
	ArrayList<Job> jobs;
	
	public void findPermutation(ArrayList<Job> jobs, ArrayList<Job> preceder){
		if(jobs.size()==2){
			Collections.swap(jobs, 0, 1);
		}
		else
		{
		for(Job firstJob:jobs){
			preceder.add(firstJob);
			ArrayList<Job> backup = new ArrayList<Job>(jobs);
			backup.remove(firstJob);
			findPermutation(backup,preceder);
			for(int i=0; i<backup.size();i++)
				System.out.println(backup.get(i));
			ArrayList<Job> newPermutationOfJobs = new ArrayList<Job>(preceder);
			preceder.clear();
			newPermutationOfJobs.addAll(backup);
			int value = calculate(newPermutationOfJobs);
		}
		}
		
	}
	private int licz = 0;
	int minGlobal = Integer.MAX_VALUE;
	public void permutation(ArrayList<Job> jobs, int n){
		if(n == 1){
			licz++;
			if(calculate(jobs)<minGlobal){
				minGlobal = calculate(jobs);
			}
			//System.out.println("PERMUTATION -------- ");
			//for(int i=0;i<jobs.size();i++)
			//System.out.println(jobs.get(i));
			return;
		}
		for(int i=0; i<n; i++){
			Collections.swap(jobs, i, n-1);
			permutation(jobs, n-1);
			Collections.swap(jobs, i, n-1);
		}
			System.out.println(licz+ "MIN "+minGlobal);
			
	}
	

	
	public int calculate(ArrayList<Job> jobArray) {		
		int weightSum = 0;
		int currentNeededTime=0;
		
		for (Job i : jobArray) {
		//	System.out.println(i);
			currentNeededTime += i.getProcessingTime();		//adding current needed time
		//	System.out.println(currentNeededTime + "  current time");
			if (currentNeededTime > i.getDeadline()) {
				weightSum += i.getWeight();
			}
		}
		System.out.println("Exhaustive search's calculate WTW:   " + weightSum);
		currentNeededTime = 0;
		return weightSum;
	}
	
	
	
}
