import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadServerAcceptSocket implements Runnable {
    Thread threadServerAcceptSocket;
    private String threadName;
    DataOutputStream dOut;

    ServerSocket ss;
    String username;
    int idCounter;

    public ThreadServerAcceptSocket(String username, String threadName, ServerSocket ss, int idCounter) {
        this.threadName = threadName;
        this.username = username;
        this.ss = ss;
        this.idCounter = idCounter;
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

        try {
            acceptConnections();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptConnections() throws IOException {
        while(true) {
            Socket s = ss.accept();
            System.out.println("client connected");

            dOut = new DataOutputStream(s.getOutputStream());
            dOut.writeInt(++idCounter);
            dOut.flush(); // Send off the data

            ThreadOutput threadOutput = new ThreadOutput("ThreadOutputServer", s);
            threadOutput.start();
            ThreadInput threadInput = new ThreadInput(username, "ThreadInputServer", s, 's');
            threadInput.start();
        }
    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadServerAcceptSocket == null) {
            threadServerAcceptSocket = new Thread(this, threadName);
            threadServerAcceptSocket.start();
        }
    }
}