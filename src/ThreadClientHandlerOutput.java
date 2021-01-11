import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadClientHandlerOutput implements Runnable {
    private Thread threadOutput;
    private final String threadName;
    Socket s;
    private boolean running = true;

    public ThreadClientHandlerOutput(String threadName, Socket s) {
        this.threadName = threadName;
        this.s = s;
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

        while(running) {
            try {
                System.out.println(getMessageFromServer(s));
            } catch (IOException e) {
                System.out.println("Lost connection to server");
                running = false;
            }
        }

        //s.close();
        //System.out.println("Closed socket");
        System.out.println("Thread stopped " + threadName);
    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadOutput == null) {
            threadOutput = new Thread(this, threadName);
            threadOutput.start();
        }
    }

    private String getMessageFromServer(Socket s) throws IOException {
        DataInputStream dIn = new DataInputStream(s.getInputStream());
        return dIn.readUTF();
    }
}
