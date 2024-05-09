/*************************************************************************************************************************
 *CMSC 137 PROJECT
 * 
 * @author 
 * @date
 *************************************************************************************************************************/
package spacebattle;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import networking.Client;
import networking.Server;
import networking.GameState;

public class MultiplayerScene {

	private AnchorPane pane;
	private Scene scene;
	private GraphicsContext gc;
	private Canvas canvas;
	private Stage stage;
	private Scene splashScene;

    private boolean playerIsServer;
    private String playerName;
    private Server server;
    private InetAddress serverAddress;
    private int port;

    private TextArea chatArea;
    private TextArea chatInput;

    private TextArea playerList;

    private Button sendButton;
    private Button readyButton;


	MultiplayerScene(Stage stage, Scene splashScene){

		this.pane = new AnchorPane();
		this.scene = new Scene(pane, Game.WINDOW_WIDTH,Game.WINDOW_HEIGHT);
		this.canvas = new Canvas(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.stage = stage;
		this.splashScene = splashScene;
        
        // initialize multiplayer
        this.initMultiplayer();

        // create server or client
        if (this.playerIsServer)
        {
            // ask user for port number
            this.port = Integer.parseInt(JOptionPane.showInputDialog("Enter port number: "));
            this.server = new Server(this.port);
            System.out.println("Server started at " + server.getSocket().getLocalAddress() + " on port " + this.port);
            this.server.setState(GameState.WAITING_FOR_PLAYERS);
        }
        else
        {
            // get port number
            this.port = Integer.parseInt(JOptionPane.showInputDialog("Enter port number: "));

            Client client = new Client(this.port, this.playerName);


            // send connect message to server
            try {
                client.connect(InetAddress.getLocalHost(), this.port, this.playerName);
                System.out.println("Connect message sent");
            } catch (UnknownHostException e) {
                System.out.println("Error sending connect message");
            }


        }

        // create chat area to display chat messages in root
        this.createChatArea();

        // create chat input
        this.createChatInput();

        // create send button
        this.createSendButton();

        // create ready button
        this.createReadyButton();

        // create player list
        this.createPlayerList();

        // add event handlers
        this.addEventHandlers();

    }

    private void initMultiplayer(){
        // ask if user wants to be the server or the client
        // ask user for their name
        this.playerName = JOptionPane.showInputDialog("Enter your name: ");

        // ask user if they want to be the server
        int serverOption = JOptionPane.showConfirmDialog(null, "Do you want to be the server?", "Server Option", JOptionPane.YES_NO_OPTION);
        if (serverOption == JOptionPane.YES_OPTION)
        {
            this.playerIsServer = true;
        }
        else
        {
            this.playerIsServer = false;
        }
    }

    // create the chat area to display chat messages in AnchorPane
    private void createChatArea(){

    }

    // create the chat input
    private void createChatInput(){
        this.chatInput = new TextArea();
        this.chatInput.setPrefSize(400, 50);
        this.chatInput.setTranslateX(100);
        this.chatInput.setTranslateY(300);
        this.pane.getChildren().add(this.chatInput);
    }

    // create the send button
    private void createSendButton(){
        this.sendButton = new Button("Send");
        this.sendButton.setPrefSize(100, 50);
        this.sendButton.setTranslateX(500);
        this.sendButton.setTranslateY(300);
        this.pane.getChildren().add(this.sendButton);
    }

    // create the ready button
    private void createReadyButton(){
        this.readyButton = new Button("Ready");
        this.readyButton.setPrefSize(100, 50);
        this.readyButton.setTranslateX(500);
        this.readyButton.setTranslateY(100);
        this.pane.getChildren().add(this.readyButton);
    }

    // create the player list
    private void createPlayerList(){
        this.playerList = new TextArea();
        this.playerList.setEditable(false);
        this.playerList.setWrapText(true);
        this.playerList.setPrefSize(100, 200);
        this.playerList.setTranslateX(100);
        this.playerList.setTranslateY(100);
        this.pane.getChildren().add(this.playerList);
    }
    
    // add event handlers
    private void addEventHandlers(){
        // send button
        this.sendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent arg0) {
                // send the chat message
                String message = chatInput.getText();
                chatArea.appendText(playerName + ": " + message + "\n");
                chatInput.clear();
            }
        });

        // ready button
        this.readyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent arg0) {
                // send the ready message
                chatArea.appendText(playerName + " is ready\n");
            }
        });
    }

    // get the scene
    public Scene getScene(){
        return this.scene;
    }
}
