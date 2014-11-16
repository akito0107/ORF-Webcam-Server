package jp.ac.keio.sfc.ht.memsys;

import us.sosia.video.stream.agent.StreamClient;
import us.sosia.video.stream.agent.StreamServer;

/**
 * Created by aqram on 11/16/14.
 */
public class Main {

    public static void main(String args[]){

        String arg = args[0];

        if(arg.equalsIgnoreCase("Server")){
            StreamServer.run();
        }else if(arg.equalsIgnoreCase("Client")){
            StreamClient.run();
        }

    }
}
