package Server;

import java.net.Socket;

public class ThreadHandleClients implements Runnable {
    private Thread threadHandleClients;
    private final String threadName;

    public ThreadHandleClients(String threadName, Socket s) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadHandleClients == null) {
            threadHandleClients = new Thread(this, threadName);
            threadHandleClients.start();
        }
    }
}