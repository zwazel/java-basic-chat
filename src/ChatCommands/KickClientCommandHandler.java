package ChatCommands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

public class KickClientCommandHandler extends AbstractCommand {
    private String reasonForKickMain = "";
    private String reasonForKickStart = "Server: ";
    private String reasonForKickStartToKickedClient = "You've been kicked\n" +
            "Reason: ";
    private String getReasonForKickStartToAllOtherClients = " has been kicked\n" +
            "Reason: " ;

    @Override
    public void clientExecute(boolean isOp, String[] args, int senderId) {
        if (isOp) {
            kick(args, senderId);
        } else {
            server.sendMessageTypeToClient(senderId, MessageTypes.NORMAL_MESSAGE.getValue(), youNeedOp);
        }
    }

    @Override
    public void serverExecute(String[] args) {
        kick(args, 0);
    }

    private void kick(String[] args, int senderId) {
        int argsLength = args.length;

        if (argsLength >= 1) {
            // TODO: If the user doesnt specify a reason, auto generate one on your own
            if(argsLength >= 2) {
                for(int i = 1; i < args.length; i++) {
                    String reasonPart = args[i];
                    reasonForKickMain += reasonPart + " ";
                }
            } else {
                // Get message from text file
            }
        } else {
            if(senderId > 0) {
                server.sendMessageTypeToClient(senderId, MessageTypes.NORMAL_MESSAGE.getValue(), needTargetId);
            } else {
                System.out.println(needTargetId);
            }
        }

        if(args[0].equalsIgnoreCase("all")) {
            server.sendMessageTypeToAllClients(MessageTypes.KICK.getValue(), reasonForKickStart + reasonForKickStartToKickedClient + reasonForKickMain);
        } else {
            String[] multipleTargetsString = args[0].split(",");
            if (getTargetId(multipleTargetsString)) {
                for (int i : targetList) {
                    if (server.checkIfClientExists(i)) {
                        String kickedClientUsername = server.getClientUsername(i);
                        server.sendMessageTypeToClient(i, MessageTypes.KICK.getValue(), reasonForKickStart + reasonForKickStartToKickedClient + reasonForKickMain); // Kick the client
                        server.sendMessageTypeToAllClients(MessageTypes.NORMAL_MESSAGE.getValue(), reasonForKickStart + kickedClientUsername + getReasonForKickStartToAllOtherClients + reasonForKickMain); // Tell all the other clients who got kicked
                    } else {
                        if(senderId > 0) {
                            server.sendMessageTypeToClient(senderId, MessageTypes.NORMAL_MESSAGE.getValue(), "User with ID " + i + " does not exist!");
                        } else {
                            System.out.println("User with ID " + i + " does not exist!");
                        }
                    }
                }
            } else {
                server.sendMessageTypeToClient(senderId, MessageTypes.NORMAL_MESSAGE.getValue(), iNeedANumber);
            }
        }
    }

    private String getReasonForKickMain() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("file.txt"));

            int lines = 0;
            while (true) {
                try {
                    if (reader.readLine() == null) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                lines++;
            }
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Random rand = new Random();
        int n = rand.nextInt(1) + 1;

        try (Stream<String> all_lines = Files.lines(Paths.get("kickReasons.txt"))) {
            reasonForKickMain = all_lines.skip(n-1).findFirst().get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public void printDescription() {

    }
}
