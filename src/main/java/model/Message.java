package model;

public class Message {
    private String message;
    private String username;
    private int id;

    public Message(String username, String message) {
        this.message = message;
        this.username = username;
    }
}