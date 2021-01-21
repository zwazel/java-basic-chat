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
    private Server server;
    private JTextField textInput;

    public ThreadHandleMessagesServer(String threadName, Server server) {
        this.threadName = threadName;
        this.server = server;
    }

    private void initInputWindow() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Window " + threadName);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());

        textInput = new JTextField(15);
        northPanel.add(textInput);
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

        initInputWindow();

        System.out.println("Thread ended " + threadName);
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
        server.sendMessageToClients(textInput.getText());
        textInput.setText("");
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