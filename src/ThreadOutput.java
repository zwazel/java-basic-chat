import java.io.IOException;
import java.net.Socket;

public class ThreadOutput implements Runnable {
    Thread threadOutput;
    private String threadName;
    Socket s;

    public ThreadOutput(String threadName, Socket s) {
        this.threadName = threadName;
        this.s = s;
    }

    @Override
    public void run() {
        System.out.println("Thread running" + threadName);

        try {
            System.out.println(Main.getMessageFromNetwork(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("THREAD STARTEEED");
        if (threadOutput == null) {
            threadOutput = new Thread(this, threadName);
            threadOutput.start();
        }
    }
}
