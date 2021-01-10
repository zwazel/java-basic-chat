import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class Server extends ServerAndClient {
    int idCounter = 0;
    int myId = idCounter;
    HashMap<Integer, String> clients = new HashMap<Integer, String>();

    public static void main(String[] args) throws IOException {
        new Server();
    }

    public Server() throws IOException {
        this.port = getInt("The Port you are hosting on");
        this.username = getString("Your username");

        System.out.println("My ID: " + myId);

        ServerSocket ss = new ServerSocket(port);
        System.out.println("Waiting for client to connect...");

        ThreadServerAcceptSocket threadServerAcceptSocket = new ThreadServerAcceptSocket(this.username, "ThreadServerAcceptSocket", ss, idCounter);
        threadServerAcceptSocket.start();
    }
}