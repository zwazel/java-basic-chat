package ChatCommands;

import Server.Server;

public class ListAllConnectedClientsCommandHandler extends AbstractCommand {
    @Override
    public void execute(boolean isOp, String[] args) {
        System.out.println("Listing all connected Clients...");
        Server server = Server.get();
        if(server.clientMap.size() > 0) {
            for (int i : server.clientMap.keySet()) {
                server.clientMap.get(i).print();
            }
        } else {
            System.out.println("No clients connected!");
        }
    }

    @Override
    public void printDescription() {

    }
}
