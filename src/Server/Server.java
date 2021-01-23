package Server;

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
    String username = "Server";
    private Scanner scanner;
    int port;
    int idCounter = 0;
    int myId = idCounter++;
    ServerSocket ss;
    HashMap<Integer, ServerClient> clientMap = new HashMap<>();
    private boolean running = true;

    public Server() {
        scanner = new Scanner(System.in);

        port = getInt("The Port you are hosting on");
        try {
            ss = new ServerSocket(port);

            // Get IP (Not needed, but used for the user to easily copy and send to others)
            System.out.println("Getting IP adress...");
            System.out.println("My IP adress: " + getPublicIp());

            // Start thread
            ThreadHandleMessagesServer threadHandleMessagesServer = new ThreadHandleMessagesServer("threadServerHandlerOutput", this);
            threadHandleMessagesServer.start();

            // Accept incoming connections
            acceptConnections();
        } catch (IOException e) {
            System.out.println("Cant create server socket! VERY BAD");
        }
    }

    public void disconnectClient(int clientId) {
        sendMessageToClients(clientMap.get(clientId).getUsername() + " disconnected");
        clientMap.remove(clientId);
    }

    public void disconnectAllClients() {
        System.out.println(username + " (Me): Disconnecting...");

        for (int i : clientMap.keySet()) {
            Socket s = clientMap.get(i).getSocket();

            try {
                DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
                dOut.writeByte(0);
                dOut.flush(); // Send off the data
            } catch (IOException e) {
                // TODO: Tell on what client we failed
                System.out.println("Can't send call for disconnecting to client! VERY BAD");
            }
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
                String clientUsername = dIn.readUTF(); // Read text

                sendMessageToClients("Client " + clientUsername + " connected with ID " + idCounter);

                addClientToMap(idCounter, clientUsername, s);

                ThreadHandleClient threadHandleClient = new ThreadHandleClient("threadServerHandleClients", this, s);
                threadHandleClient.start();

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

            for (int i : clientMap.keySet()) { // go through all the currently connected clients
                Socket s = clientMap.get(i).getSocket(); // Get the socket of the current Client
                message = username + ": " + message; // set the message so it's displaying correctly for the clients
                sendMessage(message,s); // Send the message to the specified socket
            }
        }
    }

    // Send a message from a client to all the other clients. Don't send the message to the client himself
    public void sendMessageFromClientToClients(int id, String message) { // We need the ID because we need to know who sent the message
        //String username = clientsHashMap.get(id).getUsername(); // We already have the username inside of the message, so this is not needed

        if(running) { // Only while the server is running (this is to avoid errors as I haven't found another workaround)
            System.out.println(message); // print the message for myself
            for (int i : clientMap.keySet()) { // go through all the currently connected clients
                if (i != id) { // Don't send message to the client who sent the message
                    Socket s = clientMap.get(i).getSocket(); // Get the socket from the current client from the hashmap, so we know where to send the message to
                    sendMessage(message, s); // Send the message to the socket
                }
            }
        }
    }

    // Here we send a normal message to a specified socket
    private void sendMessage(String message, Socket s) { // Get the message and the socket
        try {
            DataOutputStream dOut = new DataOutputStream(s.getOutputStream()); // create new dataOutputStream where we'll put our message in
            dOut.writeByte(1); // tell the client what type of message he's receiving (1 = default message) by writing a byte in the stream
            dOut.writeUTF(message); // Put the message in the stream
            dOut.flush(); // Send off the data
        } catch (IOException e) { // catch error
            // TODO: Tell on what client we failed
            System.out.println("Can't send message from Server to clients! VERY BAD");
        }
    }

    private String getPublicIp() {
        String result = null;
        try {
            // Set the url from where we'll be getting the data from
            URL url = new URL("http://ipv4bot.whatismyipaddress.com/"); // Nice and fast website for getting your ipv4

            //Retrieving the contents of the specified page
            try {
                Scanner sc = new Scanner(url.openStream());

                //Instantiating the StringBuffer class to hold the result
                StringBuffer sb = new StringBuffer();
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

        return Integer.parseInt(scanner.nextLine());
    }

    public static void main(String[] args) {
        new Server();
    }
}