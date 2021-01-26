package ChatCommands;

import Server.ServerClient;

public class SetOperatorCommandHandler extends AbstractCommand {
    @Override
    public void clientExecute(boolean isOp, String[] args, int senderId) {
        if(args.length > 0) {
            try {
                int target = Integer.parseInt(args[0]);

                toggleOperator(target);

                if(isOp) { // Only if the client is OP he can execute this command
                    toggleOperator(target);
                } else {
                    server.sendMessage("You need to be OP for this command!", senderId);
                }
            } catch (final NumberFormatException e) {
                server.sendMessage("Pls enter the a number (the ID of the user)!", senderId);
            }
        } else {
            server.sendMessage("Pls tell me who should get OP rights!", senderId);
        }
    }

    @Override
    public void serverExecute(String[] args) {
        if(args.length > 0) {
            try {
                int target = Integer.parseInt(args[0]);

                toggleOperator(target);
            } catch (final NumberFormatException e) {
                System.out.println("Pls enter the a number (the ID of the user)!");
            }
        } else {
            System.out.println("Pls tell me who should get OP rights!");
        }
    }

    private void toggleOperator(int target) {
        if(server.clientMap.containsKey(target)) {
            ServerClient clientTarget = server.clientMap.get(target);
            clientTarget.toggleOperator();
            boolean isClientOp = clientTarget.isOperator();
            String clientTargetUsername = clientTarget.getUsernameWithID();

            if(isClientOp) {
                server.sendMessageToClients(clientTargetUsername + " is now operator!");
            } else {
                server.sendMessageToClients(clientTargetUsername + " is no longer operator!");
            }

            server.sendSpecialCase(target, 0);
        } else {
            System.out.println("This user does not exist! use the command \"/lc\" to see the connected users!");
        }
    }

    @Override
    public void printDescription() {

    }
}