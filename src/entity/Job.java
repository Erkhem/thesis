package entity;
import java.util.Random;


public class Job {
	private int position;
	private long weight;
	private int deadline;
	private long processingTime;
	
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public long getWeight() {
		return weight;
	}
	public void setWeight(long weight) {
		this.weight = weight;
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
	
	public Job(long weight, int deadline){
		this.weight = weight;
		this.deadline = deadline;
	}
	public Job(long weight, int deadline, long processingTime){
		this.weight = weight;
		this.deadline = deadline;
		this.processingTime = processingTime;
	}
	public Job(long weight, int deadline, int position){
		this.weight = weight;
		this.deadline = deadline;
		this.position = position;
	}
	
	public Job()
		{
			Random generator = new Random();
			weight = generator.nextInt(100)+1;
			processingTime = 1; //generator.nextInt(100)+1;	 					//sec
			deadline = (int) (generator.nextInt(100)+1+processingTime);
		}
	public String toString(){
		return "Weight: "+weight+" processing Time: "+processingTime+" deadline: "+deadline;
	}
}
