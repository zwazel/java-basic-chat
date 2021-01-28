package ChatCommands;

import Server.Server;

public abstract class AbstractCommand {
    Server server = Server.get();

    public abstract void clientExecute(boolean isOp, String[] args, int senderId);

    public abstract void serverExecute(String[] args);

    public abstract void printDescription(); // If using this command with "help" as an argument, call this method
}
