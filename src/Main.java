import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    final private static String stringAfterCommand = " > ";
    final private static String errorWrongInput = "Invalid Input! Try again";

    public Main() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (!init(scanner));
    }

    public static void main(String[] args)throws IOException {
        new Main();
    }

    private boolean init(Scanner scanner) throws IOException {
        System.out.print("Hosten (0) oder Joinen (1)?" + stringAfterCommand);
        int num = scanner.nextInt();

        switch (num) {
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

    public static String getString(String command) {
        if (!command.equals("-1")) {
            System.out.print(command + stringAfterCommand);
        }

        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static int getInt(String command) {
        if (!command.equals("-1")) {
            System.out.print(command + stringAfterCommand);
        }

        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static String getMessageFromNetwork(Socket s) throws IOException {
        InputStreamReader in = new InputStreamReader(s.getInputStream()); // Inputs stuff into buffered reader... or something idk
        BufferedReader bf = new BufferedReader(in); // Reader that reads messages from client/Server, maybe?
        String str = bf.readLine(); // Read message that we got from client/server

        return str;
    }

    public static void sendMessageToNetwork(Socket s, String username, String message) throws IOException {
        PrintWriter pr = new PrintWriter(s.getOutputStream()); // Printer
        pr.println(username + ": " + message);
        pr.flush();
    }
}