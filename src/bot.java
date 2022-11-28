import robocode.AdvancedRobot;
import robocode.*;
import java.lang.Math;


public class bot extends AdvancedRobot {

    public void run() {

        while (true) {

            ahead(500);

           // turnGunRight(360);

turnLeft(Math.random()*(360 - 1) + 1);
back(100);
setTurnRadarRight(360)  ;         // turnGunRight(360);

        }

    }


    public void onScannedRobot(ScannedRobotEvent e) {
        ahead(10);
        turnGunRight(-10);

        fire(3);

    }

}