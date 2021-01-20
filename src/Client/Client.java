package Client;

import java.util.Scanner;

public class Client {
    Scanner scanner;
    String username;
    int myId;
    

    public Client() {
        scanner = new Scanner(System.in);


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

        return Integer.parseInt(scanner.nextLine());
    }

    public static void main(String[] args) {
        new Client();
    }
}