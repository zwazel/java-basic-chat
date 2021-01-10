import java.io.*;
import java.net.*;

public class Server extends ServerAndClient {
    int idCounter = 0;
    int myId = idCounter;

    public static void main(String[] args) throws IOException {
        new Server();
    }

    public Server() throws IOException {
        this.port = getInt("The Port you are hosting on");
        this.username = getString("Your username");

        ServerSocket ss = new ServerSocket(port);
        System.out.println("Waiting for client to connect...");
        Socket s = ss.accept();

        System.out.println("client connected");

        ThreadOutput threadOutput = new ThreadOutput("ThreadOutputServer", s);
        threadOutput.start();
        ThreadInput threadInput = new ThreadInput(username, "ThreadInputServer", s);
        threadInput.start();
    }
}