package universe;

import java.awt.Point;
import java.util.Random;

public class MovingObject extends GameObject {
	
	private double direction;
	private int speed;
	
	public MovingObject(){
		super();
		setDirection(getLocation());
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(Point location) {
		
		Random r = new Random();
		
		int radio = 30;
		
		int x = location.x + (r.nextInt() % radio);
		int y = location.y + (r.nextInt() % radio);
		
		direction = Math.atan2((y - location.y), (x - location.x));
		
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	
}
