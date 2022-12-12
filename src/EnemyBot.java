import robocode.ScannedRobotEvent;
import robocode.Robot;

public class EnemyBot {
	
	//http://mark.random-article.com/weber/java/ch5/lab4.html
	private double bearing, distance, energy, heading,  velocity;
	
	private double x, y; 
	
	private String name;
	
	public double getBearing() {
		return bearing;
	}
	public double getDistance() {
		return distance;
	}
	public double getEnergy() {
		return energy;
	}
	public double getHeading() {
		return heading;
	}
	public double getVelocity() {
		return velocity;
	}
	public String getName() {
		return name;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	public EnemyBot() {
		reset();
	}
	
	public void update(ScannedRobotEvent e) {
		this.bearing = e.getBearing();
		this.distance = e.getDistance();
		this.energy = e.getEnergy();
		this.heading = e.getHeading();
		this.velocity = e.getVelocity();
		this.name = e.getName();
	}
	
	public void update(ScannedRobotEvent e, Robot robot)
	
	public void reset() {
		this.bearing = 0;
		this.distance = 0; 
		this.energy = 0; 
		this.heading = 0 ;
		this.velocity = 0;
		this.name = ""; 
		
		this.x = 0; 
		this.y = 0;
		
		
	
	}
	public  boolean none() {
		return this.name == "";
	}
}
