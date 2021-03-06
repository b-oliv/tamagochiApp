package application.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import application.TamagochiApplication;

public class TamagochiClient {

	private TamagochiApplication mainApp;

	public Socket clientSocket;
	private ClientThread clientThread;

	public TamagochiClient(TamagochiApplication mainApp) {
		this.mainApp = mainApp;
	}

	public void start() throws IOException {
		String host = mainApp.getUserCfg().getHost();
		int port = Integer.valueOf(mainApp.getUserCfg().getPort());
		clientSocket = new Socket(host, port);
		BufferedWriter outStream = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
		outStream.flush();
		BufferedReader inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
		clientThread = new ClientThread(this, outStream, inStream);
		Thread thread = new Thread(clientThread);
		this.sendMessage(mainApp.getUserCfg().getTamagochiname() + "\n"); // send name tama to server for pooj
		thread.start();
	}

	public void sendMessage(String message) {
		try {
			clientThread.getWriter().write(message);
			clientThread.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected TamagochiApplication getMainApp() {
		return mainApp;
	}

}
