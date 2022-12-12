import robocode.AdvancedRobot;

import robocode.*;
import robocode.util.Utils;
import java.lang.Math;

//http://mark.random-article.com/weber/java/robocode/lesson4.html

public class bot extends AdvancedRobot {
	
	private EnemyBot currentEnemy = new EnemyBot();
	private byte scanDirection = 1;
	
	/*private _botRadar radar;
	private _botTank tank; 
	private _botCannon cannon;
	static int corner = 0;*/
	private RobotStatus robotStatus; 
	private double currentX;
	private double currentY;
	private int fieldArea;
	
    public void run() {
    	currentEnemy.reset();
    	setAdjustRadarForRobotTurn(true);
    	setAdjustGunForRobotTurn(true);

	//this.goCorner(); 
	
	
    while (true) {
		setTurnRadarRight(360);
    	
    	
  /*  	for(int i = 0; i < 18; i++) {
    	turnGunLeft(5); 
    	turnRadarLeft(5);
    	}
    	for(int i = 0; i < 18; i++) {

    	turnRadarRight(5);
    	turnGunRight(5);
    	}*/
		execute();
    }

        }


    
    //Move to specific Cordinates
    private void goTo(double x, double y) {
        x = x - this.robotStatus.getX();
        y = y - this.robotStatus.getY();
        
        double goAngle = Utils.normalRelativeAngle(Math.atan2(x, y) - getHeadingRadians());
 
        setTurnRightRadians(Math.atan(Math.tan(goAngle)));
    	
        setAhead(Math.cos(goAngle) * Math.hypot(x, y));
    }
    
   
    
	public void goCorner() {
		// We don't want to stop when we're just turning...
		// turn to face the wall to the "right" of our desired corner.
		turnRight(Utils.normalRelativeAngleDegrees(0 - getHeading()));
		// Ok, now we don't want to crash into any robot in our way...
		// Move to that wall
		ahead(5000);
		// Turn to face the corner
		turnLeft(90);
		// Move to the corner
		ahead(5000);
		// Turn gun to starting point
		turnGunLeft(90);
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);

	}


    public void onScannedRobot(ScannedRobotEvent e) {
    	
    	if (
    			// we have no currentEnemy, or...
    			currentEnemy.none() ||
    			// the one we just spotted is closer, or...
    			e.getDistance() < currentEnemy.getDistance() ||
    			// we found the one we've been tracking
    			e.getName().equals(currentEnemy.getName())
    			) {
    			// track him
    			currentEnemy.update(e);
    		}
    	
    	if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10)
        	setFire(Math.min(400 / currentEnemy.getDistance(), 3));
    	
    	
        
        scanDirection *= -1; // changes value from 1 to -1
    	setTurnRadarRight(360 * scanDirection);
    	setTurnGunRight(getHeading() - getGunHeading() + e.getBearing());
    	//fire(3);
    	//fire(3);
    	
    	//try predictive targetting
    	//get time bullet needs to arrive
    	long time = (long)(currentEnemy.getDistance() / Math.min(400 / currentEnemy.getDistance(), 3));
    	
    }
    
    public void onRobotDeath(RobotDeathEvent e) {
    	if (e.getName().equals(currentEnemy.getName())) {
    		currentEnemy.reset();
    	}
    }
    public void onStatus(StatusEvent e) {
      this.robotStatus = e.getStatus();
    }    


}