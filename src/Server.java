import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class Server extends ServerAndClient {
    int idCounter = 0;
    int myId = idCounter;
    int maxAmountClients;
    //HashMap<Integer, String> clients = new HashMap<Integer, String>();

    public static void main(String[] args) throws IOException {
        new Server();
    }

    public Server() throws IOException {
        port = getInt("The Port you are hosting on");
        maxAmountClients = getInt("How many Clients are allowed (-1 for a lot)");

        username = "Server";

        System.out.println("My ID: " + myId);

        ServerSocket ss = new ServerSocket(port);
        System.out.println("Waiting for client to connect...");

        ThreadServerAcceptSocket threadServerAcceptSocket = new ThreadServerAcceptSocket(username, "ThreadServerAcceptSocket", ss, idCounter, maxAmountClients);
        threadServerAcceptSocket.start();
    }
}