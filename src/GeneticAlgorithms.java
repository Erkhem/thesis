import java.util.ArrayList;
import java.util.Random;


public class GeneticAlgorithms {

	private ArrayList<Job> zbiorJobow;
	private int biezacyPotrzebnyCzas = 0, wtw = 0;;
	
	GeneticAlgorithms(int ileJobow) {
		zbiorJobow = new ArrayList<Job>();

		for (int j = 0; j < ileJobow; j++){
			zbiorJobow.add(new Job());
		}
	}
	
	int oblicz(ArrayList<Job> tablica) {
		//szuka ustawienia wg kryterium: minimalna wartość z TWT = SUMA(w*T)
		wtw = 0;
		
		for (Job i : tablica) {
			System.out.println(i);
			biezacyPotrzebnyCzas ++;		//sumowanie
			System.out.println(biezacyPotrzebnyCzas + "  biezacy czas"); //@test
			if (biezacyPotrzebnyCzas < i.getDeadline()) {
				wtw += i.getWeight();
			}
			else {
				wtw += i.getWeight()*(biezacyPotrzebnyCzas-i.getDeadline());
			}
		}
		System.out.println("To jest wtw    " + wtw);
		biezacyPotrzebnyCzas = 0;
		return wtw; //wtw temteratura początkowa
	}
	
	void szukajOptimum() {
		int rozwiazanie = oblicz(zbiorJobow), ktory = 0, zamienZ = 0;
		Random generator = new Random();
		ArrayList<Job> kopia = zbiorJobow;
		Job tmp;
		
		while (rozwiazanie > zbiorJobow.size()*50) {
			 ktory = generator.nextInt(zbiorJobow.size());			//losuje dwa elementy które zamienie
			 zamienZ = generator.nextInt(zbiorJobow.size());
			 
			 tmp = kopia.get(ktory);
			 kopia.set(ktory, kopia.get(zamienZ));
			 kopia.set(zamienZ, tmp);
			 
			 if (oblicz(kopia) <= rozwiazanie) {
				 zbiorJobow = kopia;
				 for (Job i : zbiorJobow) {
					 System.out.println(" Jest  " + i);
				 }
			 }
		}
		//kopiuje tablice, losuje, usuwam wylosowany element z kopii tablicy
	}
}
