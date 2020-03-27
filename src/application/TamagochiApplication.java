package application;

import java.io.IOException;

import application.client.TamagochiClient;
import application.model.Tamagochi;
import application.model.UserConfig;
import application.view.BaseView;
import application.view.ConfigView;
import application.view.TamagochiView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TamagochiApplication extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private UserConfig userCfg;
	private TamagochiClient tamagochiClient;
	private ConfigView configView;
	private TamagochiView tamagochiView;
	private Tamagochi tamagochi;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Tamagochi Game");
		this.primaryStage.setResizable(false);
		this.primaryStage.setOnCloseRequest(e -> System.exit(0));
		tamagochiClient = new TamagochiClient(this);
		BaseView.setMainApp(this);
		configView = new ConfigView();
		tamagochiView = new TamagochiView();
		this.initRoot();
	}

	private void initRoot() {
		rootLayout = new BorderPane();
		Scene scene = new Scene(rootLayout, 980, 470);
		primaryStage.setScene(scene);
		primaryStage.show();
		this.setView(getConfigView());
	}

	public void appendToLogs(String message) {
		tamagochiView.receiveToServer(message);
	}

	public void startChatClient() throws IOException {
		getTamaClient().start();
	}

	public void setView(BaseView newView) {
		rootLayout.setCenter(newView.getView());
	}

	public UserConfig getUserCfg() {
		return userCfg;
	}

	public void setUserCfg(UserConfig userCfg) {
		this.userCfg = userCfg;
	}

	public Tamagochi getTamagochi() {
		if (this.tamagochi == null)
			this.tamagochi = new Tamagochi();
		return tamagochi;
	}

	public TamagochiClient getTamaClient() {
		return tamagochiClient;
	}

	public TamagochiView getChatView() {
		return tamagochiView;
	}

	public ConfigView getConfigView() {
		return configView;
	}

}