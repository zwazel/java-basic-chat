package Client;

public class ThreadHandleMessagesClient implements Runnable {
    private Thread threadMessageHandlerClient;
    private final String threadName;

    public ThreadHandleMessagesClient(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

        
    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadMessageHandlerClient == null) {
            threadMessageHandlerClient = new Thread(this, threadName);
            threadMessageHandlerClient.start();
        }
    }
}