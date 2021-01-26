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
        this.threadName = threadName;
        this.username = username;
        this.myId = myId;
        this.serverSocket = serverSocket;
    }

    private void initInputWindow() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(threadName);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());

        textInput = new JTextField(15);
        northPanel.add(textInput);
        add(northPanel, BorderLayout.NORTH);

        JButton sendMessageButton = new JButton("Send Message"); // instanciate new button
        sendMessageButton.addActionListener(this); // Add actionlistener to the button
        JPanel centerPanel = new JPanel();
        centerPanel.add(sendMessageButton); // add the button to the panel

        add(centerPanel, BorderLayout.CENTER);

        addWindowListener(this); // Add a window listener to the window with which we check if the window is closing or not

        setSize(300,200); // Set the size
        setVisible(true); // Make the window visible
    }

    public void stopWindow() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

        initInputWindow(); // Start the new input window
    }

    private void sendMessageToServer(String message) {
        System.out.println(username + " (Me): " + message);
        try {
            DataOutputStream dOut = new DataOutputStream(serverSocket.getOutputStream());
            dOut.writeByte(1); // Declare type of message (1 = normal message)
            dOut.writeUTF(username + ": " + message);
            dOut.flush(); // Send off the data
        } catch (IOException e) {
            System.out.println("Can't send message in thread " + threadName + ", VERY BAD");
        }
    }

    private void sendCommandToServer(String command) {
        System.out.println(username + " (Me): " + command);
        try {
            DataOutputStream dOut = new DataOutputStream(serverSocket.getOutputStream());
            dOut.writeByte(2); // Declare type of message (2 = command)
            dOut.writeUTF(command);
            dOut.flush(); // Send off the data
        } catch (IOException e) {
            System.out.println("Can't send command in thread " + threadName + ", VERY BAD");
        }
    }

    private void disconnect() {
        try {
            DataOutputStream dOut = new DataOutputStream(serverSocket.getOutputStream()); // instanciate new data output stream
            dOut.writeByte(0); // Declare type of message (0 = disconnect)
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
        String text = textInput.getText();
        if(text.startsWith("/")) {
            text = text.substring(1);
            sendCommandToServer(text);
        } else {
            sendMessageToServer(text); // Get the text inside of the input field and send it to all the connected clients
        }
        textInput.setText(""); // Reset the input field
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Closing " + getTitle()); // Tell the user that the window is closing
        disconnect(); // disconnect from the server
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