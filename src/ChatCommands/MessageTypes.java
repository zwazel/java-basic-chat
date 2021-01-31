package ChatCommands;

public enum MessageTypes {
    DISCONNECT,
    NORMAL_MESSAGE,
    KICK,
    TOGGLE_OP,
    CLIENT_COMMAND,
    ;

    private final byte value;

    MessageTypes() {
        this.value = (byte) ordinal();
    }

    public byte getValue() {
        return this.value;
    }
}
