package ChatCommands;

import Server.Server;

public class ListAllConnectedClientsCommandHandler extends AbstractCommand {
    @Override
    public void clientExecute(boolean isOp, String[] args, int senderId) {
        Server server = Server.get();
        server.sendMessage("Listing all connected Clients...", senderId);
        if(server.clientMap.size() > 0) {
            for (int i : server.clientMap.keySet()) {
                String usernameAndId = server.clientMap.get(i).getUsernameWithID();
                if(senderId == server.clientMap.get(i).getMyId()) {
                    usernameAndId += " (me)";
                }
                server.sendMessage(usernameAndId, senderId);
            }
        } else {
            System.out.println("No clients connected!");
        }
    }

    @Override
    public void serverExecute(String[] args) {
        Server server = Server.get();
        System.out.println("Listing all connected Clients...");
        if(server.clientMap.size() > 0) {
            for (int i : server.clientMap.keySet()) {
                String usernameAndId = server.clientMap.get(i).getUsernameWithID();
                System.out.println(usernameAndId);
            }
        } else {
            System.out.println("No clients connected!");
        }
    }

    @Override
    public void printDescription() {

    }
}
