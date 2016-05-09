import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

import algorithms.Exhaustive;
import algorithms.GeneticAlgorithm;
import algorithms.Greedy;
import algorithms.Population;
import algorithms.Schedule;
import algorithms.SimulatedAnnealing;
import entity.Job;


public class ChooseAndRunAlgorithm {

		static LateJobP1 alg1 = new LateJobP1();
		static ObjectMapper mapper = new ObjectMapper();
		private static String fileDirectory = "data/json_50_test_p1.json";
		
		
		
	public static void main(String[] args) {		
		//to measure execution time
		Stopwatch stopwatch = Stopwatch.createUnstarted();
		SimulatedAnnealing sa = new SimulatedAnnealing();	
		Greedy greedyA = new Greedy();
		
		try {
			//To write to file JSON change the name and directory.
			/*
			ArrayList<Job> jobs12 = new ArrayList<Job>();
			for(int i = 1; i<=10; i++){
				jobs12.add(new Job(10));
			}
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			mapper.writeValue(new File(fileDirectory),jobs12);
			*/
			
			ArrayList<Job> jobs = mapper.readValue(new File(fileDirectory), new TypeReference<ArrayList<Job>>() {
			});
			ArrayList<Job> copyOfJobsSA = new ArrayList<>(jobs);
			ArrayList<Job> copyOfJobsGreedy = new ArrayList<>(jobs);
			
			Exhaustive exhaustive = new Exhaustive();
			
			Population initial = new Population(jobs, 10);
			
			for(int i = 0; i<400; i++){
				initial = GeneticAlgorithm.evolvePopulation(initial);
				for(int j=0; j<initial.getPopulationSize(); j++){
					Schedule scheduleAtJ = initial.getScheduleAt(j);
					System.out.println(initial.getScheduleAt(j).getObjectiveFunctionValue());
				}
				
				Schedule schedule = initial.getFittest();
				int objFun = schedule.getObjectiveFunctionValue();
				
				System.out.println("fittest obj of this generation is "+ objFun+ "\n"+" ------------------------------------- ");
			}
			Schedule schedule = initial.getFittest();
			System.out.println("Genetic Algorithm: "+schedule.getObjectiveFunctionValue());
			System.out.println("Genetic algorithm fittest permutation ");
			printPermutation(schedule.getPermutation());
			

//			stopwatch.start();
//			//Exhaustive search
//			exhaustive.permutation(jobs, jobs.size());  // (jobs, emptyList);
//			System.out.println(exhaustive.getMinGlobal());
//			stopwatch.stop();
			
			//System.out.println("time spend on Exhaustive ----------: "+stopwatch.elapsed(TimeUnit.MILLISECONDS));
			


			Stopwatch stopwatchG1 = Stopwatch.createStarted();
			//Greedy Algorithm with only sorting wieght
			greedyA.sortWeights(copyOfJobsGreedy);
			System.out.println("No disturbance Greedy Algorithm solution is "+exhaustive.calculate(copyOfJobsGreedy));
			stopwatchG1.stop();
		//	System.out.println("time spend on no disturbance Greedy algorithm:  -----------------"+stopwatch.elapsed(TimeUnit.MICROSECONDS));
			
			//stopwatch.reset();
			Stopwatch stopwatchSA = Stopwatch.createStarted();
			//find optimum solution using SA algorithm
			sa.findOptimalSolution(copyOfJobsSA);
			stopwatchSA.stop();
			
		//	System.out.println("time spend on SA: --------------"+stopwatch.elapsed(TimeUnit.MICROSECONDS));
			
			
			Stopwatch stopwatchG2 = Stopwatch.createStarted();
			//Greedy Algorithm with time interval and universal
			greedyA.positionJobUniversal(copyOfJobsGreedy);
			printPermutation(copyOfJobsGreedy);
			greedyA.sortByStartTime(copyOfJobsGreedy);
			printPermutation(copyOfJobsGreedy);
			System.out.println("Greedy Algorithm universal solution is "+exhaustive.calculate(copyOfJobsGreedy));
			stopwatchG2.stop();
			
			
			
			System.out.println("time spend on algorithm: -------------"+stopwatchSA.elapsed(TimeUnit.MILLISECONDS)+"\t"+stopwatchG1.elapsed(TimeUnit.MILLISECONDS)+"\t"+stopwatchG2.elapsed(TimeUnit.MILLISECONDS));
			 			
		} catch (IOException e) {			
			e.printStackTrace();
		}	

	}
	
	public static void printPermutation(ArrayList<Job> jobsToPrint){
		System.out.print("Permutation of Jobs: ");
		for(int i=0; i<jobsToPrint.size(); i++){
			System.out.print(jobsToPrint.get(i).getId()+" ");
		}
		System.out.println("");
	}

}
