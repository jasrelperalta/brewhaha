/*************************************************************************************************************************
 *CMSC 137 PROJECT
 * 
 * @author 
 * @date
 *************************************************************************************************************************/
package spacebattle;
import java.util.Random;

import javafx.scene.image.Image;

class Building extends Sprite {
	private double speed;
	private boolean gainedScore;	//boolean value if the UFO already added a score to the spaceship
	
	// Array of building images
    private final static Image[] BUILDING_IMAGES = {
        new Image("images/1.png"),
        new Image("images/2.png"),
        new Image("images/3.png"),
        new Image("images/4.png"),
        new Image("images/5.png"),
        new Image("images/6.png"),
        new Image("images/flipped_1.png"),
        new Image("images/flipped_2.png"),
        new Image("images/flipped_3.png"),
        new Image("images/flipped_4.png"),
        new Image("images/flipped_5.png"),
        new Image("images/flipped_6.png")
    };

	Building(int x, int y, int num){
		super(x, y, BUILDING_IMAGES[num]);
		Random r = new Random();
		this.speed = 6;
	}
	
	//Moves the UFOs
	void move() {
		this.xPos -= this.speed;
	}
	
	void checkCollision(Spaceship spaceship){
	
		if(this.collidesWith(spaceship)){
			spaceship.die();
		}
	}
	
}