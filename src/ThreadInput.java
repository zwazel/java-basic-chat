import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadInput extends ServerAndClient implements Runnable {
    private Thread threadInput;
    private final String threadName;
    String username;
    private DataOutputStream dOut;
    char type;

    public ThreadInput(String username, String threadName, Socket s, char type) {
        this.threadName = threadName;
        this.socket = s;
        this.username = username;
        this.type = type;
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

        String text;

        do {
            text = getString("Send message");
            try {
                sendMessageToServer(socket, username, text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!text.equals("/dc"));
    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadInput == null) {
            threadInput = new Thread(this, threadName);
            threadInput.start();
        }
    }

    private void sendMessageToServer(Socket s, String username, String message) throws IOException {
        dOut = new DataOutputStream(s.getOutputStream());
        dOut.writeUTF(username+ ": " + message);
        dOut.flush(); // Send off the data
    }
}
