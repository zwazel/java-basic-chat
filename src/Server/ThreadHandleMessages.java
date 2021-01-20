package Server;

import javax.swing.*;

public class ThreadHandleMessages extends JFrame implements Runnable {
    private Thread threadHandleMessages;
    private final String threadName;

    public ThreadHandleMessages(String threadName) {
        this.threadName = threadName;

        initInputWindow();
    }

    private void initInputWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(threadName);

        setSize(500,500);
        setVisible(true);
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadHandleMessages == null) {
            threadHandleMessages = new Thread(this, threadName);
            threadHandleMessages.start();
        }
    }
}