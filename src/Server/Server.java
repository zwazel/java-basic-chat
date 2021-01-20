package Server;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Scanner;

public class Server {
    private Scanner scanner;
    int port;
    int idCounter = 0;
    int myId = idCounter++;
    ServerSocket ss;
    HashMap<Integer, ServerClient> clientMap = new HashMap<>();

    public Server() {
        scanner = new Scanner(System.in);

        port = getInt("The Port you are hosting on");

        ThreadHandleMessages threadHandleMessages = new ThreadHandleMessages("threadServerHandlerOutput");
        threadHandleMessages.start();
    }

    protected String getString(String command) {
        if (!command.equals("-1")) {
            System.out.print(command + " > ");
        }

        return scanner.nextLine();
    }

    protected int getInt(String command) {
        if (!command.equals("-1")) {
            System.out.print(command + " > ");
        }

        return Integer.parseInt(scanner.nextLine());
    }

    public static void main(String[] args) {
        new Server();
    }
}