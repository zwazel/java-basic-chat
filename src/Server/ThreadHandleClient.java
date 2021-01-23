package Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadHandleClient implements Runnable {
    private Thread threadHandleClients;
    private final String threadName;
    private Server server;
    private Socket socket;
    private boolean running = true;

    public ThreadHandleClient(String threadName, Server server, Socket s) {
        this.threadName = threadName;
        this.server = server;
        this.socket = s;
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

        getMessageFromClient(socket);

        System.out.println("Thread ended " + threadName);
    }

    private void getMessageFromClient(Socket s) {
        while(running) {
            try {
                DataInputStream dIn = new DataInputStream(s.getInputStream());

                switch (dIn.readByte()) {
                    case 0: // Disconnect
                        server.clientIsDisconnecting(dIn.readInt());
                        running = false;
                        break;

                    case 1: // Normal message
                        server.sendMessageFromClientToClients(dIn.readInt(), dIn.readUTF());
                        break;
                }
            } catch (IOException e) {
                System.out.println("Can't get message from client in thread " + threadName);
            }
        }
    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadHandleClients == null) {
            threadHandleClients = new Thread(this, threadName);
            threadHandleClients.start();
        }
    }
}