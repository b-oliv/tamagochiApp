package application.view;

import java.io.IOException;
import java.util.Random;

import application.TamagochiApplication;
import application.model.UserConfig;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ConfigView extends BaseView {

	private AnchorPane anchorPane = null;
	private VBox vbox;
	private TextField userNameInput;
	private TextField tamagochiNameInput;
	private TextField ipInput;
	private TextField portInput;
	private Button startBtn;

	private TamagochiApplication mainApp = BaseView.getMainApp();

	public AnchorPane getView() {
		if (anchorPane == null)
			this.createView();
		return anchorPane;
	}

	private EventHandler<ActionEvent> onBtnEvent = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {

			if (event.getSource() == startBtn) {
				UserConfig userCfg = new UserConfig();
				userCfg.setUsername(userNameInput.getText());
				userCfg.setTamagochiname(tamagochiNameInput.getText());
				userCfg.setHost(ipInput.getText());
				userCfg.setPort(portInput.getText());

				mainApp.setUserCfg(userCfg);

				try {
					mainApp.startChatClient();
				} catch (IOException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Could not connect");
					alert.setHeaderText("Could not connect to the server.");
					alert.setContentText("Make sure you're using the correct IP and Port, or that\n"
							+ "the server you're trying to connect to is running.");

					alert.showAndWait();

					return;
				}

				mainApp.setView(mainApp.getChatView());
			}

		}
	};

	private void createView() {
		anchorPane = new AnchorPane();
		vbox = new VBox(10);

		Label label = new Label("User Name");

		userNameInput = new TextField();
		userNameInput.setText("User-" + new Random().nextInt(9000));
		userNameInput.setAlignment(Pos.CENTER);

		vbox.getChildren().add(label);
		vbox.getChildren().add(userNameInput);

		label = new Label("Tamagochi Name");

		tamagochiNameInput = new TextField();
		tamagochiNameInput.setText("Tamagochi-" + new Random().nextInt(9000));
		tamagochiNameInput.setAlignment(Pos.CENTER);

		vbox.getChildren().add(label);
		vbox.getChildren().add(tamagochiNameInput);

		label = new Label("Host");

		ipInput = new TextField();
		ipInput.setText("127.1.0.0");
		ipInput.setAlignment(Pos.CENTER);

		vbox.getChildren().add(label);
		vbox.getChildren().add(ipInput);

		label = new Label("Port");

		portInput = new TextField();
		portInput.setText("4242");
		portInput.setAlignment(Pos.CENTER);

		vbox.getChildren().add(label);
		vbox.getChildren().add(portInput);

		startBtn = new Button("Connect");
		startBtn.setPrefWidth(160.0);
		startBtn.setOnAction(onBtnEvent);
		startBtn.setAlignment(Pos.CENTER);

		vbox.getChildren().add(startBtn);

		AnchorPane.setTopAnchor(vbox, 45.0);
		AnchorPane.setLeftAnchor(vbox, 130.0);
		AnchorPane.setRightAnchor(vbox, 130.0);

		anchorPane.getChildren().add(vbox);
	}

}
