package main;

import client.Client;
import view.MyFXApplication;

public class MainJFXApp implements Runnable {
    private Thread mainJFXApp;
    private final String threadName;
    private Client parent;

    public MainJFXApp(String threadName) {
        this.threadName = threadName;
    }

    public void setParent(Client parent) {
        this.parent = parent;
        System.out.println("Set parent of MainJFXApp");
    }

    @Override
    public void run() {
        System.out.println("THREAD RUNNING " + threadName);

        String[] args = {""};

        MyFXApplication.main(args);
        MyFXApplication.setControllerParent(parent);

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