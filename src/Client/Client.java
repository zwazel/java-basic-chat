package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Scanner scanner;
    private String username; // my username
    private int myId; // My id
    private Socket s; // my socket
    private String serverIp; // The ip of the server
    private int serverPort; // the open port of the server
    private boolean running = true; // are we running?
    private ThreadHandleMessagesClient threadHandleMessagesClient; // Our thread which handles our messages

    public Client() {
        scanner = new Scanner(System.in);

        serverIp = getString("The IP of the server"); // Get the IP of the server
        serverPort = getInt("The open Port of the server"); // Get the open port of the server

        username = getString("Your username"); // get my username

        init();
    }

    private void init() {
        try {
            s = new Socket(serverIp, serverPort); // instanciate new socket with IP and PORT
            System.out.println("Connected to server " + serverIp + " on port " + serverPort + " with username " + username); // Tell the user that we have successfully established a connection to the server

            // Reading my ID
            System.out.println("Getting ID from Server..."); // Tell the user that we're getting our ID right now
            DataInputStream dIn = new DataInputStream(s.getInputStream()); // Create new input stream
            myId = dIn.readInt(); // Read int from the server
            System.out.println("My ID: " + myId); // Set the ID to the number we got from server

            // Sending my username to the server, so he can add us to the hashmap
            DataOutputStream dOut = new DataOutputStream(s.getOutputStream()); // Create new output stream, linked with the client that just connected
            dOut.writeUTF(username); // put the username in the stream
            dOut.flush(); // Send off the data

            // Start thread which handles our messages
            threadHandleMessagesClient = new ThreadHandleMessagesClient("HandleMessages " + username + " " + myId, username, myId, s); // instanciate new thread
            threadHandleMessagesClient.start(); // Start new thread

            // Get messages from server
            printMessageFromServer();
        } catch (IOException e) {
            System.out.println("Can't create new socket! VERY BAD");
        }
    }

    private void printMessageFromServer() {
        while (running) { // While we are running
            try {
                DataInputStream dIn = new DataInputStream(s.getInputStream()); // instanciate new dataInputStream which is linked to the client
                switch (dIn.readByte()) { // Check what type of message we got
                    case 0 -> { // message tells us the server disconnected
                        System.out.println("Server disconnected! Disconnecting myself..."); // print what happened
                        running = false; // Stop everything
                    }
                    case 1 -> System.out.println(dIn.readUTF()); // we got a Normal message
                }
            } catch (IOException e) {
                System.out.println("Can't print message! VERY BAD");
            }
        }

        // If we shouldn't run anymore, end it
        System.out.println("Thread ended Client " + username); // Tell the user that this thread has stopped
        threadHandleMessagesClient.stopWindow(); // Close the window
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