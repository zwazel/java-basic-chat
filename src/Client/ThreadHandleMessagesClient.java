package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadHandleMessagesClient extends JFrame implements Runnable, ActionListener, WindowListener {
    private Thread threadMessageHandlerClient;
    private final String threadName;
    private String username;
    private Socket serverSocket;
    private JTextField textInput;
    private int myId;

    public ThreadHandleMessagesClient(String threadName, String username, int myId, Socket serverSocket) {
        this.threadName = threadName + " " + myId;
        this.username = username;
        this.myId = myId;
        this.serverSocket = serverSocket;
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

    private void sendMessageToServer(String message) {
        System.out.println(username + " (Me): " + message);
        try {
            DataOutputStream dOut = new DataOutputStream(serverSocket.getOutputStream());
            dOut.writeByte(1); // Declare type of message
            dOut.writeInt(myId); // Send id
            dOut.writeUTF(username + ": " + message);
            dOut.flush(); // Send off the data
        } catch (IOException e) {
            System.out.println("Can't send message in thread " + threadName + ", VERY BAD");
        }
    }

    private void disconnect() {
        try {
            DataOutputStream dOut = new DataOutputStream(serverSocket.getOutputStream());
            dOut.writeByte(0); // Declare type of message
            dOut.writeInt(myId); // Send id
            dOut.flush(); // Send off the data
        } catch (IOException e) {
            System.out.println("Can't disconnect from Server in thread " + threadName + ", VERY BAD");
        }
    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadMessageHandlerClient == null) {
            threadMessageHandlerClient = new Thread(this, threadName);
            threadMessageHandlerClient.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        sendMessageToServer(textInput.getText());
        textInput.setText("");
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        // TODO: Handle Disconnects
        System.out.println("Closing " + getTitle());
        disconnect();
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