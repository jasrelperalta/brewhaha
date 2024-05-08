/*************************************************************************************************************************
 *CMSC 137 PROJECT
 * 
 * @author 
 * @date
 *************************************************************************************************************************/
package spacebattle;

import java.io.Serializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.function.Consumer;

import javax.swing.JOptionPane;

import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import networking.GameClient;
import networking.GameNetwork;
import networking.GameServer;


public class MultiplayerScene {

    public static GameNetwork connection;
	private boolean playerIsServer;
    private TextArea chatArea; // chat messages
	private StackPane pane;
	private Scene scene;
	private GraphicsContext gc;
	private Canvas canvas;
	private Stage stage;
	private Scene splashScene;
    private String playerName;
	
     
	
	MultiplayerScene(Stage stage, Scene splashScene){      
        // Initialize variables        
        // ask user for their name
        playerName = JOptionPane.showInputDialog("Enter your name: ");

        // ask user if they want to be the server
        int serverOption = JOptionPane.showConfirmDialog(null, "Do you want to be the server?", "Server Option", JOptionPane.YES_NO_OPTION);
        if (serverOption == JOptionPane.YES_OPTION)
        {
            playerIsServer = true;
        }
        else
        {
            playerIsServer = false;
        }
        
        // Initialize JavaFX components
		this.pane = new StackPane();
		this.scene = new Scene(pane, Game.WINDOW_WIDTH,Game.WINDOW_HEIGHT);
		this.canvas = new Canvas(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.stage = stage;
		this.splashScene = splashScene;

        // Generate components
        AnchorPane root = new AnchorPane();
        VBox chatBox = generateChatBox();
        VBox playerList = generatePlayerList();
		Button backButton = new Button("Back");
        backMainMenu(backButton);

        Button startGameButton = new Button("Start Game");

        // Add components to anchor pane
		root.getChildren().add(chatBox);
        root.getChildren().add(playerList);
		root.getChildren().add(backButton);
        root.getChildren().add(startGameButton);

        // Set component positions in anchor pane
        AnchorPane.setTopAnchor(chatBox, 10.0);
        AnchorPane.setLeftAnchor(chatBox, 10.0);
        AnchorPane.setTopAnchor(playerList, 10.0);
        AnchorPane.setRightAnchor(playerList, 10.0);
        AnchorPane.setBottomAnchor(backButton, 10.0);
        AnchorPane.setLeftAnchor(backButton, 10.0);
        AnchorPane.setBottomAnchor(startGameButton, 10.0);
        AnchorPane.setRightAnchor(startGameButton, 10.0);

        // Show scene
        pane.getChildren().add(root);


        // Start connection        
		if (playerIsServer)
        {
            connection = createServer();
            chatArea.appendText("Connecting to clients...\n");
        }
        else
        {
            connection = createClient();
            chatArea.appendText("Connecting to server...\n");
        }

		try
        {
            connection.startConnection();
        }
        catch ( Exception e )
        {
            System.err.println("Error: Failed to start connection");
            System.exit(1);
        }
        
	}

     // Initialize Server
     private GameServer createServer() {
         return new GameServer(8000, data -> {
             // Below: Runs whenever data is revieved from the client.
             //        runLater() gives JavaFX time to draw GUI.
             Platform.runLater(() -> {
                     // Display in chat message box
                     chatArea.appendText(data.toString() + "\n");
             });
         });
     }

     // Initialize Client
     private GameClient createClient() {
         // localhost IP address
         return new GameClient("127.0.0.1", 8000, data -> {
             // Below: Runs whenever data is revieved from the server.
             //        runLater() gives JavaFX time to draw GUI.
             Platform.runLater(() -> {
                     // Display in chat message box
                     chatArea.appendText(data.toString() + "\n");
             });
         });
     }
     
    // Generate player list
    private VBox generatePlayerList()
    {
        TextArea playerList = new TextArea();
        playerList.setEditable(false);
        playerList.setPrefSize(100, 200);
        playerList.appendText("Players:\n");

        // Add player to list
        playerList.appendText(playerName + "\n");
        
        VBox playerListBox = new VBox(20, playerList);
        playerListBox.getStyleClass().add("player-list-box");

        return playerListBox;
    }

     // Generate chat window
    private VBox generateChatBox()
    {  
        // sends messages
        TextField chatField = new TextField();
        // set size of chat field
        chatField.setPrefSize(250, 20);
        chatField.setOnAction(event -> {
            // Specify if message is from server or client
            String message = playerName + ": ";

            message += chatField.getText();
            chatField.clear();
            chatArea.appendText(message + "\n");

            try {
                connection.send(message);
            }
            catch (Exception e) {
                chatArea.appendText("Failed to send\n");
            }
        });

        // displays messages
        chatArea = new TextArea();
        chatArea.setEditable(false);

        // set size of chat area
        chatArea.setPrefSize(250, 350);

        VBox chatBox = new VBox(20, chatArea, chatField);

        return chatBox;
    }


 	
 	private void backMainMenu(Button btn) {
 		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

 			public void handle(MouseEvent arg0) {
 				stage.setScene(splashScene);		//sets the scene back to the mainmenu scene
 			}
 		});
 	}	
 	//scenes getter
 	Scene getScene(){
 		return this.scene;
 	}

}
