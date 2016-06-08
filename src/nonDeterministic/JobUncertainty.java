package nonDeterministic;

import java.io.Serializable;
import java.util.Random;

public class JobUncertainty implements Serializable {
	public static int nextId;
	private int id;
	private  int weightStart;
	private  int weightEnd;
	private  int deadline;
	private  long processingTime;
	
	public int getWeightStart() {
		return weightStart;
	}
	public void setWeightStart(int weightStart) {
		this.weightStart = weightStart;
	}
	public int getWeightEnd() {
		return weightEnd;
	}
	public void setWeightEnd(int weightEnd) {
		this.weightEnd = weightEnd;
	}
	public int getDeadline() {
		return deadline;
	}
	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}
	public long getProcessingTime() {
		return processingTime;
	}
	public void setProcessingTime(long processingTime) {
		this.processingTime = processingTime;
	}
	public int getId() {
		return id;
	}	
	
	public JobUncertainty(){
		
	}
	
	public JobUncertainty(int numberOfJobs)
	{
		id = nextId++;
		Random generator = new Random();
		weightStart = generator.nextInt(10)+1;
		weightEnd = weightStart+generator.nextInt(10)+3; //long interval 150
		processingTime =1;	 					//sec
		deadline = (int) (generator.nextInt(numberOfJobs-1))+1;
	}

}
