import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

interface Observer{
	void update();
	void done();
}

public class FxTimerTask extends TimerTask {
	Observer observer;
	TimerModel tModel;

	public FxTimerTask(Observer o, TimerModel model) {
		observer = o;
		tModel = model;
	}

	@Override
	public void run() {
		try {
			tModel.decrementSecond();
		} catch (TimerDone e) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.OK);
					alert.setTitle("Timer is done!");
					observer.done();
					alert.showAndWait();
					
				}
			});
			
//			e.printStackTrace();
		}
		observer.update();
		
	}

}
