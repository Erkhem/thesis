package nonDeterministic;

import java.util.ArrayList;
import java.util.Collections;

import algorithms.Greedy;
import entity.Job;

public class ExhaustiveRobust {
	static int minGlobal = Integer.MAX_VALUE;
	
	public static int getMinGlobal(){
		return minGlobal;
	}
	
	public static int findRegret(ArrayList<JobUncertainty> input){
		
		ArrayList<Job> worstCaseScen = calculateWorstCaseScenario(input);
		//printPermutation(worstCaseScen);
		
		int objFunForInput = calculateObjectiveFunction(worstCaseScen);
		//System.out.println("input: "+objFunForInput);
		
		ArrayList<Job> piStar = calculateOptimalSchedule(worstCaseScen);
		//printPermutation(piStar);
		
		int objFunForPiStar = calculateObjectiveFunction(piStar);
		//System.out.println("piStar: "+objFunForPiStar);
		
		int regret = objFunForInput-objFunForPiStar;
		//System.out.println("regret: "+regret);	
		
		return regret;
		
	}	
	public static void permutation(ArrayList<JobUncertainty> jobs, int n){
		if(n == 1){
			int min = findRegret(jobs);
			if(min<minGlobal){
				minGlobal = min;
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
			//System.out.println(licz+ "MIN "+minGlobal);
			
	}
	
	
	
	private static ArrayList<Job> calculateOptimalSchedule(ArrayList<Job> unscheduledJobs){
		Greedy greedyAlgorithm = new Greedy();
		
		//ArrayList<Job> optimalSolution =  greedyAlgorithm.findOptimum(unscheduledJobs);
		
/*	Universal method for finding optimal solution. Works also for non unit processing time	
  But giving sometimes wrong result when two jobs have same weights
*/
		
		ArrayList<Job> optimalSolution = new ArrayList<>(unscheduledJobs);
  		greedyAlgorithm.sortWeights(optimalSolution);
		greedyAlgorithm.positionJobUniversal(optimalSolution);
		greedyAlgorithm.sortByStartTime(optimalSolution);
		
		
		return optimalSolution;
	}
	
	
	
	//For calculated optimal schedule with 'calculateInitialPi' method, we calculate worst case 
	//Scenario. This scenario is when the all late jobs take its wieght_end and all on time jobs will take wieght_start for schedule \pi
	public static ArrayList<Job> calculateWorstCaseScenario(ArrayList<JobUncertainty> pi){
		int currentNeededTime=0;	
		ArrayList<Job> worstCaseScen = new ArrayList<>();
		
		for (JobUncertainty job : pi) {
			currentNeededTime += job.getProcessingTime();		//adding current needed time
			//JobUncertainty uncertainVersionOfJob = uncertainJobs.get(job.getId());			
			if (currentNeededTime > job.getDeadline()) {				
				worstCaseScen.add(new Job(job.getId(),job.getWeightEnd(), job.getDeadline(), job.getProcessingTime()));
			}
			else{
				worstCaseScen.add(new Job(job.getId(),job.getWeightStart(), job.getDeadline(), job.getProcessingTime()));
			}
		}
		return worstCaseScen;
	}
	
	public static int calculateObjectiveFunction(ArrayList<Job> jobArray) {		
		int weightSum = 0;
		int currentNeededTime=0;
		
		for (Job i : jobArray) {
			currentNeededTime += i.getProcessingTime();		//adding current needed time
			if (currentNeededTime > i.getDeadline()) {
				weightSum += i.getWeight();
			}
		}
		currentNeededTime = 0;
		return weightSum;
	}
	
	public static void printPermutation(ArrayList<Job> jobsToPrint){
		System.out.print("Permutation: ");
		for(int i=0; i<jobsToPrint.size(); i++){
			System.out.print(jobsToPrint.get(i).getId()+"_");
			System.out.print("w"+jobsToPrint.get(i).getWeight()+"; ");
		}
		System.out.println("");
	}
}
