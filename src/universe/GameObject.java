package universe;

import java.awt.Point;
import java.awt.Polygon;
import java.util.Random;


public class GameObject {
	
	protected Point location;
	protected Polygon shape;
	
	
	public GameObject(){
		super();
		locate();
	}

	public Point getLocation() {
		return location;
	}

	
	public void setLocation(Point location) {
		this.location = location;
	}

	public Polygon getShape() {
		return shape;
	}

	public void setShape(Polygon shape) {
		this.shape = shape;
	}	

	private void locate(){
		Random r = new Random();
		
		int x = (Math.abs(r.nextInt())) % 800;
		int y = (Math.abs(r.nextInt())) % 600;
		location = new Point(x, y);
	}
}
