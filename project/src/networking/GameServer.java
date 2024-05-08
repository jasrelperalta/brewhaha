package networking;

import java.io.Serializable;
import java.util.function.Consumer;

public class GameServer extends GameNetwork {

    private int port;

    public GameServer(int port, Consumer<Serializable> onRecieveCallback) {
        // calls constructor of NetworkConnection
        super(onRecieveCallback);
        this.port = port;
    }

    @Override
    protected boolean isServer() {
        return true;
    }

    @Override
    protected String getIP() {
        return null;
    }

    @Override
    protected int getPort() {
        return port;
    }
}