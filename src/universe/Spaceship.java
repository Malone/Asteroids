package universe;

import java.awt.Point;
import java.awt.Polygon;

public class Spaceship extends MovingObject {
	
	// Spaceship has same size all the time
	// Movement will be
	//		- Forward : Up Arrow
	//		- Break   : Down Arrow
	//		- Spin counterclockwise : Left Arrow
	//		- Spin clockwise : Right Arrow
	
	// Initial position : center of the panel
	// Direction : none
	// Speed : none
	
	private int[][] puntas;
	private final double[] ANGLES = {270, 45, 135};
	private final double[] ROTATION_ANGLES = {270, 45, 135};
	private double[] radios = new double[ANGLES.length];
	
	private boolean hidden = false;
	private boolean horizontalLeftDown = false;
	private boolean horizontalLeftUp = false;
	private boolean horizontalRightDown = false;
	private boolean horizontalRightUp = false;

	private boolean verticalTopDown = false;
	private boolean verticalTopUp = false;
	private boolean verticalBottomDown = false;
	private boolean verticalBottomUp = false;
	
	public Spaceship(String type) {
		super(type);
		puntas = createPoints(location, false);
		create(location, puntas);
	}


	private int[][] createPoints(Point location, boolean update) {
		int[][] points = new int[3][2];
		// (x, y - r)
		// 
		double r;
		int i = 0;
		int[][] firstPoint = new int[1][2];
		for (double ang : ANGLES) {
			if (update == false) {
				r = 20; 
				radios[i] = r;
			} else {
				r = radios[i];
			}
			points[i][0] = (int) (location.x + r * Math.cos(Math.toRadians(ang)));
			points[i][1] = (int) (location.y + r * Math.sin(Math.toRadians(ang)));
			if(ang == 270) {
				firstPoint[0] = points[i]; 
			}			
			setDirection(location, firstPoint);
			checkLocation(location);
			i++;
		}

		return points;
	}
	
	public void spin(int i, int j, String action) {
		updateSpin(location, puntas, action);
	}
	
	private void updateSpin(Point location, int[][] puntas, String action) {
		int[][] points = new int[3][2];
		int i = 0;
		int r = 20;
		int delta = 0;
		if(ShipActions.SPIN_LEFT.toString().equals(action)) {
			delta = -5;
		}else if(ShipActions.SPIN_RIGHT.toString().equals(action)){
			delta = 5;
		}
		for (int j = 0; j < ROTATION_ANGLES.length; j++) {
			ROTATION_ANGLES[j] = (ROTATION_ANGLES[j] + delta) % 360;
		}
		for (double ang : ROTATION_ANGLES) {			
			points[i][0] = (int) (location.x + r * Math.cos(Math.toRadians(ang)));
			points[i][1] = (int) (location.y + r * Math.sin(Math.toRadians(ang)));
			System.out.println(points[i][0] + " " + points[i][1]);
			i++;
		}
		System.out.println("Location : " + location);
		create(location, points);
	}
	
	
	private void create(Point location, int[][] vertices) {
		int[][] points = vertices;// createPoints(size, location);
		int[] x = new int[3];
		int[] y = new int[3];
		int j = 0;
		boolean flag = true;
		for (int[] puntos : points) {
			for (int dot : puntos) {
				if (flag) {
					x[j] = dot;
				} else {
					y[j] = dot;
				}
				flag = !flag;
			}
			j++;
		}
		shape = new Polygon(x, y, 3);
	}

	public void checkLocation(Point center) {

		this.hidden = true;

		double r = 40;
		int x = center.x;
		int y = center.y;

		// Is the left side of the asteroid among the range of the panel (0 - 800) ??
		this.horizontalLeftDown = (x - r) > 0;
		this.horizontalLeftUp = (x - r) < 800;
		boolean horizontalLeft = (this.horizontalLeftDown && this.horizontalLeftUp);// Left side within visibility range

		// Is the right side of the asteroid among the range of the panel (0 - 800) ??
		this.horizontalRightDown = (x + r) > 0;
		this.horizontalRightUp = (x + r) < 800;
		boolean horizontalRight = (this.horizontalRightDown && this.horizontalRightUp);// Right side within visibility
																						// range

		boolean horizontalVisibility = (horizontalLeft || horizontalRight);

		this.verticalTopDown = (y - r) > 0;
		this.verticalTopUp = (y - r) < 600;
		boolean verticalTop = (verticalTopDown && verticalTopUp);// Top side within visibility range

		this.verticalBottomDown = (y + r) > 0;
		this.verticalBottomUp = (y + r) < 600;
		boolean verticalBottom = (verticalBottomDown && verticalBottomUp);// Bottom side within visibility range
		boolean verticalVisibility = (verticalTop || verticalBottom);

		if (horizontalVisibility == true && verticalVisibility == true) {
			this.hidden = false;
		}
	}
	
	public void update(int x, int y, String action) {
		// Moves the center of the Asteroid
		/* delta
		 *    - MOVE FORWARD : delta increments exponentially while pressing UP
		 *    - BREAK : delta decreases exponentially while pressing DOWN
		 * */
		int delta = 10; 
		int coorX = 0, coorY = 0;
		setDirection(location, puntas);
		double dir = getDirection();

		double r = Math.ceil((40 + (0.99) * 40 * 0.5));

		double deltaX = delta * Math.cos(dir);
		double deltaY = delta * Math.sin(dir);
		coorX = location.x + (int) deltaX;
		coorY = location.y + (int) deltaY;
		if (this.hidden) {
			if (!this.horizontalLeftDown) {
				coorX += 800 + 2 * r;
			} else if (!this.horizontalLeftUp) {
				coorX -= 800 + 2 * r;
			}
			if (!this.verticalBottomDown) {
				coorY += 600 + 2 * r;
			} else if (!this.verticalBottomUp) {
				coorY -= 600 + 2 * r;
			}
		}

		Point nuevoPunto = new Point(coorX, coorY);
		location.setLocation(nuevoPunto);
		puntas = createPoints(location, true);
		create(nuevoPunto, puntas);
	}

}
