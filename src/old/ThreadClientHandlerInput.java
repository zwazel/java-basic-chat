package old;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadClientHandlerInput extends ServerAndClient implements Runnable {
    private Thread threadInput;
    private final String threadName;
    String username;
    char type;
    InputWindow inputWindow;

    public ThreadClientHandlerInput(String username, String threadName, Socket s, char type) {
        this.threadName = threadName;
        this.socket = s;
        this.username = username;
        this.type = type;
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

        //inputWindow = InputWindow.startWindow(this);
    }

    public void getMesageFromInputField(String message) {
        try {
            sendMessageToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() throws IOException {
        System.out.println("Disconnecting...");
        DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
        dOut.writeByte(0);
        dOut.writeUTF(username + " disconnected...");
        dOut.flush(); // Send off the data
    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadInput == null) {
            threadInput = new Thread(this, threadName);
            threadInput.start();
        }
    }

    private void sendMessageToServer(String message) throws IOException {
        System.out.println(username + " (Me): " + message);
        DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
        dOut.writeByte(1);
        dOut.writeUTF(username + ": " + message);
        dOut.flush(); // Send off the data
    }
}