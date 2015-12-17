import java.util.ArrayList;
import java.util.Arrays;

import entity.Job;


public class ChooseAndRunAlgorithm {

		static LateJobP1 alg1 = new LateJobP1();
		static float fl = 12;
		
	public static void main(String[] args) {
			
		LateJobP1 greedyAlgorithm = new LateJobP1(1000);
		Job[] optimalSolution = greedyAlgorithm.findOptimum();
		fl = 3;
		
		for(Job j1:optimalSolution){
			int d = j1.getDeadline();
		}
		WithProcessingTime simmulatedAnneaing = new WithProcessingTime(10);
		simmulatedAnneaing.findOptimum();
	}

}
