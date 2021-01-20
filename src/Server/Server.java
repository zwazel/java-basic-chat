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
        } catch (IOException e) {
            System.out.println("Cant create server socket! VERY BAD");
        }

        ThreadHandleMessages threadHandleMessages = new ThreadHandleMessages("threadServerHandlerOutput");
        threadHandleMessages.start();

        acceptConnections();
    }

    private void acceptConnections() {
        while (true) {
            try {
                Socket s = ss.accept();

                // Tell the client what ID he has
                DataOutputStream dOut = new DataOutputStream(s.getOutputStream()); // Create new output stream, linked with the client that just connected
                dOut.writeInt(idCounter++); // increase id then write id
                dOut.flush(); // Send off the data

                // get the username from the client that just connected
                DataInputStream dIn = new DataInputStream(s.getInputStream()); // Create new input stream
                String clientUsername = dIn.readUTF(); // Read text

                addClientToMap(idCounter, clientUsername, s);

                ThreadHandleClients threadHandleClients = new ThreadHandleClients("threadServerHandleClients", s);
                threadHandleClients.start();
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