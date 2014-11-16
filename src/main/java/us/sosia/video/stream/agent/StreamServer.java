package us.sosia.video.stream.agent;

import java.awt.Dimension;
import java.net.InetSocketAddress;

import com.github.sarxos.webcam.Webcam;
import jp.ac.keio.sfc.ht.memsys.Constants;


public class StreamServer {

	/**
	 * @author kerr
	 */
	public static void run() {
		Webcam.setAutoOpenMode(true);
		Webcam webcam = Webcam.getDefault();
		Dimension dimension = new Dimension(Constants.WIDTH, Constants.HEIGHT);
		webcam.setViewSize(dimension);

		StreamServerAgent serverAgent = new StreamServerAgent(webcam, dimension);
		serverAgent.start(new InetSocketAddress("localhost", 20000));
	}

}
