import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerAndClient {
    int port;
    String username;
    int myId;

    public ServerAndClient() {

    }

    protected String getString(String command) {
        if (!command.equals("-1")) {
            System.out.print(command + Main.stringAfterCommand);
        }

        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    protected int getInt(String command) {
        if (!command.equals("-1")) {
            System.out.print(command + Main.stringAfterCommand);
        }

        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    protected String getMessageFromNetwork(Socket s) throws IOException {
        InputStreamReader in = new InputStreamReader(s.getInputStream()); // Inputs stuff into buffered reader... or something idk
        BufferedReader bf = new BufferedReader(in); // Reader that reads messages from client/Server, maybe?
        String str = bf.readLine(); // Read message that we got from client/server

        return str;
    }

    protected void sendMessageToNetwork(Socket s, String username, String message) throws IOException {
        PrintWriter pr = new PrintWriter(s.getOutputStream()); // Printer
        pr.println(username + ": " + message);
        pr.flush();
    }
}