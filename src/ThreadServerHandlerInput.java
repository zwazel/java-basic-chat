import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class ThreadServerHandlerInput extends ServerAndClient implements Runnable {
    private Thread threadHandlingServerOutput;
    private final String threadName;
    private DataOutputStream dOut;
    InputWindow inputWindow;

    private HashMap<Integer, ServerClient> clientsHashMap = new HashMap<Integer, ServerClient>();

    public ThreadServerHandlerInput(String threadName, String username) {
        this.threadName = threadName;
        this.username = username;

        inputWindow = new InputWindow();
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

        String message;
        do {
            message = inputWindow.getText();

            try {
                sendMessageToClients(username, message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while(!message.equals("/dc"));
    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadHandlingServerOutput == null) {
            threadHandlingServerOutput = new Thread(this, threadName);
            threadHandlingServerOutput.start();
        }
    }

    private void sendMessageToClients(String username, String message) throws IOException {
        for (int i : clientsHashMap.keySet()) {
            Socket s = clientsHashMap.get(i).getSocket();

            dOut = new DataOutputStream(s.getOutputStream());
            dOut.writeUTF(username + ": " + message);
            dOut.flush(); // Send off the data
        }
    }

    public void sendMessageFromClientToClients(int id, String message) throws IOException {
        //String username = clientsHashMap.get(id).getUsername();

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