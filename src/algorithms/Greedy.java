package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

import entity.Job;

public class Greedy {
	
	long objectiveFunctionValue;
	RangeMap<Long,Integer> timeline = TreeRangeMap.create(); 
	
	//Sort jobs according to it's weights 
	public void sortWeights(ArrayList<Job> jobs){
		Collections.sort(jobs, new Comparator<Job>() {
			@Override
			public int compare(Job o1, Job o2) {				
				return (int) (o2.getWeight()-o1.getWeight());
			}
		});
	}
	
	public void sortByStartTime(ArrayList<Job> jobs){
		Collections.sort(jobs, new Comparator<Job>() {
			@Override
			public int compare(Job o1, Job o2) {				
				return (int) (o1.getStartTime()-o2.getStartTime());
			}
		});
	}
	
	/**
	 * After sorting job according to its weight:
	 * Take the most profitable(most weighted) job and schedule it in latest free
	 * slot meeting its deadline
	 * If there is no free slot meeting it's deadline, do not schedule the job
	 * @param sortedJobs
	 */
	public Job[] positionTheElement(ArrayList<Job> sortedJobs){
				
		int countOfJobs = sortedJobs.size();
		//Scheduled jobs will be stored here
		Job[] scheduledJobs = new Job[countOfJobs];
		
		for(Job job:sortedJobs){
			boolean findNextFreeIndex = true;
			int iterator = countOfJobs-1;
				while(findNextFreeIndex & iterator >=0 ){
					if(job.getDeadline()>iterator && scheduledJobs[iterator] ==null){
						scheduledJobs[iterator] = job;
//						job.setPosition(iterator);
						findNextFreeIndex = false;
					}
					else{
							iterator--;							
					}
				}
				if(findNextFreeIndex == true){
					//if findNextFreeIndex is still true, which indicates this job hasn't scheduled.
					//Then add the weight of unscheduled job to ObjectiveFunctionValue.
					objectiveFunctionValue +=job.getWeight();
	//				job.setPosition(-1);
				}
		}
		return scheduledJobs;
	}
	
	//Algorithm which provides universal solution for both unit processing time and not unit processing time
	//which will place the job in latest possible position according to it's deadline and how other jobs are scheduled.
	//To implement this, new parameter start time, and position should be introduced to jobs
	//or some timeline measurement
	public void positionJobUniversal(ArrayList<Job> sortedJobs){
		int processingTime=0;
		for(Job job:sortedJobs){
			long processingTimeOfJob = job.getProcessingTime();
			long endTime = job.getDeadline();
			long startTime = endTime-processingTimeOfJob;
			
			while(startTime>=0){
				RangeMap<Long, Integer> intersecting = timeline.subRangeMap(Range.openClosed(startTime,endTime ));
					if(intersecting.toString()=="{}"){
						job.setEndTime(endTime);			
						job.setStartTime(startTime);
						timeline.put(Range.openClosed(job.getStartTime(), job.getEndTime()), job.getId());
						startTime = -100;
						
					}
					else{
						startTime -=1;
						endTime -=1;
					}		
			}
			//If job wasn't scheduled within timeline, then make the start time as big as possible to make it start latest
			//this job will not be performed.
			if(startTime!=-100){
				job.setStartTime(Integer.MAX_VALUE);
			}
		}
	}
		
	public ArrayList<Job> findOptimum(ArrayList<Job> jobs){
		sortWeights(jobs);
		Job[] positionedArrayOfJobs = positionTheElement(jobs);
		ArrayList<Job> arrayListJobs = new ArrayList<>(Arrays.asList(positionedArrayOfJobs));
		return arrayListJobs;
	}
	
	public long getObjectiveFunctionValue(){
		return objectiveFunctionValue;
	}
	

}
