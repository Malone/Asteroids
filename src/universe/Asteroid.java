package universe;

import java.awt.Point;
import java.awt.Polygon;
import java.util.Random;

public class Asteroid extends MovingObject {

	public static final float LARGE = 1.0f;
	public static final float MEDIUM = 0.5f;
	public static final float SMALL = 0.25f;

	private final double[] ANGLES = { 0, 22.5, 45, 67.5, 90, 112.5, 135, 157.5, 180, 202.5, 225, 247.5, 270, 292.5, 315,
			337.5 };

	private int[][] puntas;
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

	private float SIZE = 0.0f;

	

	public Asteroid(float size, String type) {
		super(type);		
		setSIZE(size);
		puntas = createPoints(size, location, false);
		create(size, location, puntas);
	}

	private void create(float size, Point location, int[][] vertices) {
		int[][] points = vertices;// createPoints(size, location);
		int[] x = new int[16];
		int[] y = new int[16];
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
		shape = new Polygon(x, y, 16);
	}

	private int[][] createPoints(float size, Point location, boolean update) {
		int[][] points = new int[16][2];
		double r;
		int i = 0;
		for (double ang : ANGLES) {
			if (update == false) {
				r = (40 + Math.random() * 40 * 0.5) * size;
				radios[i] = r;
			} else {
				r = radios[i];
			}
			points[i][0] = (int) (location.x + r * Math.cos(Math.toRadians(ang)));
			points[i][1] = (int) (location.y + r * Math.sin(Math.toRadians(ang)));
			checkLocation(location, size);
			i++;
		}

		return points;
	}

	public void update() {
		// Moves the center of the Asteroid
		int delta = 10;
		int coorX = 0, coorY = 0;
		double dir = getDirection();

		double r = Math.ceil((40 + (0.99) * 40 * 0.5) * this.getSIZE());

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
		puntas = createPoints(LARGE, location, true);
		create(LARGE, nuevoPunto, puntas);
	}

	public void checkLocation(Point center, float size) {

		this.hidden = true;

		double r = (40 + (0.99) * 40 * 0.5) * size;
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

	public float getSIZE() {
		return this.SIZE;
	}

	public void setSIZE(float sIZE) {
		this.SIZE = sIZE;
	}


}
