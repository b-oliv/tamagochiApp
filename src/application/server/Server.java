package application.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Server extends Thread {

	final private int PORT = 4242;
	final private List<ClientInServerThread> clientList = new ArrayList<ClientInServerThread>();
	final private Logger LOG = Logger.getLogger(Server.class.getName());

	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		run();
	}

	public void run() {
		try {
			ServerSocket serversocket = new ServerSocket(PORT);
			LOG.info("Server has started and is listening for clients.");

			while (true) {
				Socket socket = serversocket.accept();
				LOG.info("A client has connected.");
				// Get streams
				BufferedWriter outStream = new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
				outStream.flush(); // Flush directly after creating to avoid
									// deadlock
				BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				LOG.info("Streams to client established.");

				// Create a new thread and store it
				ClientInServerThread clientThread = new ClientInServerThread(this, outStream, inStream);
				clientList.add(clientThread);
				Thread thread = new Thread(clientThread);
				thread.start();
				LOG.info("A new client thread started.");

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void removeClient(ClientInServerThread client) {
		clientList.remove(clientList.indexOf(client));
	}

	protected void sendToClients(String message, ClientInServerThread sender) throws IOException {

		LOG.info("Sending message: " + message);

		for (ClientInServerThread clientThread : clientList) {

			if (clientThread == sender) {
				clientThread.getWriter().write(message + "\n");
				clientThread.getWriter().flush();
			}
		}
	}

}
