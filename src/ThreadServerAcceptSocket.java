public class ThreadServerAcceptSocket implements Runnable {
    Thread threadInput;
    private String threadName;

    public ThreadServerAcceptSocket() {

    }

    @Override
    public void run() {
        System.out.println("Thread running" + threadName);
    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadInput == null) {
            threadInput = new Thread(this, threadName);
            threadInput.start();
        }
    }
}