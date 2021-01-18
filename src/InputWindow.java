import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputWindow extends JPanel implements ActionListener, WindowListener {
    static ThreadHandleInput threadHandleInput;
    private static JTextField textField;
    static InputWindow inputWindow;
    private static JFrame frame;

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
        btn.addActionListener(this);
        centerPanel.add(btn);
        frame.addWindowListener(this);

        add(centerPanel, BorderLayout.CENTER);
    }

    public static InputWindow startWindow(ThreadHandleInput _threadHandleInput) {
        threadHandleInput = _threadHandleInput;

        frame = new JFrame(threadHandleInput.username + " InputWindowClient");
        inputWindow = new InputWindow();
        frame.add(inputWindow);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        return inputWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String content = textField.getText();
        if (!content.equals("")) {
            try {
                threadHandleInput.sendMessageToServer(content);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            textField.setText("");
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        Frame frame = (Frame) e.getSource();
        System.out.println("Closing = " + frame.getTitle());
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