package Client;

import GlobalStuff.MessageTypes;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadHandleMessagesClient extends JFrame implements Runnable, ActionListener, WindowListener {
    private Thread threadMessageHandlerClient;
    private final String threadName;
    private String username;
    private Socket serverSocket;
    private JTextField textInput;
    private int myId;


    private JTextPane textPane;
    private JScrollPane scrollPane;
    Client client;

    public ThreadHandleMessagesClient(String threadName, String username, int myId, Socket serverSocket, Client client) {
        this.threadName = threadName;
        this.username = username;
        this.myId = myId;
        this.serverSocket = serverSocket;
        this.client = client;
    }

    public JTextPane getTextPane() {
        return textPane;
    }

    private void initInputWindow() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Chatroom von " + username);
        JPanel interactionPanel = new JPanel();
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());
        JPanel userpanel = new JPanel();
        userpanel.setLayout(new GridLayout(0, 1));
        textInput = new JTextField(15);
        northPanel.add(textInput);
        interactionPanel.add(northPanel, BorderLayout.NORTH);
        ArrayList<JLabel> userlabels = new ArrayList<>();

        textPane = new JTextPane();
        textPane.setBackground(Color.BLACK);
        textPane.setForeground(Color.LIGHT_GRAY);
        textPane.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14)); //MONOSPACED
        textPane.setEditable(false);
        scrollPane = new JScrollPane(textPane);
        DefaultCaret caret = (DefaultCaret) textPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JButton sendMessageButton = new JButton("Send Message"); // instanciate new button
        sendMessageButton.addActionListener(this); // Add actionlistener to the button
        JPanel centerPanel = new JPanel();
        centerPanel.add(sendMessageButton); // add the button to the panel

        userpanel.setPreferredSize(new Dimension(150, 400));
        JLabel usertitle = new JLabel("Alle Clients:  ");
        userpanel.add(usertitle, BorderLayout.NORTH);
        userlabels.add(new JLabel(username + " ID: " + client.getMyId()));

        for (JLabel jl : userlabels) {
            userpanel.add(jl);
        }
        interactionPanel.add(centerPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.CENTER);
        add(interactionPanel, BorderLayout.SOUTH);
        add(userpanel, BorderLayout.EAST);
        addWindowListener(this); // Add a window listener to the window with which we check if the window is closing or not

        setSize(900, 400); // Set the size
        setVisible(true); // Make the window visible
        append("The start of an epic discussion!\n" +
                "*************************************\n", textPane, Color.WHITE);
    }

    public void append(String s, JTextPane pane, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        try {
            Document doc = pane.getDocument();
            doc.insertString(doc.getLength(), s, aset);
        } catch (BadLocationException exc) {
            exc.printStackTrace();
        }
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
        try {
            DataOutputStream dOut = new DataOutputStream(serverSocket.getOutputStream());
            dOut.writeByte(1); // Declare type of message (1 = normal message)
            dOut.writeUTF(message);
            dOut.flush(); // Send off the data
        } catch (IOException e) {
            System.out.println("Can't send message in thread " + threadName + ", VERY BAD");
        }
    }

    private void sendCommandToServer(boolean isOp, String command, String[] args) {
        try {
            DataOutputStream dOut = new DataOutputStream(serverSocket.getOutputStream());
            dOut.writeByte(MessageTypes.CLIENT_COMMAND.getValue()); // Declare type of message (2 = command)
            dOut.writeBoolean(isOp);
            dOut.writeUTF(command);
            dOut.writeInt(args.length); // Tell the receiver how many arguments there are
            for (int i = 0; i < args.length; i++) {
                dOut.writeUTF(args[i]);
            }
            dOut.flush(); // Send off the data
        } catch (IOException e) {
            System.out.println("Can't send command in thread " + threadName + ", VERY BAD");
        }
    }

    private void disconnect() {
        try {
            DataOutputStream dOut = new DataOutputStream(serverSocket.getOutputStream()); // instanciate new data output stream
            dOut.writeByte(MessageTypes.DISCONNECT.getValue()); // Declare type of message (0 = disconnect)
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
        if (text.startsWith("/")) {
            text = text.toLowerCase();
            text = text.substring(1);
            String[] commandParts = text.split(" ");
            String command = commandParts[0];

            String[] args = new String[commandParts.length - 1];
            // Copy the elements of the commandParts array from index 1 into args from index 0
            if (args.length > 0) {
                System.arraycopy(commandParts, 1, args, 0, commandParts.length - 1);
            }
            sendCommandToServer(client.operator, command, args);
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