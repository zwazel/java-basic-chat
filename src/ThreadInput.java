import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadInput extends ServerAndClient implements Runnable {
    private Thread threadInput;
    private final String threadName;
    Socket s;
    String username;
    private DataOutputStream dOut;

    public ThreadInput(String username, String threadName, Socket s) {
        this.threadName = threadName;
        this.s = s;
        this.username = username;
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

        String text = getString("Send message");

        while (!text.equals("/dc")) {
            try {
                sendMessageToNetwork(s, username, text);
            } catch (IOException e) {
                e.printStackTrace();
            }
            text = getString("Send message");
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
        dOut = new DataOutputStream(s.getOutputStream());
        dOut.writeUTF(username+ ": " + message);
        dOut.flush(); // Send off the data
    }
}
