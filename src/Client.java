import java.io.*;
import java.net.*;

public class Client {
    String username;
    String ipOfServer;
    int portOfServer;

    public static void main(String[] args) throws IOException {
        new Client();
    }

    public Client() throws IOException {
        this.ipOfServer = Main.getString("The IP of the server");
        this.portOfServer = Main.getInt("The open Port of the server");

        Socket s = new Socket(ipOfServer, portOfServer); // Create socket, connect with host on port
        System.out.println("Connected with server on IP " + ipOfServer + " and Port " + portOfServer);

        this.username = Main.getString("Your username");

        ThreadOutput threadOutput = new ThreadOutput("ThreadOutputClient", s);
        threadOutput.start();
        ThreadInput threadInput = new ThreadInput(username, "ThreadInputClient", s);
        threadInput.start();
    }


}