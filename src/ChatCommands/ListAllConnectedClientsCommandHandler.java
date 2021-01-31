package ChatCommands;

public class ListAllConnectedClientsCommandHandler extends AbstractCommand {
    @Override
    public void clientExecute(boolean isOp, String[] args, int senderId) {
        server.sendMessageTypeToClient(senderId, MessageTypes.NORMAL_MESSAGE.getValue(), "Listing all connected Clients...");
        if(server.clientMap.size() > 0) {
            for (int i : server.clientMap.keySet()) {
                String usernameAndId = server.clientMap.get(i).getUsernameWithID();
                if(senderId == server.clientMap.get(i).getMyId()) {
                    usernameAndId += " (me)";
                }

                if(server.clientMap.get(i).isOperator()) {
                    usernameAndId += " (OP)";
                }

                server.sendMessageTypeToClient(senderId, MessageTypes.NORMAL_MESSAGE.getValue(), "- " + usernameAndId);
            }
        } else {
            server.sendMessageTypeToClient(senderId, MessageTypes.NORMAL_MESSAGE.getValue(), "No clients connected!");
        }
    }

    @Override
    public void serverExecute(String[] args) {
        System.out.println("Listing all connected Clients...");
        if(server.clientMap.size() > 0) {
            for (int i : server.clientMap.keySet()) {
                String usernameAndId = server.clientMap.get(i).getUsernameWithID();

                if(server.clientMap.get(i).isOperator()) {
                    usernameAndId += " (OP)";
                }

                System.out.println("- " + usernameAndId);
            }
        } else {
            System.out.println("No clients connected!");
        }
    }

    @Override
    public void printDescription() {

    }
}
