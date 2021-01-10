import java.io.*;
import java.net.*;

public class Client extends ServerAndClient {
    String ipOfServer;
    DataInputStream dIn;

    public static void main(String[] args) throws IOException {
        new Client();
    }

    public Client() throws IOException {
        this.ipOfServer = getString("The IP of the server");
        this.port = getInt("The open Port of the server");

        this.username = getString("Your username");

        Socket s = new Socket(ipOfServer, port); // Create socket, connect with host on port
        System.out.println("Connected with server on IP " + ipOfServer + " and Port " + port);

        dIn = new DataInputStream(s.getInputStream());
        myId = dIn.readByte();
        System.out.println("My ID: " + myId);

        ThreadOutput threadOutput = new ThreadOutput("ThreadOutputClient", s);
        threadOutput.start();
        ThreadInput threadInput = new ThreadInput(username, "ThreadInputClient", s);
        threadInput.start();
    }


}