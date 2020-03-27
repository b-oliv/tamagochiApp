package application.view;

import application.TamagochiApplication;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class TamagochiView extends BaseView {

	private AnchorPane anchorPane = null;
	private HBox hbox;
	private HBox hbox2;
	private TextField age;
	private TextField locate;
	private TextField satiety;
	private TextField emotion;
	private TextArea logs;
	private Button feed;
	private Button play;
	private Boolean isAlive = true;

	private TamagochiApplication mainApp = BaseView.getMainApp();

	private EventHandler<MouseEvent> onClickFeed = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			System.err.println(event);
			String message = "MANGE" + "\n";
			mainApp.getTamaClient().sendMessage(message);
			locate.setText("Cuisine");
			satiety.setText("Repu");
			event.consume();
		}
	};

	private EventHandler<MouseEvent> onClickPlay = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			System.err.println(event);
			String message = "PLAY" + "\n";
			mainApp.getTamaClient().sendMessage(message);
			locate.setText("Salon");
			emotion.setText("Content");
			event.consume();
		}
	};

	public void receiveToServer(String message) {
		boolean isSec = isNumber(message);
		if (isSec && isAlive.equals(true)) {
			age.setText(message + " sec");
		} else {
			update(message);
		}
	}

	public boolean isNumber(String chaine) {
		try {
			Integer.parseInt(chaine);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	private void update(String message) {
		String time = "[" + age.getText() + "]-";
		String name = "<" + mainApp.getUserCfg().getTamagochiname() + ">";
		if (isAlive.equals(true)) {
			if (message.contains("FAIM")) {
				satiety.setText("Faim");
				logs.appendText(
						time + name + " a faim !!! Il risque de mourir dans 10 secondes." + System.lineSeparator());
			} else if (message.contains("ENNUIE")) {
				emotion.setText("Triste");
				logs.appendText(time + name + " s'ennuir !!! Tu peux jouer avec lui ?!" + System.lineSeparator());
			} else if (message.contains("DEAD")) {
				satiety.setText("-");
				emotion.setText("-");
				feed.setVisible(false);
				play.setVisible(false);
				isAlive = false;
				logs.appendText(time + name + " est mort!!!" + System.lineSeparator());
				mainApp.getTamaClient().sendMessage("DEAD" + "\n");
			} else {
				logs.appendText(age.getText() + "" + name + "" + message + System.lineSeparator());
			}
		}
	}

	public AnchorPane getView() {
		if (anchorPane == null)
			this.createView();
		return anchorPane;
	}

	private void createView() {
		anchorPane = new AnchorPane();
		hbox = new HBox(10);

		Label label = new Label("Age");
		age = new TextField();
		age.setText("0 sec");
		age.setEditable(false);
		hbox.getChildren().add(label);
		hbox.getChildren().add(age);

		label = new Label("Lieu");
		locate = new TextField();
		locate.setText("Salon");
		locate.setEditable(false);
		hbox.getChildren().add(label);
		hbox.getChildren().add(locate);

		label = new Label("Satiété");
		satiety = new TextField();
		satiety.setText("tranquille");
		satiety.setEditable(false);
		hbox.getChildren().add(label);
		hbox.getChildren().add(satiety);

		label = new Label("Emotion");
		emotion = new TextField();
		emotion.setText("Sage");
		emotion.setEditable(false);
		emotion.setAlignment(Pos.CENTER);
		hbox.getChildren().add(label);
		hbox.getChildren().add(emotion);

		AnchorPane.setTopAnchor(hbox, 10.0);
		AnchorPane.setLeftAnchor(hbox, 10.0);
		AnchorPane.setRightAnchor(hbox, 10.0);

		anchorPane.getChildren().add(hbox);

		logs = new TextArea();
		logs.setFocusTraversable(false);
		logs.setEditable(false);
		logs.setMinHeight(230);
		logs.setWrapText(true);

		AnchorPane.setTopAnchor(logs, 50.0);
		AnchorPane.setRightAnchor(logs, 20.0);
		AnchorPane.setLeftAnchor(logs, 20.0);

		anchorPane.getChildren().add(logs);

		hbox2 = new HBox(10);

		feed = new Button("Manger");
		hbox2.getChildren().add(feed);

		play = new Button("Jouer");
		hbox2.getChildren().add(play);

		AnchorPane.setTopAnchor(hbox2, 400.0);
		AnchorPane.setRightAnchor(hbox2, 20.0);
		AnchorPane.setLeftAnchor(hbox2, 20.0);
		anchorPane.getChildren().add(hbox2);

		// EVENT HANDLER
		feed.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickFeed);
		play.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickPlay);
	}

}
