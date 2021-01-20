package Server;

import old.InputWindowClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThreadHandleMessages extends JFrame implements Runnable, ActionListener {
    private Thread threadHandleMessages;
    private final String threadName;

    public ThreadHandleMessages(String threadName) {
        this.threadName = threadName;

        initInputWindow();
    }

    private void initInputWindow() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(threadName);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());

        northPanel.add(new JTextField(15));
        add(northPanel, BorderLayout.NORTH);

        JButton sendMessageButton = new JButton("Send Message");
        sendMessageButton.addActionListener(this);
        JPanel centerPanel = new JPanel();
        centerPanel.add(sendMessageButton);

        add(centerPanel, BorderLayout.CENTER);

        setSize(200,150);
        setVisible(true);
    }

    @Override
    public void run() {
        System.out.println("Thread running " + threadName);

    }

    public void start() {
        System.out.println("THREAD " + threadName + " STARTED");
        if (threadHandleMessages == null) {
            threadHandleMessages = new Thread(this, threadName);
            threadHandleMessages.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Send message
        System.out.println("Button pressedl");
    }
}