import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadServerHandlerOutput implements Runnable {
    private Thread threadServerHandlerOutput;
    private final String threadName;
    Socket s;
    private final ThreadHandleInput threadHandleInput;
    private final int id;

    public ThreadServerHandlerOutput(String threadName, Socket s, ThreadHandleInput threadHandleInput, int id) {
        this.threadName = threadName;
        this.s = s;
        this.threadHandleInput = threadHandleInput;
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

        while(true) {
            try {
                String message = getMessageFromClient(s);
                System.out.println(message);
                threadHandleInput.sendMessageToClients(message);
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
        DataInputStream dIn = new DataInputStream(s.getInputStream());

        switch(dIn.readByte()) {
            case 0: // Disconnect
                break;

            case 1: // Normal message
                break;
        }

        return dIn.readUTF();
    }
}