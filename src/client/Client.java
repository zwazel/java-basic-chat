package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public Client() throws IOException {
        Socket s = new Socket("localhost", 4999); // Create socket, connect with host on port

        PrintWriter pr = new PrintWriter(s.getOutputStream());
        pr.println(getString());
        pr.flush();

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        String str = bf.readLine();
        System.out.println("server : " + str);
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }

    private String getString() {
        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine();
    }
}