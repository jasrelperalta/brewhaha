package networking;

import java.net.*;

public class Client implements Runnable {
    
    // datagram socket for the client
    private DatagramSocket socket;

    // server address
    private InetAddress serverAddress;

    // server port
    private int serverPort;

    // GameUser object for the player
    private GameUser player;

    // thread for the client
    Thread t = new Thread(this);
    
    // constructor
    public Client(int serverPort, String name){
        try {
            this.socket = new DatagramSocket();
            this.serverAddress = InetAddress.getLocalHost();
            this.serverPort = serverPort;

            this.player = new GameUser(name, serverAddress, serverPort);
        } catch (Exception e) {
            System.out.println("Error creating client socket");
        }
    }

    // method to send a packet
    public void sendPacket(byte[] data, InetAddress address, int port){
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        try {
            socket.send(packet);
        } catch (Exception e) {
            System.out.println("Error sending packet");
        }
    }
    
    // method to receive a packet
    public byte[] receivePacket(){
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        try {
            socket.receive(packet);
        } catch (Exception e) {
            System.out.println("Error receiving packet");
        }
        return data;
    }

    // method to send a chat message
    public void sendChatMessage(String message, InetAddress address, int port, String name){
        byte[] data = ("chat " + name + ": " + message).getBytes();
        sendPacket(data, address, port);
    }

    // connect to the server and send a connect message containing the player's name
    public void connect(InetAddress address, int port, String name){
        byte[] data = ("connect " + name).getBytes();
        sendPacket(data, address, port);
    }

    // send ready message
    public void sendReadyMessage(InetAddress address, int port, String name){
        byte[] data = "ready".getBytes();
        sendPacket(data, address, port);
    }
    
    // method to close the socket
    public void close(){
        socket.close();
    }

    // run method for the client
    @Override
    public void run() {
        while (true){
            byte[] data = receivePacket();
            if (new String(data).trim().startsWith("chat")){
                System.out.println(new String(data).trim().substring(5));
            }
            else if (new String(data).trim().startsWith("player")){
                System.out.println(new String(data).trim().substring(7));
            }
            else if (new String(data).trim().startsWith("ready")){
                System.out.println(new String(data).trim());
            }
        }
    }
    
}
