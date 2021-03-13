package main;

import ServerClient.server.Server;
import view.JavaFXApplication;

import javax.swing.*;

public class MainJFXApp implements Runnable {
    private Thread threadHandleMessagesServer;
    private final String threadName;
    private Server server;
    private JTextField textInput;

    public MainJFXApp(String threadName, Server server) {
        this.threadName = threadName;
        this.server = server;
    }

    public static void main(String[] args) {
        JavaFXApplication.main(args);
    }

    public boolean startJavaFXWindow(String[] args) {
        return true;
    }

    @Override
    public void run() {

    }
}
