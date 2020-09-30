/*
 * GameModel -- Class to represent the game without the GUI

 * Defines width, height of the universe (800x600)
 * Score is initially set to Zero (0)
 * Level is set to 1
 * Lives is set to 4
 * Defines an array of Asteroids objects
 * */


package universe;

public class GameModel {

	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	
	private int lives = 4;
	private int level = 1;	
	private int score = 0;
	
	private Asteroid[] asteroids;
	private Spaceship spaceship;
	
	public GameModel(){
		super();
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public void update(){
		setScore(getScore() + 1);
		for(Asteroid ast : asteroids){
			ast.update();
		}		
	}

	public Asteroid[] getAsteroids() {
		return asteroids;
	}

	public void setAsteroids(Asteroid[] asteroid) {
		this.asteroids = asteroid;
	}

	public Spaceship getSpaceship() {
		return spaceship;
	}

	public void setSpaceship(Spaceship spaceship) {
		this.spaceship = spaceship;
	}	
	
	
}
