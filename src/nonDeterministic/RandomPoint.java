package nonDeterministic;

import java.util.ArrayList;

import entity.Job;

public class RandomPoint {

	public static ArrayList<Job> convertDeterministic(ArrayList<JobUncertainty> uncertainJobs){
		ArrayList<Job> deterministicJobs =new ArrayList<>();
		for(JobUncertainty job:uncertainJobs){		
			int weight =  (int) (job.getWeightStart()+Math.random()*(job.getWeightEnd()-job.getWeightStart()+1));
			Job newJob = new Job(job.getId(), weight,job.getDeadline(), job.getProcessingTime());
			deterministicJobs.add(newJob);
		}
		return deterministicJobs;
	}
	
}
