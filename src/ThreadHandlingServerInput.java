import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class ThreadHandlingServerInput extends ServerAndClient implements Runnable {
    private Thread threadHandlingServerOutput;
    private final String threadName;
    private DataOutputStream dOut;

    private HashMap<Integer, ServerClient> clientsHashMap = new HashMap<Integer, ServerClient>();

    public ThreadHandlingServerInput(String threadName, String username) {
        this.threadName = threadName;
        this.username = username;
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);
        try {
            sendMessageToClients();
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

    private void sendMessageToClients() throws IOException {
        String message;
        do {
            message = getString("Send message");

            for (int i : clientsHashMap.keySet()) {
                Socket s = clientsHashMap.get(i).getSocket();

                dOut = new DataOutputStream(s.getOutputStream());
                dOut.writeUTF(username + ": " + message);
                dOut.flush(); // Send off the data
            }
        } while(!message.equals("/dc"));
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