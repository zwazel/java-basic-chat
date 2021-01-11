import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputWindow extends JPanel {
    private static JTextField textField;

    public InputWindow() {
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("InputWindow");
        frame.add(new InputWindow());
        frame.setVisible(true);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void startWindow() {
        JFrame frame = new JFrame("InputWindow");
        frame.add(new InputWindow());
        frame.setVisible(true);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static class BtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String content = textField.getText();
        }
    }
}