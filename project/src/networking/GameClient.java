package networking;

import java.io.Serializable;
import java.util.function.Consumer;

public class GameClient extends GameNetwork{

    private String ip;
    private int port;

    public GameClient(String ip, int port, Consumer<Serializable> onRecieveCallback) {
        super(onRecieveCallback);
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected boolean isServer() {
        return false;
    }

    @Override
    protected String getIP() {
        return ip;
    }

    @Override
    protected int getPort() {
        return port;
    }
}