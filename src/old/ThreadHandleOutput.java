package old;

public class ThreadHandleOutput implements Runnable {
    private Thread threadHandleOutput;
    private final String threadName;

    public ThreadHandleOutput(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadHandleOutput == null) {
            threadHandleOutput = new Thread(this, threadName);
            threadHandleOutput.start();
        }
    }
}