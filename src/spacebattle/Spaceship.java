/*************************************************************************************************************************
 *CMSC 137 PROJECT
 * 
 * @author 
 * @date
 *************************************************************************************************************************/
package spacebattle;

import java.util.Random;
import javafx.scene.image.Image;

class Spaceship extends Sprite {
	private String name;
	private boolean alive;
	private int score;
	private int velocityY; //move bird up/down speed.
    private int gravity;

	private final static Image SPACESHIP_IMAGE = new Image("images/spaceship.png");	
	private final static double INITIAL_X = 200;
	public final static int SPACESHIP_SPEED = 2;
	public final static int ADDED_SPEED = 3;
	public final static int jumpHeight = 100;

	Spaceship(String name, double y){
       	super(Spaceship.INITIAL_X, y,Spaceship.SPACESHIP_IMAGE);
       	Random r = new Random();
		this.name = name;
		this.alive = true;
		this.velocityY = 0;
		this.gravity =1;
		//this.speed = SPACESHIP_SPEED;
	}

	
	//Score getter
	int getScore(){
		return this.score;
	}

	
	//Die setter
    void die(){
    	this.alive = false;
    }
    
    //Deducts a damage to the spaceship's strength
    void getDamage(int damage) {
    	this.die();
 
    }
	
    //Method for jumping
    public void fly(){

        velocityY = -18; // JUMP HEIGHT

    }
    
    //Adds 1 to the score when a UFO was shot
    void gainScore(int increase){
    	this.score+=increase;
    	System.out.println("Score: "+score);
    }
    
    //Alive state getter
    boolean isAlive(){
    	return this.alive;
    } 

    //Method for moving
    void move() {
    	//bird
        velocityY += gravity;
        this.yPos += velocityY;
        this.yPos = Math.max(this.yPos, 0); //apply gravity to current bird.y, limit the bird.y to top of the canvas

	}
	
}