package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Scanner scanner;
    private String username;
    private int myId;
    private Socket s;
    private String serverIp;
    private int serverPort;

    public Client() {
        scanner = new Scanner(System.in);

        serverIp = getString("The IP of the server");
        serverPort = getInt("The open Port of the server");

        username = getString("Your username");

        try {
            s = new Socket(serverIp, serverPort);
            System.out.println("Connected to server " + serverIp + " on port " + serverPort + " with username " + username);

            // Reading my ID
            System.out.println("Getting ID from Server...");
            DataInputStream dIn = new DataInputStream(s.getInputStream()); // Create new input stream
            myId = dIn.readInt(); // Read text
            System.out.println("My ID: " + myId);

            // Sending my username
            DataOutputStream dOut = new DataOutputStream(s.getOutputStream()); // Create new output stream, linked with the client that just connected
            dOut.writeUTF(username); // increase id then write id
            dOut.flush(); // Send off the data

            // Start thread
            ThreadHandleMessagesClient threadHandleMessagesClient = new ThreadHandleMessagesClient("threadClientHandleMessages", username, myId, s);
            threadHandleMessagesClient.start();

            // Get messages from server
            printMessagesFromServer();
        } catch (IOException e) {
            System.out.println("Can't create new socket! VERY BAD");
        }
    }

    private void printMessagesFromServer() {
        while (true) {
            DataInputStream dIn = null;
            try {
                dIn = new DataInputStream(s.getInputStream());
                System.out.println(dIn.readUTF());
            } catch (IOException e) {
                System.out.println("Can't print message! VERY BAD");
            }
        }
    }

    private String getString(String command) {
        if (!command.equals("-1")) {
            System.out.print(command + " > ");
        }

        return scanner.nextLine();
    }

    private int getInt(String command) {
        if (!command.equals("-1")) {
            System.out.print(command + " > ");
        }

        return Integer.parseInt(scanner.nextLine());
    }

    public static void main(String[] args) {
        new Client();
    }
}