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
}