package ChatCommands;

public abstract class AbstractCommand {
    public abstract void clientExecute(boolean isOp, String[] args, int senderId);

    public abstract void serverExecute(String[] args);


    public abstract void printDescription();
}