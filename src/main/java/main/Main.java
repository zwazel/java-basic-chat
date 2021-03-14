package main;

import ServerClient.client.Client;
import ServerClient.server.Server;

import java.util.Scanner;

public class Main {
    Scanner scanner;

    public Main(String[] args) {
        scanner = new Scanner(System.in);
        while (!init(args)); // While our input isn't valid
    }

    private boolean init(String[] args) {
        System.out.print("Do you want to host (0) or to join (1)? > "); // Tell the user what to do

        // TODO: catch error if not number
        switch (Integer.parseInt(scanner.nextLine())) { // get the input as string, and convert it to an Integer
            case 0: // case 0, behave as a ServerClient.server
                new Server(); // instantiate new ServerClient.server
                return true; // our input is valid
            case 1: // case 1, behave as a ServerClient.client
                new Client(args); // instantiate new ServerClient.client
                return true; // our input is valid
            default: // default, input is not valid
                System.out.println("Invalid Input! Try again"); // tell the user whats wrong
                return false; // input is not valid
        }
    }

    public static void main(String[] args) {
        new Main(args);
    }
}