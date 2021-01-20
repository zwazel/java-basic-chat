package Server;

import java.net.Socket;

public class ServerClient {
    private int myId = -1;
    private String username = "Not Defined";
    private Socket s;

    public ServerClient(int myId, String username, Socket s) {
        this.myId = myId;
        this.username = username;
        this.s = s;
    }

    public int getMyId() {
        return myId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Socket getSocket() {
        return s;
    }
}