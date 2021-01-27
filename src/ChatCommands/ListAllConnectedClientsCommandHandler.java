package ChatCommands;

public class ListAllConnectedClientsCommandHandler extends AbstractCommand {
    @Override
    public void clientExecute(boolean isOp, String[] args, int senderId) {
        server.sendMessage("Listing all connected Clients...", senderId);
        if(server.clientMap.size() > 0) {
            for (int i : server.clientMap.keySet()) {
                String usernameAndId = server.clientMap.get(i).getUsernameWithID();
                if(senderId == server.clientMap.get(i).getMyId()) {
                    usernameAndId += " (me)";
                }

                if(server.clientMap.get(i).isOperator()) {
                    usernameAndId += " (OP)";
                }

                server.sendMessage("- " + usernameAndId, senderId);
            }
        } else {
            server.sendMessage("No clients connected!", senderId);
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
