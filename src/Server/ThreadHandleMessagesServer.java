package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ThreadHandleMessagesServer extends JFrame implements Runnable, ActionListener, WindowListener {
    private Thread threadHandleMessagesServer;
    private final String threadName;

    public ThreadHandleMessagesServer(String threadName) {
        this.threadName = threadName;

        initInputWindow();
    }

    private void initInputWindow() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Window " + threadName);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());

        northPanel.add(new JTextField(15));
        add(northPanel, BorderLayout.NORTH);

        JButton sendMessageButton = new JButton("Send Message");
        sendMessageButton.addActionListener(this);
        JPanel centerPanel = new JPanel();
        centerPanel.add(sendMessageButton);

        add(centerPanel, BorderLayout.CENTER);

        this.addWindowListener(this);

        setSize(200,150);
        setVisible(true);
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadHandleMessagesServer == null) {
            threadHandleMessagesServer = new Thread(this, threadName);
            threadHandleMessagesServer.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Send message
        System.out.println("Button pressedl");
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        // TODO: Handle Disconnects
        System.out.println("Closing " + getTitle());
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}