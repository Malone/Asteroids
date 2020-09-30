package universe;

import java.awt.Point;
import java.awt.Polygon;
import java.util.Random;


public abstract class GameObject {
	
	//protected Point location;
	protected Polygon shape;
	
	
	public GameObject(){
		super();		
	}

//	public Point getLocation() {
//		return location;
//	}
//
//	
//	public void setLocation(Point location) {
//		this.location = location;
//	}

	public Polygon getShape() {
		return shape;
	}

	public void setShape(Polygon shape) {
		this.shape = shape;
	}	

	public abstract void locate(String type);
}
