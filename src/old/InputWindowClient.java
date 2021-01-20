package old;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputWindowClient extends JPanel {
    static ThreadClientHandlerInput threadClientHandlerInput;
    private static JTextField textField;
    static InputWindow inputWindow;

    public InputWindowClient() {
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());
        /*JLabel label = new JLabel("Send message ");
        northPanel.add(label);*/

        textField = new JTextField(15);
        northPanel.add(textField);
        add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        JButton btn = new JButton("Send message");
        btn.addActionListener(new BtnListener());
        centerPanel.add(btn);

        add(centerPanel, BorderLayout.CENTER);
    }

    public static InputWindow startWindow(ThreadClientHandlerInput _threadClientHandlerInput) {
        threadClientHandlerInput = _threadClientHandlerInput;

        JFrame frame = new JFrame(threadClientHandlerInput.username + " InputWindowClient");
        inputWindow = new InputWindow();
        frame.add(inputWindow);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        WindowListener listener = new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                Frame frame = (Frame) evt.getSource();
                System.out.println("Closing = " + frame.getTitle());
            }
        };
        frame.addWindowListener(listener);

        frame.setVisible(true);
        return inputWindow;
    }

    private static class BtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String content = textField.getText();
            if (!content.equals("")) {
                threadClientHandlerInput.getMesageFromInputField(content);
                textField.setText("");
            }
        }
    }
}