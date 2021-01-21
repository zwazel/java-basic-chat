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
        sendMessageToClients("Disconnecting client " + clientMap.get(clientId).getUsername());
        clientMap.remove(clientId);
    }

    private void acceptConnections() {
        while (true) {
            try {
                Socket s = ss.accept();

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

                idCounter++;
            } catch (IOException e) {
                System.out.println("Can't accept socket connection! VERY BAD");
            }
        }
    }

    public void sendMessageToClients(String message) {
        System.out.println(username + " (Me): " + message);

        for (int i : clientMap.keySet()) {
            Socket s = clientMap.get(i).getSocket();

            try {
                DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
                dOut.writeUTF(username + ": " + message);
                dOut.flush(); // Send off the data
            } catch (IOException e) {
                System.out.println("Can't send message from Server to clients! VERY BAD");
            }
        }
    }

    public void sendMessageFromClientToClients(int id, String message) {
        //String username = clientsHashMap.get(id).getUsername(); // We already have the username inside of the message, so this is not needed

        System.out.println(message);
        for (int i : clientMap.keySet()) {
            if (i != id) { // Don't send message to myself
                Socket s = clientMap.get(i).getSocket();

                try {
                    DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
                    dOut.writeUTF(message);
                    dOut.flush(); // Send off the data
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getPublicIp() {
        //Instantiating the URL class
        URL url = null;
        String result = null;
        try {
            url = new URL("http://ipv4bot.whatismyipaddress.com/");

            //Retrieving the contents of the specified page
            Scanner sc = null;
            try {
                sc = new Scanner(url.openStream());

                //Instantiating the StringBuffer class to hold the result
                StringBuffer sb = new StringBuffer();
                while(sc.hasNext()) {
                    sb.append(sc.next());
                    //System.out.println(sc.next());
                }
                //Retrieving the String from the String Buffer object
                result = sb.toString();
                result = result.replaceAll("<[^>]*>", "");
            } catch (IOException e) {
                System.out.println("Can't create openStream Scanner! VERY BAD");
            }
        } catch (MalformedURLException e) {
            System.out.println("Can't connect to URL! VERY BAD");
        }

        return result;
    }

    private void addClientToMap(int id, String username, Socket s) {
        clientMap.put(id, new ServerClient(id, username, s));
    }

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