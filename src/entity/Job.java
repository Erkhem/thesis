package entity;
import java.io.Serializable;
import java.util.Random;


public class Job implements Serializable {
	public static int nextId;
	private int id;
	private long weight;
	private int deadline;
	private long processingTime;
/*	
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	*/
	public int getId() {
		return id;
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
//		this.position = position;
	}
	
	public Job(int numberOfJobs)
		{
			id = nextId++;
			Random generator = new Random();
			weight = generator.nextInt(numberOfJobs/2)+1;
			processingTime =generator.nextInt(numberOfJobs)+1;	 					//sec
			deadline = (int) (generator.nextInt(numberOfJobs));
		}
	public Job(){
		
	}
	public String toString(){
		return "Id: "+id+"\n Weight: "+weight+"\n processing Time: "+processingTime+"\n deadline: "+deadline;
	}
}
