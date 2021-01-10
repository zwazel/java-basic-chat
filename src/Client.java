import java.io.*;
import java.net.*;

public class Client extends ServerAndClient {
    String username;
    String ipOfServer;
    int portOfServer;

    public static void main(String[] args) throws IOException {
        new Client();
    }

    public Client() throws IOException {
        this.ipOfServer = getString("The IP of the server");
        this.portOfServer = getInt("The open Port of the server");

        Socket s = new Socket(ipOfServer, portOfServer); // Create socket, connect with host on port
        System.out.println("Connected with server on IP " + ipOfServer + " and Port " + portOfServer);

        this.username = getString("Your username");

        ThreadOutput threadOutput = new ThreadOutput("ThreadOutputClient", s);
        threadOutput.start();
        ThreadInput threadInput = new ThreadInput(username, "ThreadInputClient", s);
        threadInput.start();
    }


}