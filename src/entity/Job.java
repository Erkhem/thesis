package entity;
import java.io.Serializable;
import java.util.Random;


public class Job implements Serializable {
	public static int nextId;
	private int id;
	private  long weight;
	private  int deadline;
	private  long processingTime;
	private long startTime;
	private long endTime;
	private int position;

	public int getId() {
		return id;
	}
	public long getWeight() {
		return weight;
	}
	public int getDeadline() {
		return deadline;
	}
	public long getProcessingTime() {
		return processingTime;
	}	
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}	
	public Job(long weight, int deadline, long processingTime){
		this.weight = weight;
		this.deadline = deadline;
		this.processingTime = processingTime;
	}
	public Job(int id,long weight, int deadline, long processingTime){
		this.id = id;
		this.weight = weight;
		this.deadline = deadline;
		this.processingTime = processingTime;
	}
	
	public Job(int numberOfJobs)
		{
			id = nextId++;
			Random generator = new Random();
			weight = generator.nextInt(100)+1;
			processingTime =generator.nextInt(numberOfJobs/2)+1;	 					//sec
			deadline = (int) (generator.nextInt(numberOfJobs))+1;
		}
	public String toString(){
		return "Id: "+id+"\n Weight: "+weight+"\n processing Time: "+processingTime+"\n deadline: "+deadline;
	}
	public Job(){		
	}
}
