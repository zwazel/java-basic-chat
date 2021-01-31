package ChatCommands;

public class KickClientCommandHandler extends AbstractCommand {
    @Override
    public void clientExecute(boolean isOp, String[] args, int senderId) {
        if(isOp) {
            if(args.length > 0) {
                try {
                    // TODO: Make it possible for the user to kick multiple clients at once. by seperating the numbers with a ',' and putting them in an array
                    int target = Integer.parseInt(args[0]);

                    if(server.checkIfClientExists(target)) {
                        // TODO: Make it possible for the user to specify a reason which will be send to the kicked user
                        // TODO: If the user doesnt specify a reason, auto generate one on your own

                        // TODO: Send reason to all the clients
                        server.disconnectSingleClient(target);
                    } else {
                        server.sendMessage(thisUserDoesNotExist, senderId);
                    }
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
        if(args.length > 0) {
            try {
                // TODO: Make it possible for the user to kick multiple clients at once. by seperating the numbers with a ',' and putting them in an array
                int target = Integer.parseInt(args[0]);

                if(server.checkIfClientExists(target)) {
                    // TODO: Make it possible for the user to specify a reason which will be send to the kicked user
                    // TODO: If the user doesnt specify a reason, auto generate one on your own

                    // TODO: Send reason to all the clients
                    server.disconnectSingleClient(target);
                } else {
                    System.out.println(thisUserDoesNotExist);
                }
            } catch (final NumberFormatException e) {
                System.out.println(iNeedANumber);
            }
        } else {
            System.out.println(needTargetId);
        }
    }

    @Override
    public void printDescription() {

    }
}
