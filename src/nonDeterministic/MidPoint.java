package nonDeterministic;

import java.util.ArrayList;

import entity.Job;

public class MidPoint {

	
	public ArrayList<Job> convertDeterministic(ArrayList<JobUncertainty> uncertainJobs){
		ArrayList<Job> deterministicJobs =new ArrayList<>();
		for(JobUncertainty job:uncertainJobs){			
			int weight = job.getWeightStart()+(job.getWeightEnd()-job.getWeightStart())/2;
			Job newJob = new Job(job.getId(),weight,job.getDeadline(), job.getProcessingTime());
			deterministicJobs.add(newJob);
		}
		return deterministicJobs;
	}
	
}
