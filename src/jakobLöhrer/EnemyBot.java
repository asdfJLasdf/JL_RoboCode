package jakobLöhrer;
import robocode.ScannedRobotEvent;
import robocode.Robot;

public class EnemyBot {
	
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
	
	//Predictive targetting
	public void update(ScannedRobotEvent e, Robot robot) {
		this.update(e);
		
		double absoluteBearingDegrees= (robot.getHeading() + e.getBearing());
		if (absoluteBearingDegrees < 0) absoluteBearingDegrees += 360;
		
		// callculate X position out robot
		x = robot.getX() + Math.sin(Math.toRadians(absoluteBearingDegrees)) * e.getDistance();
		
		// callculate Y position out robot
		y = robot.getY() + Math.cos(Math.toRadians(absoluteBearingDegrees)) * e.getDistance();
	
	}
	
	// callculating future X --> idk if this works
	double getFutureX(long when){
		return x + Math.sin(Math.toRadians(getHeading())) * getVelocity() * when;	
	}
	
	double getFutureY(long when){
		return y + Math.cos(Math.toRadians(getHeading())) * getVelocity() * when;	}
	
	
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
	
	public boolean none() {
		return this.name == "";
	}
	
}
