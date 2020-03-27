package application.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.SocketException;

import application.view.ConfigView;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClientThread implements Runnable {

	private TamagochiClient chatClient;
	private BufferedWriter outStream;
	private BufferedReader inStream;

	public ClientThread(TamagochiClient chatClient, BufferedWriter outStream, BufferedReader inStream) {
		this.chatClient = chatClient;
		this.outStream = outStream;
		this.inStream = inStream;
	}

	@Override
	public void run() {
		try {

			while (true) {
				String message = inStream.readLine();
				chatClient.getMainApp().appendToLogs(message);
				System.out.println("" + message);
			}
		} catch (SocketException e) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Connection Lost");
					alert.setHeaderText("Lost the connection to the server.");
					alert.setContentText("The connection to the server was lost.");
					alert.showAndWait();
					chatClient.getMainApp().setView(new ConfigView());
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BufferedWriter getWriter() {
		return this.outStream;
	}

}
