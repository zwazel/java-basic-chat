import java.net.Socket;
import java.util.HashMap;

public class ThreadHandlingServerInput extends ServerAndClient implements Runnable {
    private Thread threadHandlingServerOutput;
    private final String threadName;

    private HashMap<Integer, ServerClient> clientsHashMap = new HashMap<Integer, ServerClient>();

    public ThreadHandlingServerInput(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);
        sendMessageToClients();
    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadHandlingServerOutput == null) {
            threadHandlingServerOutput = new Thread(this, threadName);
            threadHandlingServerOutput.start();
        }
    }

    private void sendMessageToClients() {

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