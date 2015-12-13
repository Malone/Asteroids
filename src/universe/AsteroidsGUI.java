package universe;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.SwingConstants;


class SpacePanel extends JPanel{
	
	private int[][] stars = new int[100][2];
	//private Asteroid asteroid;
	private Asteroid[] asteroidArray = new Asteroid[4];
	

	public SpacePanel(){
		super();
		createPoints();
	}
	
	private void doDrawing(Graphics g){
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setColor(Color.WHITE);
		
		drawStars(g2d);
				
		
		for(int i = 0; i < 4; i++){
			if(i == 3){
				g2d.setColor(Color.YELLOW);				
			}
			asteroidArray[i] = drawAsteroids(g2d, i);
		}       
        		
	}
	
	private void createPoints(){
		
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
			asteroid = new Asteroid(Asteroid.LARGE);
		}		
		else{
			asteroid = asteroidArray[i];
			//g2d.setColor(Color.red);
		}
				
        Polygon p = asteroid.getShape();
		g2d.drawPolygon(p);         
        return asteroid;        
		
	}
	
	public void paintComponent(Graphics g ){
		super.paintComponent(g);
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
	
}

public class AsteroidsGUI extends JFrame implements ActionListener{

	private SpacePanel space;
	private GameModel gameModel;
	private int i;
	private JLabel lblScore;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setResizable(false);
		
		space = new SpacePanel();
		space.setBackground(Color.BLACK);
		space.setLayout(new BorderLayout(0, 0));		
		
		JPanel upperPanel = new JPanel();
		
		upperPanel.setOpaque(false);
		
		space.add(upperPanel, BorderLayout.NORTH);
		upperPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		lblScore = new JLabel("Score : ");
		lblScore.setHorizontalAlignment(SwingConstants.LEFT);
		lblScore.setOpaque(false);
		lblScore.setForeground(Color.WHITE);
		upperPanel.add(lblScore);
		
		getContentPane().add(space, BorderLayout.CENTER);
		
		gameModel = new GameModel();
		gameModel.setAsteroids(space.getAsteroidArray());
		
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gameModel.update();
		reDraw();
	}
}
