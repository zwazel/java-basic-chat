package Server;

import ChatCommands.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class Server {
    private static Server server;
    private String username = "Server"; // Our username
    private Scanner scanner;
    private final int port; // our free port
    private int idCounter = 0; // id counter
    private final int myId = idCounter++; // our id, and increase the idocunter by one
    private ServerSocket ss; // our socket
    public HashMap<Integer, ServerClient> clientMap = new HashMap<>(); // hashmap where we'll safe a reference for each connected client
    private HashMap<String, AbstractCommand> commandList = new HashMap<>();
    private boolean running = true; // are we running right now?

    public Server() {
        scanner = new Scanner(System.in); // instantiate a scanner
        server = this;

        port = getInt("The Port you are hosting on"); // Get the open port
        try {
            ss = new ServerSocket(port); // Create a new server socket

            initCommands();

            // Get IP (Not needed, but used for the user to easily copy and send to others)
            System.out.println("Getting IP adress...");
            System.out.println("My IP adress: " + getPublicIp());

            // Start new thread that will handle all the messages the server will send
            ThreadHandleMessagesServer threadHandleMessagesServer = new ThreadHandleMessagesServer("threadServerHandlerOutput", this); // instanciate new thread
            threadHandleMessagesServer.start(); // Start new thread

            // Accept incoming connections
            acceptConnections();
        } catch (IOException e) { // Catch error if we can't create a server socket
            System.out.println("Cant create server socket! VERY BAD");
        }
    }

    public boolean checkIfClientExists(int clientId) {
        if(clientMap.containsKey(clientId)) {
            return true;
        }

        return false;
    }

    private void initCommands() {
        commandList.put("lc", new ListAllConnectedClientsCommandHandler());
        commandList.put("op", new SetOperatorCommandHandler());
        commandList.put("kick", new KickClientCommandHandler());
    }

    public static Server get() {
        return server;
    }

    // a single client is disconnecting on its own
    public void clientIsDisconnecting(int clientId) {
        String disconnectedClientUsername = clientMap.get(clientId).getUsername();
        clientMap.remove(clientId); // remove the client from the hashmap
        sendMessageToClients("Client \"" + disconnectedClientUsername + "\" with id: " + clientId + " disconnected"); // Tell all the currently connected clients who just disconnected
    }

    public void sendMessageTypeToClient(int clientId, byte messageType) {
        Socket s = clientMap.get(clientId).getSocket(); // Get the socket of the current client

        try {
            DataOutputStream dOut = new DataOutputStream(s.getOutputStream()); // Create new output stream
            dOut.writeByte(messageType);
            dOut.flush(); // Send off the data
        } catch (IOException e) { // Catch error
            System.out.println("Can't send messagetype " + messageType + "from server to client \"" + clientId + "\"! VERY BAD");
        }
    }

    public void sendMessageTypeToClient(int clientId, byte messageType, String message) {
        Socket s = clientMap.get(clientId).getSocket(); // Get the socket of the current client

        try {
            DataOutputStream dOut = new DataOutputStream(s.getOutputStream()); // Create new output stream
            dOut.writeByte(messageType);
            dOut.writeUTF(message);
            dOut.flush(); // Send off the data
        } catch (IOException e) { // Catch error
            System.out.println("Can't send messagetype " + messageType + "from server to client \"" + clientId + "\"! VERY BAD");
        }
    }

    public void sendMessageTypeToAllClients(byte messageType) {
        for (int i : clientMap.keySet()) { // Go through all the clients
            sendMessageTypeToClient(i, messageType);
        }
    }

    public void sendMessageTypeToAllClients(byte messageType, String message) {
        for (int i : clientMap.keySet()) { // Go through all the clients
            sendMessageTypeToClient(i, messageType, message);
        }
    }

    private void acceptConnections() {
        while (running) { // Only while the server is running (this is to avoid errors as I haven't found another workaround)
            try {
                Socket s = ss.accept(); // Wait for a request from a client

                // Tell the client what ID he has
                DataOutputStream dOut = new DataOutputStream(s.getOutputStream()); // Create new output stream, linked with the client that just connected
                dOut.writeInt(idCounter); // increase id then write id
                dOut.flush(); // Send off the data

                // get the username from the client that just connected
                DataInputStream dIn = new DataInputStream(s.getInputStream()); // Create new input stream
                String clientUsername = dIn.readUTF(); // Read text and save it

                sendMessageToClients("Client \"" + clientUsername + "\" connected with ID " + idCounter); // Tell the other clients that someone new just connected

                addClientToMap(idCounter, clientUsername, s); // Add the new client to the hashmap (after telling everyone that he joined, so that he's not getting the message)

                ThreadHandleClient threadHandleClient = new ThreadHandleClient("HandleClient " + clientUsername + " " + idCounter, this, s, idCounter); // instanciate a new Thread which will handle this specific client
                threadHandleClient.start(); // start the new thread

                idCounter++; // Increase the ID counter, to make sure that nobody gets the same ID
            } catch (IOException e) { // catch error
                System.out.println("Can't accept socket connection! VERY BAD");
            }
        }
    }

    // Send a message from me, the server, to all the other clients.
    public void sendMessageToClients(String message) { // We need the message we want to send
        if(running) { // Only while the server is running (this is to avoid errors as I haven't found another workaround)
            System.out.println(username + " (Me): " + message); // Print the message for myself
            message = username + ": " + message; // set the message so it's displaying correctly for the clients

            for (int i : clientMap.keySet()) { // go through all the currently connected clients
                sendMessage(message, i); // Send the message to the specified socket
            }
        }
    }

    // Send a message from a client to all the other clients. Don't send the message to the client himself
    public void sendMessageFromClientToClients(int id, String message) { // We need the ID because we need to know who sent the message
        //String username = clientsHashMap.get(id).getUsername(); // We already have the username inside of the message, so this is not needed

        if(running) { // Only while the server is running (this is to avoid errors as I haven't found another workaround)
            System.out.println(message); // print the message for myself
            for (int i : clientMap.keySet()) { // go through all the currently connected clients
                if (i != id) { // Don't send message back to the client who sent us the message
                    sendMessage(message, i); // Send the message to the socket
                }
            }
        }
    }

    // Here we send a normal message to a specified socket
    public void sendMessage(String message, int clientId) { // Get the message and the socket
        try {
            Socket clientSocket = clientMap.get(clientId).getSocket();
            DataOutputStream dOut = new DataOutputStream(clientSocket.getOutputStream()); // create new dataOutputStream where we'll put our message in
            dOut.writeByte(1); // tell the client what type of message he's receiving (1 = default message) by writing a byte in the stream
            dOut.writeUTF(message); // Put the message in the stream
            dOut.flush(); // Send off the data
        } catch (IOException e) { // catch error
            System.out.println("Can't send message from Server to client \"" + clientMap.get(clientId).getUsername() + "\" with ID: " + clientId + "! VERY BAD");
        }
    }

    public boolean handleCommandsClient(boolean isOp, String command, String[] args, int senderId) {
        if(commandList.containsKey(command)) {
            commandList.get(command).clientExecute(isOp, args, senderId);
            return true;
        }
        return false;
    }

    public boolean handleCommandsServer(String command, String[] args) {
        if(commandList.containsKey(command)) {
            commandList.get(command).serverExecute(args);
            return true;
        }
        return false;
    }

    // Get the public ip of the server, for easily sharing with others
    private String getPublicIp() {
        String result = null;
        try {
            // Set the url from where we'll be getting the data from
            URL url = new URL("http://ipv4bot.whatismyipaddress.com/"); // Nice and fast website for getting your ipv4

            //Retrieving the contents of the specified page
            try {
                Scanner sc = new Scanner(url.openStream());

                //Instantiating the StringBuffer class to hold the result
                StringBuilder sb = new StringBuilder();
                while(sc.hasNext()) {
                    sb.append(sc.next());
                }
                //Retrieving the String from the String Buffer object
                result = sb.toString();
                result = result.replaceAll("<[^>]*>", ""); // Remove the html tags with some fancy black magic
            // Catch error if we can't create an open stream to the provided url
            } catch (IOException e) {
                System.out.println("Can't create openStream Scanner! VERY BAD");
            }
        // Catch error if we can't connect to the website
        } catch (MalformedURLException e) {
            System.out.println("Can't connect to URL! VERY BAD");
        }

        // return the ip, or null if we can't find an IP
        return result;
    }

    // Add a new client to the HashMap
    private void addClientToMap(int id, String username, Socket s) { // Get the id, username and the socket
        clientMap.put(id, new ServerClient(id, username, s)); // Create a new ServerClient Instance and safe it in the HashMap
    }

    // Method for getting an Integer
    private int getInt(String command) {
        if (!command.equals("-1")) {
            System.out.print(command + " > ");
        }

        // TODO: Catch error if not number
        // Get the number as a String, and convert it to an Integer. by doing so, we won't have a problem with the scanner "skipping a line"!
        return Integer.parseInt(scanner.nextLine());
    }

    public static void main(String[] args) {
        new Server();
    }
}