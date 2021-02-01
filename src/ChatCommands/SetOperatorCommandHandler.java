package ChatCommands;

import Server.ServerClient;
import Main.MessageTypes;

public class SetOperatorCommandHandler extends AbstractCommand {
    @Override
    public void clientExecute(boolean isOp, String[] args, int senderId) {
        if(isOp) { // Only if the client is OP he can execute this command
            if(args.length > 0) {
                try {
                    int target = Integer.parseInt(args[0]);

                    toggleOperator(target, senderId);
                } catch (final NumberFormatException e) {
                    server.sendMessageTypeToClient(server.getId(), senderId, MessageTypes.NORMAL_MESSAGE.getValue(), iNeedANumber);
                }
            } else {
                server.sendMessageTypeToClient(server.getId(), senderId, MessageTypes.NORMAL_MESSAGE.getValue(), needTargetId);
            }
        } else {
            server.sendMessageTypeToClient(server.getId(), senderId, MessageTypes.NORMAL_MESSAGE.getValue(), youNeedOp);
        }
    }

    @Override
    public void serverExecute(String[] args) {
        if(args.length > 0) {
            try {
                int target = Integer.parseInt(args[0]);

                toggleOperator(target, server.getId());
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
                server.sendMessageTypeToAllClients(senderId, MessageTypes.NORMAL_MESSAGE.getValue(), clientTargetUsername + " is now operator!");
            } else {
                server.sendMessageTypeToAllClients(senderId, MessageTypes.NORMAL_MESSAGE.getValue(), clientTargetUsername + " is no longer operator!");
            }

            server.sendMessageTypeToClient(target, MessageTypes.TOGGLE_OP.getValue());
        } else {
            if(senderId > 0) {
                server.sendMessageTypeToClient(server.getId(), senderId, MessageTypes.NORMAL_MESSAGE.getValue(), thisUserDoesNotExist);
            } else {
                System.out.println(thisUserDoesNotExist);
            }
        }
    }

    @Override
    public void printDescription() {
        
    }
}
