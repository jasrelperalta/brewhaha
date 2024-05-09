package networking;

import java.util.ArrayList;
import java.util.Map;

public class GameState {

    // constants for the server state
	public final static int GAME_START=0;
	public final static int IN_PROGRESS=1;
	public final static int GAME_END=2;
	public final static int WAITING_FOR_PLAYERS=3;
    public final static int INITIALIZING_SERVER=4;

    public final static String SERVER_ADDRESS = "0.0.0.0";
    
    // arraylist to store the players
    private ArrayList<GameUser> players;
    
    // constructor
    public GameState(){}

    // method to add a player to the player list
    public void addPlayer(GameUser player){
        players.add(player);
    }

    // method to remove a player from the player list
    public void removePlayer(GameUser player){
        players.remove(player);
    }

    // method to get the player list
    public ArrayList<GameUser> getPlayers(){
        return players;
    }
}
