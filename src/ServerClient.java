import java.net.Socket;

public class ServerClient {
    String username;
    int myId;
    Socket socket;

    public ServerClient(Socket socket, String username, int myId) {
        this.username = username;
        this.socket = socket;
        this.myId = myId;
    }

    public void print() {
        System.out.println(username);
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return myId;
    }

    public Socket getSocket() {
        return socket;
    }
}