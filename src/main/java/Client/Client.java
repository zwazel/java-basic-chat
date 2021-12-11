package Client;

import GlobalStuff.MessageTypes;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  private Scanner scanner;
  private String username;        // my username
  private int myId;               // My id
  private Socket s;               // my socket
  private final String serverIp;  // The ip of the server
  private final int serverPort;   // the open port of the server
  private boolean running = true; // are we running?
  private ThreadHandleMessagesClient
      threadHandleMessagesClient;     // Our thread which handles our messages
  protected boolean operator = false; // is the user an operator?
  private Color messageColor = Color.WHITE; // The Color of the users messages
  private Status clientStatus = Status.AVAILABLE;

  public Client() {
    scanner = new Scanner(System.in);

    serverIp = getString("The IP of the server"); // Get the IP of the server
    serverPort = getInt(
        "The open Port of the server"); // Get the open port of the server

    username = getString("Your username"); // get my username

    init();
  }

  public int getMyId() { return myId; }

  private void init() {
    try {
      s = new Socket(serverIp,
                     serverPort); // instantiate new socket with IP and PORT
      System.out.println("Connected to server " + serverIp + " on port " +
                         serverPort + " with username " +
                         username); // Tell the user that we have successfully
                                    // established a connection to the server

      // Reading my ID
      System.out.println(
          "Getting ID from Server..."); // Tell the user that we're getting our
                                        // ID right now
      DataInputStream dIn =
          new DataInputStream(s.getInputStream()); // Create new input stream
      myId = dIn.readInt();                        // Read int from the server
      System.out.println("My ID: " +
                         myId); // Set the ID to the number we got from server

      // Sending my username to the server, so he can add us to the hashmap
      DataOutputStream dOut = new DataOutputStream(
          s.getOutputStream()); // Create new output stream, linked with the
                                // client that just connected
      dOut.writeUTF(username);  // put the username in the stream
      dOut.flush();             // Send off the data

      // Start thread which handles our messages
      threadHandleMessagesClient = new ThreadHandleMessagesClient(
          "HandleMessages " + username + " " + myId, username, myId, s,
          this);                          // instantiate new thread
      threadHandleMessagesClient.start(); // Start new thread

      // Get messages from server
      printMessageFromServer();
    } catch (IOException e) {
      System.out.println("Can't create new socket! VERY BAD");
    }
  }

  private void printMessageFromServer() {
    while (running) { // While we are running
      try {
        DataInputStream dIn = new DataInputStream(
            s.getInputStream()); // instantiate new dataInputStream which is
                                 // linked to the client
        MessageTypes messageType =
            MessageTypes
                .values()[dIn.readByte()]; // read the byte (message type) and
                                           // get the corresponding enum

        int senderId = -1;
        String senderName = "";
        String messageBody = "";

        // TODO: Find a way to intelligently check if we get an empty message or
        // if we have stuff to read, can we read sender ID, senderName, etc, or
        // not?
        switch (messageType) { // Check what type of message we got
        case DISCONNECT:       // message tells us the server disconnected
          System.out.println(
              "Server disconnected! Disconnecting myself..."); // print what
                                                               // happened
          running = false; // Stop everything
          break;
        case NORMAL_MESSAGE: // Normal message
          senderId = dIn.readInt();
          senderName = dIn.readUTF();
          messageBody = dIn.readUTF();
          // check if the Message was from a client or just a information for
          // this user
          if (senderId > -1) {
            if (senderId == myId) {
              senderName += " (Me)";
              messageColor = Color.YELLOW;
              senderName += ": ";
            } else {
              senderName += ": ";
              sendSystemMessage(senderName + messageBody,
                                TrayIcon.MessageType.INFO, "New message");
            }
          }

          System.out.println(senderName +
                             messageBody); // we got a Normal message
          threadHandleMessagesClient.append(
              senderName + messageBody + "\n",
              threadHandleMessagesClient.getTextPane(), messageColor);
          messageColor = Color.WHITE;
          break;
        case TOGGLE_OP: // OP should be toggled
          toggleOperator();
          break;
        case KICK_CLIENT: // I'M GETTING KICKED?!?! :(
          senderId = dIn.readInt();
          senderName = dIn.readUTF();
          messageBody = dIn.readUTF();

          if (senderId == myId) {
            senderName += " (Me)";
            messageColor = Color.YELLOW;
          }
          senderName += ": ";

          System.out.println(senderName + messageBody);
          messageColor = Color.RED;
          threadHandleMessagesClient.append(
              senderName + messageBody + "\n",
              threadHandleMessagesClient.getTextPane(), messageColor);
          messageColor = Color.WHITE;
          running = false;
          break;
        }
      } catch (IOException e) {
        System.out.println("Can't print message! VERY BAD");
      }
    }

    // If we shouldn't run anymore, end it
    System.out.println("Thread ended Client " +
                       username); // Tell the user that this thread has stopped
    threadHandleMessagesClient.stopWindow(); // Close the window
  }

  public boolean isOperator() { return operator; }

  public void toggleOperator() {
    this.operator = !this.operator;

    if (operator) {
      System.out.println("You are now operator!");
    } else {
      System.out.println("You're no longer operator!");
    }
  }

  private void sendSystemMessage(String message,
                                 TrayIcon.MessageType messageType,
                                 String title) {
    if (SystemTray.isSupported()) {
      // Obtain only one instance of the SystemTray object
      SystemTray tray = SystemTray.getSystemTray();

      // If the icon is a file
      Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
      // Alternative (if the icon is on the classpath):
      // Image image =
      // Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

      TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
      // Let the system resize the image if needed
      trayIcon.setImageAutoSize(true);
      // Set tooltip text for the tray icon
      trayIcon.setToolTip("System tray icon demo");
      try {
        tray.add(trayIcon);
      } catch (AWTException e) {
        e.printStackTrace();
      }
      if (clientStatus != Status.DONOTDISTURB) {
        trayIcon.displayMessage(title, message, messageType);
      }
    } else {
      System.err.println("System tray not supported!");
    }
  }

  private String getString(String command) {
    if (!command.equals("-1")) {
      System.out.print(command + " > ");
    }

    return scanner.nextLine();
  }

  private int getInt(String command) {
    if (!command.equals("-1")) {
      System.out.print(command + " > ");
    }

    // TODO: catch error if not number
    return Integer.parseInt(scanner.nextLine());
  }

  public static void main(String[] args) { new Client(); }

  public Status getClientStatus() { return clientStatus; }

  public void setClientStatus(Status status) { clientStatus = status; }
}