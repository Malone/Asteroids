package universe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;


class SpacePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[][] stars = new int[100][2];
	private Asteroid[] asteroidArray = new Asteroid[4];
	private Spaceship spaceship;
	

	public SpacePanel(){
		super();
		createPoints();
	}
	
	private void doDrawing(Graphics g){
		
		Graphics2D g2d = (Graphics2D)g;		
		g2d.setColor(Color.WHITE);
		
		// Draw starts		
		drawStars(g2d);
				
		// Draw asteroids
		for(int i = 0; i < 4; i++){
			if(i == 3){
				g2d.setColor(Color.YELLOW);				
			}
			asteroidArray[i] = drawAsteroids(g2d, i);
		}
		
		// Draw spaceship
		spaceship = drawSpaceShip(g2d);
        		
	}


	private void createPoints(){		
		// Creates 100 pairs of (x, y) coordinates
		// where x & y are random numbers (Integer range)
		Random r = new Random();		
		for(int i = 0; i < 100; i++){			
			for(int j = 0; j < 2; j++){
				stars[i][j] = Math.abs(r.nextInt());
			}			
		}
		
	}
	
	private void drawStars(Graphics2D g2d){
		
		Dimension size = getSize();		
		Insets insets = getInsets();
		
		boolean dim = true;
		int p = 0, x = 0, y = 0;
		
		int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;
        
        for(int[] spar : stars){
        	for(int d : spar){
        		// Operates on the numbers of the stars array
        		// to put the coordinates into the range of the panel (width & height)
        		if (dim == true){
        			x = (d) % w;
        			dim = false;
        		}
        		else{
        			y = (d) % h;
        		}        			
        	}
        	dim = true;
        	g2d.drawLine(x, y, x, y);
        }
	}
	
	private Asteroid drawAsteroids(Graphics2D g2d, int i){
		
		Asteroid asteroid;
		boolean alreadyCreated = checkArray();
		
		
		if(alreadyCreated == false){
			asteroid = new Asteroid(Asteroid.LARGE, GameObjectTypes.ASTEROID.toString());
		}		
		else{
			asteroid = asteroidArray[i];
			//g2d.setColor(Color.red);
		}
				
        Polygon p = asteroid.getShape();
		g2d.drawPolygon(p);         
        return asteroid;        
		
	}
	
	
	private Spaceship drawSpaceShip(Graphics2D g2d) {
		if(this.spaceship == null) {
			spaceship = new Spaceship(GameObjectTypes.SPACESHIP.toString());
		}
        Polygon p = spaceship.getShape();
		g2d.drawPolygon(p);   
		return spaceship;
	}	
	
	public void moveSpaceship(String action) {
		if(ShipActions.SPIN_LEFT.toString().equals(action) 
				|| ShipActions.SPIN_RIGHT.toString().equals(action)) {
			spaceship.spin(spaceship.location.x, spaceship.location.y, action);
		}else {
			spaceship.update(++spaceship.location.x, ++spaceship.location.y, action);
			System.out.println(this.spaceship.location.x + " " + this.spaceship.location.y);
		}
		
	}
	
	
	// This method will be called at the moment of instantiation 
	// of the SpacePanel class
	@Override
	public void paintComponent(Graphics g ){		
		super.paintComponent(g);
		//System.out.println("paintComponent method !! ************");
		doDrawing(g);
	}

	public Asteroid[] getAsteroidArray() {
		return asteroidArray;
	}

	public void setAsteroidArray(Asteroid[] asteroidArray) {
		this.asteroidArray = asteroidArray;
	}

	private boolean checkArray(){		
		for(Asteroid ast: asteroidArray){
			if(ast == null){
				return false;
			}
		}		
		return true;
	}

	// Overriding this method makes the JPanel able to gain focus an listen to events
	public boolean isFocusTraversable () {
		return true;
	}

	public Spaceship getSpaceship() {
		return spaceship;
	}

	public void setSpaceship(Spaceship spaceship) {
		this.spaceship = spaceship;
	}
	
}

public class AsteroidsGUI extends JFrame implements ActionListener, KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SpacePanel space;
	private GameModel gameModel;
	private JLabel lblScore;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		// Calling the Event Dispatcher Thread
		// invokeLater schedules the task (Runnable) and returns to the main thread
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AsteroidsGUI frame = new AsteroidsGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public AsteroidsGUI() {
		
		// Main window represented by the AsteroidGUI class
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setResizable(false);
		
		// Initialize main panel
		space = new SpacePanel();
		space.setBackground(Color.BLACK);
		space.setLayout(new BorderLayout(0, 0));		
		
		// Panel for the score label
		JPanel upperPanel = new JPanel();		
		upperPanel.setOpaque(false);
		// Add score panel to the main panel
		space.add(upperPanel, BorderLayout.NORTH);
		upperPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		space.addKeyListener(this);
		
		// Create the score label
		lblScore = new JLabel("Score : ");
		lblScore.setHorizontalAlignment(SwingConstants.LEFT);
		lblScore.setOpaque(false);
		lblScore.setForeground(Color.WHITE);
		// Add label to the score panel
		upperPanel.add(lblScore);
		
		// Add main panel to the window
		getContentPane().add(space, BorderLayout.CENTER);
		
		
		gameModel = new GameModel();
		// Game starts with 4 Asteroids created in the SpacePanel class
		// These 4 Asteroids are set in the GameModel object
		gameModel.setAsteroids(space.getAsteroidArray());
		
		gameModel.setSpaceship(space.getSpaceship());
		
		// Timer fires ActionEvent each 300 registering this object (AsteroidsGUI) 
		// as its action listener object, so this will call actionPerformed method
		Timer timer =  new Timer(300, this);
		timer.setInitialDelay(1000);
		timer.start();
	}

	private void reDraw(){
		//lblScore.setText("Score : " + String.valueOf(gameModel.getScore()));
		lblScore.setText("x = " + space.getAsteroidArray()[3].location.x +
				"y = " + space.getAsteroidArray()[3].location.y);
		space.repaint();
	}

	// Listens the 
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// The update method goes through the Asteroids array
		// and updates each of them, meaning: it moves the center
		gameModel.update();
		reDraw();
	}


	@Override
	public void keyTyped(KeyEvent e) {
//		System.out.println("KeyTyped");
//		space.printlLocation();		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("KeyPressed");
		int key = e.getKeyCode();
		String action;
		if(key == KeyEvent.VK_SPACE) {
			action = ShipActions.SHOOT.toString();
		} else if(key == KeyEvent.VK_UP) {
			action = ShipActions.MOVE_FORWARD.toString();
		} else if(key == KeyEvent.VK_DOWN) {
			action = ShipActions.BRAKE.toString();
		} else if(key == KeyEvent.VK_RIGHT) {
			action = ShipActions.SPIN_RIGHT.toString();
		}else if(key == KeyEvent.VK_LEFT) {
			action = ShipActions.SPIN_LEFT.toString();
		}else {
			return;
		}
		System.out.println(action);
		space.moveSpaceship(action);		
	}


	@Override
	public void keyReleased(KeyEvent e) {
//		System.out.println("KeyRelease");
//		space.printlLocation();	
	}
}
