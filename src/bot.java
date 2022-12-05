import robocode.AdvancedRobot;
import robocode.*;
import robocode.util.Utils;
import java.lang.Math;


public class bot extends AdvancedRobot {
	/*private _botRadar radar;
	private _botTank tank; 
	private _botCannon cannon;
	static int corner = 0;*/
	private RobotStatus robotStatus; 
	private double currentX;
	private double currentY;
	private int fieldArea;
    public void run() {
    	//goCorners();
	this.goCorner(); 
	
    while (true) {
    	for(int i = 0; i < 18; i++) {
    	turnGunLeft(5); 
    	turnRadarLeft(5);
    	}
    	for(int i = 0; i < 18; i++) {

    	turnRadarRight(5);
    	turnGunRight(5);
    	}
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
        fire(3);
        fire(3);

    }

    public void onStatus(StatusEvent e) {
      this.robotStatus = e.getStatus();
    }    


}