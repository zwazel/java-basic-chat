package server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    public Server() throws IOException {
        ServerSocket ss = new ServerSocket(4999);
        Socket s = ss.accept();

        System.out.println("client connected");

        InputStreamReader in = new InputStreamReader(s.getInputStream()); // Inputs stuff into buffered reader... or something idk
        BufferedReader bf = new BufferedReader(in); // Reader that reads messages from client, maybe?

        String str = bf.readLine(); // Read message that we got from client
        System.out.println("client : " + str);

        PrintWriter pr = new PrintWriter(s.getOutputStream()); // Print to client

        pr.println(getString()); // Send message to client
        pr.flush();
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }

    private String getString() {
        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine();
    }
}