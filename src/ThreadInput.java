import java.io.IOException;
import java.net.Socket;

public class ThreadInput implements Runnable {
    Thread threadInput;
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

        String text = Main.getString("Send message to Clients");

        while (!text.equals("/dc")) {
            try {
                Main.sendMessageToNetwork(s, username, text);
            } catch (IOException e) {
                e.printStackTrace();
            }
            text = Main.getString("Send message to clients");
        }
    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadInput == null) {
            threadInput = new Thread(this, threadName);
            threadInput.start();
        }
    }
}
