package ChatCommands;

import Main.MessageTypes;

public class ListAllConnectedClientsCommandHandler extends AbstractCommand {
    @Override
    public void clientExecute(boolean isOp, String[] args, int senderId) {
        server.sendMessageTypeToClient(-1, senderId, MessageTypes.NORMAL_MESSAGE.getValue(), "\nListing all connected Clients...");
        if(server.clientMap.size() > 0) {
            String message = "";
            for (int i : server.clientMap.keySet()) {
                String usernameAndId = server.clientMap.get(i).getUsernameWithID();
                if(senderId == server.clientMap.get(i).getMyId()) {
                    usernameAndId += " (me)";
                }

                if(server.clientMap.get(i).isOperator()) {
                    usernameAndId += " (OP)";
                }

                message+="- " + usernameAndId + "\n";
            }

            server.sendMessageTypeToClient(-1, senderId, MessageTypes.NORMAL_MESSAGE.getValue(), message);
        } else {
            server.sendMessageTypeToClient(-1, senderId, MessageTypes.NORMAL_MESSAGE.getValue(), "No clients connected!");
        }
    }

    @Override
    public void serverExecute(String[] args) {
        System.out.println("\nListing all connected Clients...");
        if(server.clientMap.size() > 0) {
            String message = "";
            for (int i : server.clientMap.keySet()) {
                String usernameAndId = server.clientMap.get(i).getUsernameWithID();

                if(server.clientMap.get(i).isOperator()) {
                    usernameAndId += " (OP)";
                }

                message+="- " + usernameAndId + "\n";
            }

            System.out.println(message);
        } else {
            System.out.println("No clients connected!");
        }
    }

    @Override
    public void printDescription() {

    }
}
