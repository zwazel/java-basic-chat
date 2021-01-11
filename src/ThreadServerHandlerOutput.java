import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadServerHandlerOutput implements Runnable {
    private Thread threadServerHandlerOutput;
    private final String threadName;
    Socket s;
    private final ThreadServerHandlerInput threadServerHandlerInput;
    private final int id;
    private boolean running = true;

    public ThreadServerHandlerOutput(String threadName, Socket s, ThreadServerHandlerInput threadServerHandlerInput, int id) {
        this.threadName = threadName;
        this.s = s;
        this.threadServerHandlerInput = threadServerHandlerInput;
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

        while(running) {
            try {
                String message = getMessageFromClient();
                if (!message.equals("/SystemUserDisconnected")) {
                    System.out.println(message);
                    threadServerHandlerInput.sendMessageFromClientToClients(id, message);
                } else {
                    //running = false;
                    disconnectSocket();
                    break;
                }
            } catch (IOException e) {
                System.out.println("Can't disconnect Socket " + id + " VERY BAD");
                running = false;
            }
        }
        System.out.println("Thread stopped " + threadName);
    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadServerHandlerOutput == null) {
            threadServerHandlerOutput = new Thread(this, threadName);
            threadServerHandlerOutput.start();
        }
    }

    private String getMessageFromClient() throws IOException {
        DataInputStream dIn = new DataInputStream(s.getInputStream());

        String text = "";
        try {
            text = dIn.readUTF();
        } catch(IOException e) {
            return "/SystemUserDisconnected";
        }

        return text;
    }

    private void disconnectSocket() throws IOException {
        DataInputStream dIn = new DataInputStream(s.getInputStream());
        String disconnectingUsername = dIn.readUTF();

        System.out.println("Lost connection to Socket " + id);

        threadServerHandlerInput.removeSocketFromMap(id);
        System.out.println("Removed socket " + id);

        String message = disconnectingUsername+ " disconnected";
        System.out.println(message);
        threadServerHandlerInput.sendMessageToClients(message);

        running = false;

        s.close();
    }
}