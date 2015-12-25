import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import algorithms.Greedy;
import algorithms.SimulatedAnnealing;
import entity.Job;


public class ChooseAndRunAlgorithm {

		static LateJobP1 alg1 = new LateJobP1();
		static ObjectMapper mapper = new ObjectMapper();
		
		
	public static void main(String[] args) {
		SimulatedAnnealing sa = new SimulatedAnnealing();	
		Greedy greedyA = new Greedy();
		try {
			ArrayList<Job> jobs = mapper.readValue(new File("json_processingTime_1.json"), new TypeReference<ArrayList<Job>>() {
			});
			ArrayList<Job> copyOfOriginal = jobs;
			//find optimum solution using SA algorithm
			sa.findOptimalSolution(copyOfOriginal);
			//Greedy Algorithm
			ArrayList<Job> greedySolution = greedyA.findOptimum(jobs); //bagtahgui bol position hhgui bgaa
			System.out.println("Greedy solution: " + greedyA.getObjectiveFunctionValue());
			
	//		mapper.writeValue(new File("json_example.json"),);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		

	}

}
