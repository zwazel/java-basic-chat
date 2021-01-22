import java.io.IOException;
import java.util.Scanner;
import Server.Server;
import Client.Client;

public class Main {
    Scanner scanner;
    final private static String errorWrongInput = "Invalid Input! Try again";

    public Main() {
        scanner = new Scanner(System.in);
        while (!init());
    }

    public static void main(String[] args) {
        new Main();
    }

    private boolean init() {
        System.out.print("Do you want to host (0) or to join (1)? > ");

        switch (Integer.parseInt(scanner.nextLine())) {
            case 0:
                new Server();
                return true;
            case 1:
                new Client();
                return true;
            default:
                System.out.println(errorWrongInput);
                return false;
        }
    }
}
