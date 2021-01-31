package ChatCommands;

public class KickClientCommandHandler extends AbstractCommand {
    @Override
    public void clientExecute(boolean isOp, String[] args, int senderId) {
        if(isOp) {
            if(args.length > 0) {
                try {
                    int target = Integer.parseInt(args[0]);


                } catch (final NumberFormatException e) {
                    server.sendMessage(iNeedANumber, senderId);
                }
            } else {
                server.sendMessage(needTargetId, senderId);
            }
        } else {
            server.sendMessage(youNeedOp, senderId);
        }
    }

    @Override
    public void serverExecute(String[] args) {

    }

    @Override
    public void printDescription() {

    }
}
