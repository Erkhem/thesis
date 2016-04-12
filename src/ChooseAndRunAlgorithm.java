import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

import algorithms.Exhaustive;
import algorithms.Greedy;
import algorithms.SimulatedAnnealing;
import entity.Job;


public class ChooseAndRunAlgorithm {

		static LateJobP1 alg1 = new LateJobP1();
		static ObjectMapper mapper = new ObjectMapper();
		private static String fileDirectory = "data/json_300_test.json";
		//to measure execution time
		Stopwatch stopwatch = Stopwatch.createUnstarted();
		
	public static void main(String[] args) {		
		
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
			ArrayList<Job> copyOfOriginal = new ArrayList<>(jobs);
			
			Exhaustive exhaustive = new Exhaustive();
			
			/*//Exhaustive search
			exhaustive.permutation(jobs, jobs.size());  // (jobs, emptyList);
			System.out.println(exhaustive.getMinGlobal());
			
			*/
			
			//Greedy Algorithm with only sorting wieght
			greedyA.sortWeights(jobs);
			System.out.println("No disturbance Greedy Algorithm solution is "+exhaustive.calculate(jobs));
			
			
			//Greedy Algorithm with time interval and universal
			greedyA.positionJobUniversal(jobs);
			greedyA.sortByStartTime(jobs);
			System.out.println("Greedy Algorithm universal solution is "+exhaustive.calculate(jobs));
			
			//find optimum solution using SA algorithm
			sa.findOptimalSolution(copyOfOriginal);
			 			
		} catch (IOException e) {			
			e.printStackTrace();
		}	

	}

}
