/*************************************************************************************************************************
 *CMSC 137 PROJECT
 * 
 * @author 
 * @date
 *************************************************************************************************************************/
package spacebattle;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
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
            //System.out.println("Server");
            // ask user for port number
            TextInputDialog portDialog = new TextInputDialog();
            portDialog.setTitle("Enter Port");
            portDialog.setHeaderText("Enter port number:");
            Optional<String> portResult = portDialog.showAndWait();
            if (portResult.isPresent()) {
                this.port = Integer.parseInt(portResult.get());
            }
            this.server = new Server(this.port);
            System.out.println("Server started at " + server.getSocket().getLocalAddress() + " on port " + this.port);
            this.server.setState(GameState.WAITING_FOR_PLAYERS);
        }
        else
        {
            // get port number
            //this.port = Integer.parseInt(JOptionPane.showInputDialog("Enter port number: "));
            TextInputDialog portDialog = new TextInputDialog();
            portDialog.setTitle("Enter Port");
            portDialog.setHeaderText("Enter port number:");
            Optional<String> portResult = portDialog.showAndWait();
            if (portResult.isPresent()) {
                this.port = Integer.parseInt(portResult.get());
            }
            Client client = new Client(this.port, this.playerName, new Client.ClientCallback() {
            @Override
            public void onMessageReceived(String message) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        chatArea.appendText(message + "\n");
                    }
                });
    }
});

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
        //System.out.println("start");

        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Enter Name");
        nameDialog.setHeaderText("Please enter your name:");
        Optional<String> nameResult = nameDialog.showAndWait();
        if (nameResult.isPresent()) {
            this.playerName = nameResult.get();
        }

        //System.out.println("here");

        // ask user if they want to be the server
        Alert serverDialog = new Alert(AlertType.CONFIRMATION);
        serverDialog.setTitle("Server Option");
        serverDialog.setHeaderText("Do you want to be the server?");

        // Change the button text
        ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonData.NO);
        serverDialog.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> serverResult = serverDialog.showAndWait();
        if (serverResult.get() == yesButton){
            this.playerIsServer = true;
        } else {
            this.playerIsServer = false;
        }
    }

    // create the chat area to display chat messages in AnchorPane
    private void createChatArea(){
        this.chatArea = new TextArea();
        this.chatArea.setEditable(false);
        this.chatArea.setWrapText(true);
        this.chatArea.setPrefSize(300, 200);
        this.chatArea.setTranslateX(200);
        this.chatArea.setTranslateY(100);
        
        Label chatHeader = new Label("Chat");
        chatHeader.setTranslateX(200); // Same X position as the TextArea
        chatHeader.setTranslateY(80);
        this.pane.getChildren().addAll(chatHeader, this.chatArea);

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
        //this.pane.getChildren().add(this.playerList);

        Label playersHeader = new Label("Players");
        playersHeader.setTranslateX(100); // Same X position as the TextArea
        playersHeader.setTranslateY(80);

        this.pane.getChildren().addAll(playersHeader, this.playerList);
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
                String chatMessage = "chat " + playerName + ": " + message;
                // send the message to the server
                if (playerIsServer) {
                    MultiplayerScene.this.server.sendToClients(chatMessage.getBytes());
                } else {
                    //client.sendChatMessage(message, serverAddress, MultiplayerScene.this.port, playerName);
                }
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
