import java.util.ArrayList;


public class ChooseAndRunAlgorithm {

	static LateJobP1 alg1 = new LateJobP1();
	public static void main(String[] args) {
		ArrayList<Job> sorted = alg1.getUserInput();
		alg1.sortWeights(sorted);
		alg1.positionTheElement(sorted);
		
		GeneticAlgorithms geneticAlgr = new GeneticAlgorithms(10);
		geneticAlgr.szukajOptimum();
	}

}
