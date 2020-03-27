package application.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Logger;

public class ClientInServerThread implements Runnable {

	private int seconds = 0;
	private int secondsToEat = 0;
	private int secondsToPlay = 0;
	private BufferedWriter outStream;
	private BufferedReader inStream;
	private Server server;

	final private Logger LOG = Logger.getLogger(ClientInServerThread.class.getName());

	public ClientInServerThread(Server server, BufferedWriter outStream, BufferedReader inStream) {
		this.server = server;
		this.outStream = outStream;
		this.inStream = inStream;
	}

	@Override
	public void run() {
		try {

			while (true) {
				Thread.sleep(1000);
				if (inStream.ready()) {
					String clientSend = inStream.readLine();
					if (clientSend.contains("MANGE")) {
						server.sendToClients("Il a bien mangé", this);
						secondsToEat = 0;
					} else if (clientSend.contains("PLAY")) {
						server.sendToClients("Il s'amuse comme un fou !!", this);
						secondsToPlay = 0;
					}
				}
				server.sendToClients("" + seconds, this);
				seconds++;
				secondsToEat++;
				secondsToPlay++;
				this.checkEat();
				this.checkPlay();
			}
		} catch (SocketException e) {
			server.removeClient(this);
			LOG.info("Client was lost.");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void checkEat() throws IOException {
		if (secondsToEat == 10) {
			server.sendToClients("FAIM", this);
		} else if (secondsToEat == 20) {
			server.sendToClients("DEAD", this);
		}
	}

	private void checkPlay() throws IOException {
		if (secondsToPlay == 15) {
			server.sendToClients("ENNUIE", this);
		} else if (secondsToPlay == 50) {
			server.sendToClients("DEAD", this);
		}
	}

	public BufferedWriter getWriter() {
		return this.outStream;
	}
}
