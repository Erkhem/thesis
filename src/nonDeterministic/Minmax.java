package nonDeterministic;

import java.util.ArrayList;

import com.google.common.collect.ImmutableList;

import algorithms.Greedy;
import entity.Job;

public class Minmax {
	
	private Greedy greedyAlgorithm = new Greedy();
	private MidPoint midPointAlgorithm = new MidPoint();
	private RandomPoint randomPointAlgorithm;
	ArrayList<JobUncertainty> uncertainJobs;
	
	//ArrayList<Job> worstCaseScenario = new ArrayList<>(); // we don't take into account the permutation of this list.
	//What matters in this array is what did the weights of the jobs took argument. 
	
	
	private void recieveInput(ArrayList<JobUncertainty> inputData){
		//ArrayList<Job> jobs = 
		
		uncertainJobs = inputData;
	}
	
	//Using midpoint algorithms choosen a fixed Scenario S and calculate the
	// optimal solution using greedy algorithm
	private ArrayList<Job> calculateInitialPi(){		
		ArrayList<Job> jobsMid = midPointAlgorithm.convertDeterministic(uncertainJobs);		
		return jobsMid;		
	}
	
	private ArrayList<Job> calculateOptimalSchedule(ArrayList<Job> unscheduledJobs){
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
	public ArrayList<Job> calculateWorstCaseScenario(ArrayList<Job> pi){
		int currentNeededTime=0;	
		ArrayList<Job> worstCaseScen = new ArrayList<>();
		
		for (Job job : pi) {
			currentNeededTime += job.getProcessingTime();		//adding current needed time
			JobUncertainty uncertainVersionOfJob = uncertainJobs.get(job.getId());			
			if (currentNeededTime > job.getDeadline()) {				
				worstCaseScen.add(new Job(uncertainVersionOfJob.getId(),uncertainVersionOfJob.getWeightEnd(), uncertainVersionOfJob.getDeadline(), uncertainVersionOfJob.getProcessingTime()));
			}
			else{
				worstCaseScen.add(new Job(uncertainVersionOfJob.getId(),uncertainVersionOfJob.getWeightStart(), uncertainVersionOfJob.getDeadline(), uncertainVersionOfJob.getProcessingTime()));
			}
		}
		return worstCaseScen;
	}
	
	public int[] calculateZetPi(ArrayList<JobUncertainty> input){
		uncertainJobs = input;
		ArrayList<Job> inputCertainMid = midPointAlgorithm.convertDeterministic(input);
		ArrayList<Job> inputCertainRandom = RandomPoint.convertDeterministic(input);
		ArrayList<Job> inputCertainMin = MinPoint.convertDeterministic(input);
		ArrayList<Job> inputCertainMax = MaxPoint.convertDeterministic(input);
		
		
//		System.out.println("After midpoint algorithm");
//		printPermutation(inputCertainMid);
//		printPermutation(inputCertainRandom);
//		printPermutation(inputCertainMin);
//		printPermutation(inputCertainMax);
		
		
		
		ArrayList<Job> pi = calculateOptimalSchedule(inputCertainMid);
		ArrayList<Job> piRand = calculateOptimalSchedule(inputCertainRandom);
		ArrayList<Job> piMin = calculateOptimalSchedule(inputCertainMin);
		ArrayList<Job> piMax = calculateOptimalSchedule(inputCertainMax);
		
		
		
//		System.out.println("Optimal solution for midpoint scenario");
//		printPermutation(pi);
//		printPermutation(piRand);
//		printPermutation(piMin);
//		printPermutation(piMax);
		
		ArrayList<Job> worstCaseScenario = calculateWorstCaseScenario(pi);
		ArrayList<Job> worstCaseScenarioRand = calculateWorstCaseScenario(piRand);
		ArrayList<Job> worstCaseScenarioMin = calculateWorstCaseScenario(piMin);
		ArrayList<Job> worstCaseScenarioMax = calculateWorstCaseScenario(piMax);
		
		
		int objFunInWorstCaseScenarioForPi = calculateObjectiveFunction(worstCaseScenario);
		int objFunInWorstCaseScenarioForPiRand = calculateObjectiveFunction(worstCaseScenarioRand);
		int objFunInWorstCaseScenarioForPiMin = calculateObjectiveFunction(worstCaseScenarioMin);
		int objFunInWorstCaseScenarioForPiMax = calculateObjectiveFunction(worstCaseScenarioMax);
		
		
//		System.out.println("Worst case scenario for pi");
//		printPermutation(worstCaseScenario);
//		printPermutation(worstCaseScenarioRand);
//		printPermutation(worstCaseScenarioMin);
//		printPermutation(worstCaseScenarioMax);

		ArrayList<Job> piStarMid = calculateOptimalSchedule(worstCaseScenario);
		ArrayList<Job> piStarRand = calculateOptimalSchedule(worstCaseScenarioRand);
		ArrayList<Job> piStarMin = calculateOptimalSchedule(worstCaseScenarioMin);
		ArrayList<Job> piStarMax = calculateOptimalSchedule(worstCaseScenarioMax);
		
		
//		System.out.println("Optimal schedule for Worst case scenario");
//		printPermutation(piStarMid);
//		printPermutation(piStarRand);
//		printPermutation(piStarMin);
//		printPermutation(piStarMax);
		
		
		 int objectiveMid = objFunInWorstCaseScenarioForPi -
		calculateObjectiveFunction(piStarMid);
		 
		 int objectiveRand = objFunInWorstCaseScenarioForPiRand -
					calculateObjectiveFunction(piStarRand);	 
				 
		 int objectiveMin = objFunInWorstCaseScenarioForPiMin -
					calculateObjectiveFunction(piStarMin);
		 
		 int objectiveMax = objFunInWorstCaseScenarioForPiMax -
		calculateObjectiveFunction(piStarMax);
		 
		 System.out.println("obj for pi mid "+objFunInWorstCaseScenarioForPi);
		 System.out.println("obj for piRand "+objFunInWorstCaseScenarioForPiRand);
		 System.out.println("obj for piMin "+objFunInWorstCaseScenarioForPiMin);
		 System.out.println("obj for piMax "+objFunInWorstCaseScenarioForPiMax);
		 
		 System.out.println("obj fun in worst case scen mid"+calculateObjectiveFunction(piStarMid));
		 System.out.println("obj fun in worst case scen rand"+calculateObjectiveFunction(piStarRand));
		 System.out.println("obj fun in worst case scen min"+calculateObjectiveFunction(piStarMin));
		 System.out.println("obj fun in worst case scen max"+calculateObjectiveFunction(piStarMax));
		 calculateObjectiveFunction(piStarMax);
		 
		 int[] objectives = {objectiveMid, objectiveRand, objectiveMin, objectiveMax};
		 
		 return objectives;
	}

/*	public JobUncertainty getUncertainJob(Job job){
		for(JobUncertainty jobu:uncertainJobs){
			if(id)
		}
		JobUncertainty jobu = 
		
		
		return jobu;
	}*/
	
	public int calculateObjectiveFunction(ArrayList<Job> jobArray) {		
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
