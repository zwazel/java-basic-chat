import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadOutput implements Runnable {
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

    private String getMessageFromNetwork(Socket s) throws IOException {
        InputStreamReader in = new InputStreamReader(s.getInputStream()); // Inputs stuff into buffered reader... or something idk
        BufferedReader bf = new BufferedReader(in); // Reader that reads messages from client/Server, maybe?
        String str = bf.readLine(); // Read message that we got from client/server

        return str;
    }
}
