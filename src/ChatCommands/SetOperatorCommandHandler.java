package ChatCommands;

import Server.ServerClient;

public class SetOperatorCommandHandler extends AbstractCommand {
    @Override
    public void clientExecute(boolean isOp, String[] args, int senderId) {
        if(isOp) { // Only if the client is OP he can execute this command
            if(args.length > 0) {
                try {
                    int target = Integer.parseInt(args[0]);

                    toggleOperator(target, senderId);
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
                int target = Integer.parseInt(args[0]);

                toggleOperator(target, 0);
            } catch (final NumberFormatException e) {
                System.out.println(iNeedANumber);
            }
        } else {
            System.out.println(needTargetId);
        }
    }

    private void toggleOperator(int target, int senderId) {
        if(server.checkIfClientExists(target)) {
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
            if(senderId > 0) {
                server.sendMessage(thisUserDoesNotExist, senderId);
            } else {
                System.out.println(thisUserDoesNotExist);
            }
        }
    }

    @Override
    public void printDescription() {
        
    }
}
