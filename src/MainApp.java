import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainApp extends Application implements Observer{

	
	@FXML TextField hoursText;
	@FXML TextField minsText;
	@FXML TextField secsText;
	@FXML Button startBtn;
	@FXML Button resetBtn;
	
	Timer bgTimer;
	TimerTask updateTimeTask;
	TimerModel tModel;
	
	enum TimerState { OFF, PAUSE, RUN};
	TimerState timerState = TimerState.RUN;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("gui.fxml"));
		loader.setController(this);
		Parent root = loader.load();
		
		
		tModel = new TimerModel(0, 20, 0);
		bgTimer = new Timer(true);
		updateTimeTask = new FxTimerTask(this, tModel);
		
		Scene myscene = new Scene(root);
		primaryStage.setScene(myscene);
		primaryStage.show();
		bgTimer.schedule(updateTimeTask, 200,1000);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML
	protected void handleStartBtn(ActionEvent e) {
		if(timerState == TimerState.RUN) {
			bgTimer.cancel();
			startBtn.setText("Resume");
			timerState = TimerState.PAUSE;
		}else if(timerState == TimerState.PAUSE) {
			bgTimer = new Timer();
			bgTimer.schedule(new FxTimerTask(this, tModel), 10, 1000);
			startBtn.setText("Pause");
			timerState = TimerState.RUN;
		}else if(timerState == TimerState.OFF) {
			bgTimer = new Timer();
			bgTimer.schedule(new FxTimerTask(this, tModel), 10, 1000);
			startBtn.setText("Pause");
			timerState = TimerState.RUN;
		}
	}
	public void update() {
		Platform.runLater( new Runnable() {
			
			@Override
			public void run() {
				hoursText.setText(String.format("%2d", tModel.getHours()));
				minsText.setText(String.format("%2d", tModel.getMins()));
				secsText.setText(String.format("%2d", tModel.getSecs()));
				
			}
		});
		
	}
}
