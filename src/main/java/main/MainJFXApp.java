package main;

import view.JavaFXApplication;

public class MainJFXApp implements Runnable {
    private Thread mainJFXApp;
    private final String threadName;

    public MainJFXApp(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        System.out.println("THREAD RUNNING " + threadName);

        String[] args = {""};

        JavaFXApplication.main(args);

        System.out.println("THREAD ENDED " + threadName);
    }

    public void start() {
        System.out.println("THREAD STARTED " + threadName);
        if (mainJFXApp == null) {
            mainJFXApp = new Thread(this, threadName);
            mainJFXApp.start();
        }
    }
}