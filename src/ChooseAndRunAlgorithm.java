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
		RangeMap<Integer,Integer> timeline = TreeRangeMap.create(); 
		
		timeline.put(Range.closed(3, 6), 1);
		RangeMap<Integer,Integer> intersecting = timeline.subRangeMap(Range.closed(10, 15));
		boolean noConflict = intersecting.toString()=="{}" ?true:false;
		timeline.put(Range.closed(2, 5), 2);
		System.out.println(intersecting.toString());
		System.out.println(timeline.toString());
		
		SimulatedAnnealing sa = new SimulatedAnnealing();	
		Greedy greedyA = new Greedy();
		
		try {
			int sum = findSum(1000);
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
			
			/*
			//Greedy Algorithm
			ArrayList<Job> greedySolution = greedyA.findOptimum(jobs); //bagtahgui bol position hhgui bgaa
			System.out.println("Greedy solution: " + greedyA.getObjectiveFunctionValue());
			greedySolution.removeAll(Collections.singleton(null));
			exhaustive.calculate(greedySolution);
			*/
			
			//find optimum solution using SA algorithm
			sa.findOptimalSolution(copyOfOriginal);
			 			
		} catch (IOException e) {			
			e.printStackTrace();
		}	

	}
	public static int findSum(int limit){
		int sum = 0;
		for(int i=0;i<=limit;i++){
			if(i%3==0 || i%5==0){
				sum += i*2;
			}
			else
			{
				sum += i;
			}
		}
		return sum;
	}

}
