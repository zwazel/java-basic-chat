package ChatCommands;

public class KickClientCommandHandler extends AbstractCommand {
    @Override
    public void clientExecute(boolean isOp, String[] args, int senderId) {
        if (isOp) {
            if (args.length > 0) {
                if(args[0].equalsIgnoreCase("all")) {
                    server.disconnectAllClients();
                } else {
                    String[] multipleTargetsString = args[0].split(",");
                    if (getTargetId(multipleTargetsString)) {
                        for (int i : targetList) {
                            if (server.checkIfClientExists(i)) {
                                // TODO: Make it possible for the user to specify a reason which will be send to the kicked user
                                // TODO: If the user doesnt specify a reason, auto generate one on your own

                                // TODO: Send reason to every client
                                server.disconnectSingleClient(i);
                            } else {
                                server.sendMessage("User with ID " + i + " does not exist!", senderId);
                            }
                        }
                    } else {
                        server.sendMessage(iNeedANumber, senderId);
                    }
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
        if (args.length > 0) {
            if(args[0].equalsIgnoreCase("all")) {
                server.disconnectAllClients();
            } else {
                String[] multipleTargetsString = args[0].split(",");

                if (getTargetId(multipleTargetsString)) {
                    for (int i : targetList) {
                        if (server.checkIfClientExists(i)) {
                            // TODO: Make it possible for the user to specify a reason which will be send to the kicked user
                            // TODO: If the user doesnt specify a reason, auto generate one on your own

                            // TODO: Send reason to all the clients
                            server.disconnectSingleClient(i);
                        } else {
                            System.out.println("User with ID " + i + " does not exist!");
                        }
                    }
                } else {
                    System.out.println(iNeedANumber);
                }
            }
        } else {
            System.out.println(needTargetId);
        }
    }

    @Override
    public void printDescription() {

    }
}
