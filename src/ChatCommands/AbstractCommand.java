package ChatCommands;

import Server.Server;

public abstract class AbstractCommand {
    protected Server server = Server.get();

    protected String youNeedOp = "You need to have OP rights to use this command!";
    protected String needTargetId = "Pls tell me who is the target of this command (ID of the client)";
    protected String thisUserDoesNotExist = "This user does not exist! use the command \"/lc\" to see the connected users!";
    protected String iNeedANumber = "Pls enter a number as an argument!";

    public abstract void clientExecute(boolean isOp, String[] args, int senderId);

    public abstract void serverExecute(String[] args);

    public abstract void printDescription(); // If using this command with "help" as an argument, call this method
}
