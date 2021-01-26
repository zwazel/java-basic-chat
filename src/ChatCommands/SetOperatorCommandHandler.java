package ChatCommands;

public class SetOperatorCommandHandler extends AbstractCommand {
    @Override
    public void clientExecute(boolean isOp, String[] args, int senderId) {
        if(isOp) { // Only if the client is OP he can execute this command

        } else {
            server.sendMessage("You need to be OP for this command!", senderId);
        }
    }

    @Override
    public void serverExecute(String[] args) {

    }

    @Override
    public void printDescription() {

    }
}