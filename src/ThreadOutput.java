import java.io.IOException;
import java.net.Socket;

public class ThreadOutput extends ServerAndClient implements Runnable {
    private Thread threadOutput;
    private String threadName;
    Socket s;

    public ThreadOutput(String threadName, Socket s) {
        this.threadName = threadName;
        this.s = s;
    }

    @Override
    public void run() {
        System.out.println("Thread running" + threadName);

        while(true) {
            try {
                System.out.println(getMessageFromNetwork(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadOutput == null) {
            threadOutput = new Thread(this, threadName);
            threadOutput.start();
        }
    }
}
