import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class LateJobP1 {
	
	ArrayList<Job> jobs = new ArrayList<Job>();

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
	
	public void positionTheElement(ArrayList<Job> sortedJobs){
		ArrayList<Job> positionedJobs = new ArrayList<Job>();
		
		int countOfJobs = sortedJobs.size();
		Job[] scheduledJobs = new Job[countOfJobs];
		boolean isJobsMeetDeadlines=false;
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
								
						
			isJobsMeetDeadlines = checkIfDeadlinesAreMet(positionedJobs);
			if(!isJobsMeetDeadlines){
				//Shuffle the jobs
				
			}
		}
	}	
}
