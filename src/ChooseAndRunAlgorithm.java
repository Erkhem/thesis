import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
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
		private static String fileDirectory = "data/json_small_test.json";
		
	public static void main(String[] args) {		
		
		SimulatedAnnealing sa = new SimulatedAnnealing();	
		Greedy greedyA = new Greedy();
		
		try {
			//To write to file JSON change the name and directory.
			/*
			ArrayList<Job> jobs12 = new ArrayList<Job>();
			for(int i = 1; i<=5; i++){
				jobs12.add(new Job(5));
			}
			mapper.writeValue(new File("json_small.json"),jobs12);
			*/
			
			ArrayList<Job> jobs = mapper.readValue(new File(fileDirectory), new TypeReference<ArrayList<Job>>() {
			});
			ArrayList<Job> copyOfOriginal = jobs;
			Exhaustive exhaustive = new Exhaustive();
			ArrayList<Job> emptyList = new ArrayList<Job>();
			exhaustive.permutation(jobs, jobs.size());  // (jobs, emptyList);
			System.out.println(exhaustive.getMinGlobal());
			
			//Greedy Algorithm with only sorting wieght
			greedyA.sortWeights(jobs);
			System.out.println("Greedy Algorithm solution is "+exhaustive.calculate(jobs));
			
			
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
