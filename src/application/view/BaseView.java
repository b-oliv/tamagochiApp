package application.view;

import application.TamagochiApplication;
import javafx.scene.Parent;

public abstract class BaseView {

	private static TamagochiApplication mainApp = null;

	public abstract Parent getView();

	public static TamagochiApplication getMainApp() {
		if (mainApp == null) {
			try {
				throw new Exception("No reference to mainApp in BaseView.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mainApp;
	}

	public static void setMainApp(TamagochiApplication mainApp) {
		BaseView.mainApp = mainApp;
	}

}
