package application.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Logger;

import application.model.EMOTION;
import application.model.SATIETY;
import application.model.Tamagochi;

public class ClientInServerThread implements Runnable {

	private int seconds = 0;
	private int secondsToEat = 0;
	private int secondsToPlay = 0;
	private BufferedWriter outStream;
	private BufferedReader inStream;
	private Server server;
	private Tamagochi tamagochi;

	final private Logger LOG = Logger.getLogger(ClientInServerThread.class.getName());

	public ClientInServerThread(Server server, BufferedWriter outStream, BufferedReader inStream) {
		this.server = server;
		this.outStream = outStream;
		this.inStream = inStream;
		tamagochi = new Tamagochi();
	}

	@Override
	public void run() {
		try {
			tamagochi.setName(inStream.readLine());
			LOG.info("---|||Tamagochi <" + tamagochi.getName() + "> Created|||---");
			while (tamagochi.isAlive()) {
				Thread.sleep(1000);
				if (inStream.ready()) {
					String clientSend = inStream.readLine();
					System.err.println(clientSend);
					if (clientSend.contains("MANGE")) {
						tamagochi.setSatiety(SATIETY.REPU);
						server.sendToClients(tamagochi.getSatiety() + "", this);
						secondsToEat = 0;
					} else if (clientSend.contains("PLAY")) {
						tamagochi.setEmotion(EMOTION.CONTENT);
						server.sendToClients(tamagochi.getEmotion() + "", this);
						secondsToPlay = 0;
					} else if (clientSend.contains("DEAD")) {
						tamagochi.setAlive(false);
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
