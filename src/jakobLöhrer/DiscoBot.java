package jakobLöhrer;
import robocode.AdvancedRobot;


import robocode.*;
import robocode.util.Utils;
import java.lang.Math;
import java.awt.geom.Point2D;
import java.awt.Color;
import java.util.Random;


public class DiscoBot extends AdvancedRobot {
	
	private EnemyBot currentEnemy = new EnemyBot();
	private byte scanDirection = 1;
	private byte moveDirection = 1;;
	private Random random = new Random();
	private RobotStatus robotStatus; 
	private double currentX;
	private double currentY;
	private int fieldArea;
	
	/*private _botRadar radar;
	private _botTank tank; 
	private _botCannon cannon;
	static int corner = 0;*/
	
    public void run() {
    	
    	currentEnemy.reset();
    	setAdjustRadarForRobotTurn(true);
    	setAdjustGunForRobotTurn(true);

	//this.goCorner(); 
	
	
    while (true) {
		setTurnRadarRight(360);
		doMove();
		
		setColors(new Color(random.nextInt((255 - 0) + 1) + 0,random.nextInt((255 - 0) + 1) + 0, random.nextInt((255 - 0) + 1) + 0), new Color(random.nextInt((255 - 0) + 1) + 0,random.nextInt((255 - 0) + 1) + 0, random.nextInt((255 - 0) + 1) + 0), new Color(random.nextInt((255 - 0) + 1) + 0,random.nextInt((255 - 0) + 1) + 0, random.nextInt((255 - 0) + 1) + 0));
		
		
		// Corner targeting
  /*  	for(int i = 0; i < 18; i++) {
    	turnGunLeft(5); 
    	turnRadarLeft(5);
    	}
    	for(int i = 0; i < 18; i++) {

    	turnRadarRight(5);
    	turnGunRight(5); bbbb                  
    	}*/
		
		execute();
    }

        }
  
    //Change direction on Wall hit
   // public void onHitWall(HitWallEvent e) { moveDirection *= -1; }
  //  public void onHitRobot(HitRobotEvent e) { moveDirection *= -1; }

    
    //Move to specific Cordinates
    private void goTo(double x, double y) {
        x = x - this.robotStatus.getX();
        y = y - this.robotStatus.getY();
        
        double goAngle = Utils.normalRelativeAngle(Math.atan2(x, y) - getHeadingRadians());
 
        setTurnRightRadians(Math.atan(Math.tan(goAngle)));
    	
        setAhead(Math.cos(goAngle) * Math.hypot(x, y));
    }
    
   
    //Mover to Corner
	public void goCorner() {
		// face Wall
		turnRight(Utils.normalRelativeAngleDegrees(0 - getHeading()));
		//Move to Wall
		ahead(5000);
		// face Corner
		turnLeft(90);
		// Move Wall
		ahead(5000);
		
		// Turn gun to starting point
		turnGunLeft(90);
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
	}
	
	// get bearing towards 2 objects  
	double absoluteBearing(double x1, double y1, double x2, double y2) {
		double xo = x2-x1;
		double yo = y2-y1;
		double hyp = Point2D.distance(x1, y1, x2, y2);
		double arcSin = Math.toDegrees(Math.asin(xo / hyp));
		double bearing = 0;

		if (xo > 0 && yo > 0) { // both pos: lower-Left
			bearing = arcSin;
		} else if (xo < 0 && yo > 0) { // x neg, y pos: lower-right
			bearing = 360 + arcSin; // arcsin is negative here, actuall 360 - ang
		} else if (xo > 0 && yo < 0) { // x pos, y neg: upper-left
			bearing = 180 - arcSin;
		} else if (xo < 0 && yo < 0) { // both neg: upper-right
			bearing = 180 - arcSin; // arcsin is negative here, actually 180 + ang
		}
		
		return bearing;
	}
	
	// degree to bearing
	double normalizeBearing(double angle) {
		while (angle >  180) angle -= 360;
		while (angle < -180) angle += 360;
		return angle;
	}

	
    public void onScannedRobot(ScannedRobotEvent e) {
    	
    	// setting currentEnemy to nearest
    	if (
    			// we have no currentEnemy, or...
    			currentEnemy.none() ||
    			// the one we just spotted is closer, or...
    			e.getDistance() < currentEnemy.getDistance() ||
    			// we found the one we've been tracking
    			e.getName().equals(currentEnemy.getName())
    			) {
    			// track him
    			currentEnemy.update(e, this);
    		}

    	//scan again
    	scanDirection *= -1; 
    	setTurnRadarRight(360 * scanDirection);
    	
        
    
    	
    	//try predictive targeting
    	//get time bullet needs to arrive
    	long time = (long)(currentEnemy.getDistance() / Math.min(400 / currentEnemy.getDistance(), 3));
    	
    	// get future enemy Position --> enemyBot class
    	double futureX = currentEnemy.getFutureX(time);
    	double futureY = currentEnemy.getFutureY(time);
    	double absoluteDegrees = absoluteBearing(getX(), getY(), futureX, futureY);
    	
    	// turn the gun to the predicted x,y location
    	setTurnGunRight(normalizeBearing(absoluteDegrees - getGunHeading()));

    	// shot if not overheated 
    	// adjust bullet streght to distance -> high distance low bullet
    	if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10)
        	setFire(Math.min(400 / currentEnemy.getDistance(), 3));
    	
    
    }
   
    
    // reset currentEnemy if robot dies
    public void onRobotDeath(RobotDeathEvent e) {
    	if (e.getName().equals(currentEnemy.getName())) {
    		currentEnemy.reset();
    	}
    }
    
    // get Status 
    public void onStatus(StatusEvent e) {
      this.robotStatus = e.getStatus();
    }  
    
    
    // set movement
    public void doMove(){
    	//turn sideways to enemy
    	setTurnRight(normalizeBearing(currentEnemy.getBearing() + 90 - (15 * moveDirection)));

    	//change direction every 20 ticks
    	if (getTime() % 20 == 0) {
    		moveDirection *= -1;
    		setAhead(150 * moveDirection);
    	}
    }


}