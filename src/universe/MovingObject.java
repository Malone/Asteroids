package universe;

import java.awt.Point;
import java.util.Random;

public abstract class MovingObject extends GameObject {
	
	private double direction;
	private int speed;
	protected Point location;
	
	public MovingObject(String type){
		super();
		locate(type);
		setDirection(getLocation(), null);
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(Point location, int[][] firstPoint) {
		
		Random r = new Random();
		int x, y;
		
		int radio = 30;
		if(firstPoint == null) {
			x = location.x + (r.nextInt() % radio);
			y = location.y + (r.nextInt() % radio);
		}else {
			x = firstPoint[0][0];
			y = firstPoint[0][1];
		}		
		// direction, in term of an angle of line (x, y)----(location.x, location.y) && horizontal(??) 
		direction = Math.atan2((y - location.y), (x - location.x));
		
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}	
	
	@Override
	public void locate(String type) {
		if(type.equalsIgnoreCase(GameObjectTypes.ASTEROID.toString())) {
			Random r = new Random();
			int x = (Math.abs(r.nextInt())) % 800;
			int y = (Math.abs(r.nextInt())) % 600;
			this.location = new Point(x, y);
		}else if(type.equalsIgnoreCase(GameObjectTypes.SPACESHIP.toString())) {
			this.location = new Point(400, 300);
		}
		
	}
}
