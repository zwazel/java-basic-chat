package GlobalStuff;

public enum MessageTypes {
    UNDEFINED,
    DISCONNECT,
    NORMAL_MESSAGE,
    KICK_CLIENT,
    TOGGLE_OP,
    CLIENT_COMMAND,
    MUTE_CLIENT,
    ;
    private final byte value;

    MessageTypes() {
        this.value = (byte) ordinal();
    }

    public byte getValue() {
        return this.value;
    }
}
