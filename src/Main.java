import java.io.IOException;
import java.util.Scanner;

public class Main {
    final public static String stringAfterCommand = " > ";
    final private static String errorWrongInput = "Invalid Input! Try again";

    public Main() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (!init(scanner));
    }

    public static void main(String[] args)throws IOException {
        new Main();
    }

    private boolean init(Scanner scanner) throws IOException {
        System.out.print("Do you want to host (0) or to join (1)?" + stringAfterCommand);
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
}
