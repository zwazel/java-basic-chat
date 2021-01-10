import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadServerHandlerOutput implements Runnable {
    private Thread threadServerHandlerOutput;
    private final String threadName;
    Socket s;
    private DataInputStream dIn;
    private ThreadServerHandlerInput threadServerHandlerInput;

    public ThreadServerHandlerOutput(String threadName, Socket s, ThreadServerHandlerInput threadServerHandlerInput) {
        this.threadName = threadName;
        this.s = s;
        this.threadServerHandlerInput = threadServerHandlerInput;
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

        while(true) {
            try {
                String message = getMessageFromClient(s);
                System.out.println(message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadServerHandlerOutput == null) {
            threadServerHandlerOutput = new Thread(this, threadName);
            threadServerHandlerOutput.start();
        }
    }

    private String getMessageFromClient(Socket s) throws IOException {
        dIn = new DataInputStream(s.getInputStream());
        return dIn.readUTF();
    }
}