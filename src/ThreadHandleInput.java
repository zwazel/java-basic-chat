import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class ThreadHandleInput implements Runnable {
    private DataOutputStream dOut;
    private final HashMap<Integer, ServerClient> clientsHashMap = new HashMap<>();

    private Thread threadHandleInput;
    private final String threadName;

    Socket serverSocket;
    String username;

    //*************************************************************
    // Server
    //*************************************************************
    public ThreadHandleInput(String threadName, String username) {
        this.threadName = threadName;
        this.username = username;

        // TODO: GUI
        //inputWindow = InputWindowServer.startWindow(this);
    }

    public void sendMessageToClients(String message) throws IOException {
        System.out.println(username + " (Me): " + message);

        for (int i : clientsHashMap.keySet()) {
            Socket s = clientsHashMap.get(i).getSocket();

            dOut = new DataOutputStream(s.getOutputStream());
            dOut.writeUTF(username + ": " + message);
            dOut.flush(); // Send off the data
        }
    }

    public void sendMessageFromClientToClients(int id, String message) throws IOException {
        //String username = clientsHashMap.get(id).getUsername(); // We already have the username inside of the message, so this is not needed

        for (int i : clientsHashMap.keySet()) {
            if (i != id) { // Don't send message to myself
                Socket s = clientsHashMap.get(i).getSocket();

                dOut = new DataOutputStream(s.getOutputStream());
                dOut.writeUTF(message);
                dOut.flush(); // Send off the data
            }
        }
    }

    //*************************************************************
    // Client
    //*************************************************************
    public ThreadHandleInput(String threadName, String username, Socket s) {
        this.threadName = threadName;
        this.serverSocket = s;
        this.username = username;

        // TODO: GUI
        //inputWindow = InputWindowClient.startWindow(this);
    }

    private void sendMessageToServer(String message) throws IOException {
        System.out.println(username + " (Me): " + message);
        DataOutputStream dOut = new DataOutputStream(serverSocket.getOutputStream());
        dOut.writeByte(1);
        dOut.writeUTF(username + ": " + message);
        dOut.flush(); // Send off the data
    }

    //*************************************************************
    // Both
    //*************************************************************
    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadHandleInput == null) {
            threadHandleInput = new Thread(this, threadName);
            threadHandleInput.start();
        }
    }

    // TODO: Make it so that both client and server have a hashmap, and both can use "listallconnectedclients"
    public void addSocketToMap(Socket s, String clientUsername, int idCounter) {
        ServerClient client = new ServerClient(s, clientUsername, idCounter);
        clientsHashMap.put(idCounter, client);
    }

    public void listAllConnectedClients() {
        for (int i : clientsHashMap.keySet()) {
            clientsHashMap.get(i).print();
        }
    }
}