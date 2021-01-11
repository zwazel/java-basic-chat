import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends ServerAndClient {
    int idCounter = 0;
    int myId = idCounter;
    int maxAmountClients;
    ServerSocket ss;
    private DataOutputStream dOut;
    private DataInputStream dIn;

    ThreadServerHandlerInput threadServerHandlerInput;

    public static void main(String[] args) throws IOException {
        new Server();
    }

    public Server() {
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run() throws IOException {
        port = getInt("The Port you are hosting on");
        maxAmountClients = getInt("How many Clients are allowed (-1 for a lot)");

        username = "Server";

        System.out.println("My ID: " + myId);

        ss = new ServerSocket(port);

        threadServerHandlerInput = new ThreadServerHandlerInput("ThreadHandlingServerOutput", username);
        threadServerHandlerInput.start();

        System.out.println("Waiting for client to connect...");

        if (maxAmountClients == -1) {
            maxAmountClients = 999;
        }
        for(int i = 0; i < maxAmountClients; i++) {
            acceptSockets();
        }
    }

    private void acceptSockets() throws IOException {
        // Wait for a connection request, and accept it
        Socket s = ss.accept();

        // Tell the client what ID he has
        dOut = new DataOutputStream(s.getOutputStream()); // Create new output stream, linked with the client that just connected
        dOut.writeInt(++idCounter); // increase id then write id
        dOut.flush(); // Send off the data

        // get the username from the client that just connected
        dIn = new DataInputStream(s.getInputStream()); // Create new input stream
        String clientUsername = dIn.readUTF(); // Read text
        threadServerHandlerInput.sendMessageToClients(clientUsername + " connected with ID " + idCounter); // Print username and id

        // Add client to the map
        threadServerHandlerInput.addSocketToMap(s, clientUsername, idCounter);

        // Start the Threads
        ThreadServerHandlerOutput threadServerHandlerOutput = new ThreadServerHandlerOutput("threadServerHandlerOutput", s, threadServerHandlerInput, idCounter);
        threadServerHandlerOutput.start();
    }
}