package old;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class ThreadServerHandlerInput extends ServerAndClient implements Runnable {
    private Thread threadHandlingServerOutput;
    private final String threadName;
    private DataOutputStream dOut;
    InputWindowServer inputWindow;

    private final HashMap<Integer, ServerClient> clientsHashMap = new HashMap<>();

    public ThreadServerHandlerInput(String threadName, String username) {
        this.threadName = threadName;
        this.username = username;
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);
        inputWindow = InputWindowServer.startWindow(this);
    }

    public void getMessageFromInputField(String message) {
        try {
            sendMessageToClients(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadHandlingServerOutput == null) {
            threadHandlingServerOutput = new Thread(this, threadName);
            threadHandlingServerOutput.start();
        }
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

    public void listAllConnectedClients() {
        for (int i : clientsHashMap.keySet()) {
            clientsHashMap.get(i).print();
        }
    }

    public void addSocketToMap(Socket s, String clientUsername, int idCounter) {
        ServerClient client = new ServerClient(s, clientUsername, idCounter);
        clientsHashMap.put(idCounter, client);
    }
}