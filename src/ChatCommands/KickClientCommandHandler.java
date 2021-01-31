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
    private String reasonForKick;

    @Override
    public void clientExecute(boolean isOp, String[] args, int senderId) {
        if (isOp) {
            if (args.length >= 1) {
                if(args.length >= 2) {
                    reasonForKick = args[1];
                }

                if(args[0].equalsIgnoreCase("all")) {
                    server.kickAllClients(reasonForKick);
                } else {
                    String[] multipleTargetsString = args[0].split(",");
                    if (getTargetId(multipleTargetsString)) {
                        for (int i : targetList) {
                            if (server.checkIfClientExists(i)) {
                                // TODO: Make it possible for the user to specify a reason which will be send to the kicked user
                                // TODO: If the user doesnt specify a reason, auto generate one on your own

                                // TODO: Send reason to every client
                                server.kickSingleClient(i, reasonForKick);
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
        if (args.length >= 1) {
            if(args.length >= 2) {
                reasonForKick = args[1];
            }

            if(args[0].equalsIgnoreCase("all")) {
                server.kickAllClients(reasonForKick);
            } else {
                String[] multipleTargetsString = args[0].split(",");

                if (getTargetId(multipleTargetsString)) {
                    for (int i : targetList) {
                        if (server.checkIfClientExists(i)) {
                            // TODO: Make it possible for the user to specify a reason which will be send to the kicked user
                            // TODO: If the user doesnt specify a reason, auto generate one on your own

                            // TODO: Send reason to all the clients
                            server.kickSingleClient(i, reasonForKick);
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

    private String getReasonForKick() {
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
            reasonForKick = all_lines.skip(n-1).findFirst().get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public void printDescription() {

    }
}
