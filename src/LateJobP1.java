import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import entity.Job;


public class LateJobP1 {
	
	ArrayList<Job> jobs = new ArrayList<Job>();
	
	public LateJobP1(int numberOfJob){
		for(int i = 0; i<numberOfJob; i++){
			jobs.add(new Job());
		}
	}
	public LateJobP1(){
		
	}

	public ArrayList<Job> getUserInput(){
		long tempWeight;
		int tempDeadline;
		Scanner userInput = new Scanner(System.in);
		boolean repeat=true;
		String finish;
		while(repeat){
			System.out.println("Weight: ");
			tempWeight = userInput.nextLong();
			System.out.println("Dealine: ");
			tempDeadline = userInput.nextInt(); 
			jobs.add(new Job(tempWeight, tempDeadline, Integer.MAX_VALUE));
			System.out.println("If you want to finish, write finish! Else press enter!");
			userInput.nextLine();
			finish = userInput.nextLine();
			if(finish.equals("finish")){
				repeat = false;
			}
		}
		return jobs;
	}
	
	//Sort jobs according to it's weights 
	public void sortWeights(ArrayList<Job> jobs){
		Collections.sort(jobs, new Comparator<Job>() {
			@Override
			public int compare(Job o1, Job o2) {				
				return (int) (o2.getWeight()-o1.getWeight());
			}
		});
	}
	
	public boolean checkIfDeadlinesAreMet(ArrayList<Job> jobs){		
		for(Job eachJob:jobs){
			if(eachJob.getDeadline()<eachJob.getPosition()){
			return false;	
			}
		}		
		return true;
	}
	
	/**
	 * After sorting job according to its weigh:
	 * Take the most profitable(most weighted) job and schedule it in latest free
	 * slot meeting its deadline
	 * If there is no free slot meeting it's deadline, do not schedule the job
	 * @param sortedJobs
	 */
	public Job[] positionTheElement(ArrayList<Job> sortedJobs){
				
		int countOfJobs = sortedJobs.size();
		Job[] scheduledJobs = new Job[countOfJobs];
		for(Job job:sortedJobs){
			boolean findNextFreeIndex = true;
			int iterator = countOfJobs-1;
				while(findNextFreeIndex){
					if(job.getDeadline()>iterator && scheduledJobs[iterator] ==null){
						scheduledJobs[iterator] = job;
						findNextFreeIndex = false;
					}
					else{
						if(iterator>0){
							iterator--;	
						}
						else{
							findNextFreeIndex=false;
						}
						
					}
				}
		}
		return scheduledJobs;
	}	
	
	public Job[] findOptimum(){
		sortWeights(jobs);
		//after sort, position the element
		return positionTheElement(jobs);
	}
	
}
