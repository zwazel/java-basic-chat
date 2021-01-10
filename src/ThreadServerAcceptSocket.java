import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadServerAcceptSocket implements Runnable {
    Thread threadInput;
    private String threadName;
    DataOutputStream dOut;

    public ThreadServerAcceptSocket(String username, ServerSocket ss, int idCounter) throws IOException {
        while(true) {
            Socket s = ss.accept();
            System.out.println("client connected");

            dOut = new DataOutputStream(s.getOutputStream());
            dOut.writeByte(++idCounter);
            dOut.flush(); // Send off the data

            ThreadOutput threadOutput = new ThreadOutput("ThreadOutputServer", s);
            threadOutput.start();
            ThreadInput threadInput = new ThreadInput(username, "ThreadInputServer", s, 's');
            threadInput.start();
        }
    }

    @Override
    public void run() {
        System.out.println("Thread running" + threadName);
    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadInput == null) {
            threadInput = new Thread(this, threadName);
            threadInput.start();
        }
    }
}