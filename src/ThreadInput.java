import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadInput extends ServerAndClient implements Runnable {
    private Thread threadInput;
    private String threadName;
    Socket s;
    String username;

    public ThreadInput(String username, String threadName, Socket s) {
        this.threadName = threadName;
        this.s = s;
        this.username = username;
    }

    @Override
    public void run() {
        System.out.println("Thread running" + threadName);

        String text = getString("Send message to Clients");

        while (!text.equals("/dc")) {
            try {
                sendMessageToNetwork(s, username, text);
            } catch (IOException e) {
                e.printStackTrace();
            }
            text = getString("Send message to clients");
        }
    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadInput == null) {
            threadInput = new Thread(this, threadName);
            threadInput.start();
        }
    }

    private void sendMessageToNetwork(Socket s, String username, String message) throws IOException {
        PrintWriter pr = new PrintWriter(s.getOutputStream()); // Printer
        pr.println(username + ": " + message);
        pr.flush(); // Send off the data
    }
}
