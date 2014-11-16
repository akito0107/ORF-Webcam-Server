package jp.ac.keio.sfc.ht.memsys;

import us.sosia.video.stream.agent.StreamClient;
import us.sosia.video.stream.agent.StreamServer;

/**
 * Created by aqram on 11/16/14.
 */
public class Main {

    public static void main(String args[]) {

        String arg = args[0];

        if (arg.equalsIgnoreCase("Server")) {
            String arg2 = args[1];
            if (arg2.equalsIgnoreCase("LOCAL")) {
                Constants.LOCAL_PROCESSING = true;
            } else if (arg2.equalsIgnoreCase("CLOUD")) {
                Constants.LOCAL_PROCESSING = false;
            }
            StreamServer.run();
        } else if (arg.equalsIgnoreCase("Client")) {
            StreamClient.run();
        }

    }
}
