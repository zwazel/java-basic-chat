import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputWindowServer extends JPanel {
    static ThreadServerHandlerInput threadServerHandlerInput;
    private static JTextField textField;
    static InputWindowServer inputWindowServer;

    public InputWindowServer() {
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

    public static InputWindowServer startWindow(ThreadServerHandlerInput _threadServerHandlerInput) {
        threadServerHandlerInput = _threadServerHandlerInput;

        JFrame frame = new JFrame(threadServerHandlerInput.username + " InputWindowServer");
        inputWindowServer = new InputWindowServer();
        frame.add(inputWindowServer);
        frame.setVisible(true);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return inputWindowServer;
    }

    private static class BtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String content = textField.getText();
            if (!content.equals("")) {
                threadServerHandlerInput.getMessageFromInputField(content);
                textField.setText("");
            }
        }
    }
}