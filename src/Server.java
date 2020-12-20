import java.io.*;
import java.net.*;

public class Server {
    int port;
    String username;
    int idCounter = 0;
    int myId = idCounter;

    public static void main(String[] args) throws IOException {
        new Server();
    }

    public Server() throws IOException {
        this.port = Main.getInt("The Port you are hosting on");
        this.username = Main.getString("Your username");

        ServerSocket ss = new ServerSocket(port);
        System.out.println("Waiting for client to connect...");
        Socket s = ss.accept();

        System.out.println("client connected");

        String text = Main.getString("Send message to Clients");

        while (!text.equals("/dc")) {
            Main.sendMessageToNetwork(s, username, "Clients", text);
            text = Main.getString("Send message to clients");
        }
    }
}