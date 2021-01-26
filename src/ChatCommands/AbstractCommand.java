package ChatCommands;

public abstract class AbstractCommand {
    public abstract void execute(boolean isOp, String[] args);

    public abstract void printDescription();
}