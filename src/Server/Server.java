package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class Server {
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

            // TODO: print my own IP Adress out, for easy copy: http://ipv4bot.whatismyipaddress.com/
        } catch (IOException e) {
            System.out.println("Cant create server socket! VERY BAD");
        }

        ThreadHandleMessagesServer threadHandleMessagesServer = new ThreadHandleMessagesServer("threadServerHandlerOutput");
        threadHandleMessagesServer.start();

        acceptConnections();
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

                //TODO: Send message to all clients
                System.out.println("Client " + clientUsername + " connected with ID " + idCounter);

                addClientToMap(idCounter, clientUsername, s);

                ThreadHandleClients threadHandleClients = new ThreadHandleClients("threadServerHandleClients", s);
                threadHandleClients.start();

                //TODO: remove sout
                System.out.println("Increasing ID by 1, Next ID: " + (idCounter+1));
                idCounter++;
            } catch (IOException e) {
                System.out.println("Can't accept socket connection! VERY BAD");
            }
        }
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