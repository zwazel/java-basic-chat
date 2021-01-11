import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputWindowClient extends JPanel {
    static ThreadClientHandlerInput threadClientHandlerInput;
    private static JTextField textField;
    static InputWindowClient inputWindowClient;

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

    public static InputWindowClient startWindow(ThreadClientHandlerInput _threadClientHandlerInput) {
        threadClientHandlerInput = _threadClientHandlerInput;

        JFrame frame = new JFrame(threadClientHandlerInput.username + " InputWindowClient");
        inputWindowClient = new InputWindowClient();
        frame.add(inputWindowClient);
        frame.setVisible(true);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return inputWindowClient;
    }

    private static class BtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String content = textField.getText();
            //System.out.println("Message: " + content);
            threadClientHandlerInput.getMesageFromInputField(content);
            textField.setText("");
        }
    }
}