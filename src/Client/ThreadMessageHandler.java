package Client;

public class ThreadMessageHandler implements Runnable {
    private Thread threadMessageHandler;
    private final String threadName;

    public ThreadMessageHandler(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadMessageHandler == null) {
            threadMessageHandler = new Thread(this, threadName);
            threadMessageHandler.start();
        }
    }
}