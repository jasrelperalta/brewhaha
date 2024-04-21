/*************************************************************************************************************************
 *CMSC 137 PROJECT
 * 
 * @author 
 * @date
 *************************************************************************************************************************/
package spacebattle;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class InstructionScene {
	private StackPane pane;
	private Scene scene;
	private GraphicsContext gc;
	private Canvas canvas;
	private Stage stage;
	private Scene splashScene;
	
	InstructionScene(Stage stage, Scene splashScene){
		this.pane = new StackPane();
		this.scene = new Scene(pane, Game.WINDOW_WIDTH,Game.WINDOW_HEIGHT);
		this.canvas = new Canvas(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.stage = stage;
		this.splashScene = splashScene;
		this.initInstruction();
	}
	
	private void initInstruction(){
		Image bg = new Image("images/instructions.png", 800, 500, false, false);
        this.gc.drawImage(bg, 0, 0);
        
		Button b1 = new Button("Back");
		this.addEventHandler(b1);
	
		pane.getChildren().add(this.canvas);
		pane.getChildren().add(b1);
		b1.setTranslateY(200);
	}
	
	private void addEventHandler(Button btn) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				stage.setScene(splashScene);			//sets the scene back to splash scene
			}
		});
	}	
	
	//scene getter
	Scene getScene(){
		return this.scene;
	}
}



