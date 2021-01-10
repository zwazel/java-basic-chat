import java.net.Socket;
import java.util.Scanner;

public class ServerAndClient {
    Socket socket;
    Scanner scanner;
    int port;
    String username;
    int myId;

    public ServerAndClient() {
        scanner = new Scanner(System.in);
    }

    protected String getString(String command) {
        if (!command.equals("-1")) {
            System.out.print(command + Main.stringAfterCommand);
        }

        return scanner.nextLine();
    }

    protected int getInt(String command) {
        if (!command.equals("-1")) {
            System.out.print(command + Main.stringAfterCommand);
        }

        return Integer.parseInt(scanner.nextLine());
    }
}