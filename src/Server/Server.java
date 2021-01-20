package Server;

import java.util.Scanner;

public class Server {
    Scanner scanner;
    int port;

    public Server() {
        scanner = new Scanner(System.in);

        port = getInt("On")
    }

    protected String getString(String command) {
        if (!command.equals("-1")) {
            System.out.print(command + stringAfterCommand);
        }

        return scanner.nextLine();
    }

    protected int getInt(String command) {
        if (!command.equals("-1")) {
            System.out.print(command + stringAfterCommand);
        }

        return Integer.parseInt(scanner.nextLine());
    }

    public static void main(String[] args) {
        new Server();
    }
}