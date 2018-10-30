import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.InputStream;
import java.net.URL;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.controlsfx.control.Notifications;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.control.Alert.AlertType;

interface Observer {
	void update();

	void done();
}

public class FxTimerTask extends TimerTask {
	Observer observer;
	TimerModel tModel;
	MediaPlayer mediaPlayer;

	public FxTimerTask(Observer o, TimerModel model) {
		observer = o;
		tModel = model;
		URL file = getClass().getResource("alarm.wav");
		final Media media = new Media(file.toString());
		mediaPlayer = new MediaPlayer(media);
	}
	
	public Stage createDummyStage() {
	    Stage dummyPopup = new Stage();
	    dummyPopup.initModality(Modality.NONE);
	    // set as utility so no iconification occurs
	    dummyPopup.initStyle(StageStyle.UTILITY);
	    // set opacity so the window cannot be seen
	    dummyPopup.setOpacity(0d);
	    // not necessary, but this will move the dummy stage off the screen
	    final Screen screen = Screen.getPrimary();
	    final Rectangle2D bounds = screen.getVisualBounds();
	    dummyPopup.setX(bounds.getMaxX());
	    dummyPopup.setY(bounds.getMaxY());
	    // create/add a transparent scene
	    final Group root = new Group();
	    dummyPopup.setScene(new Scene(root, 1d, 1d, Color.TRANSPARENT));
	    // show the dummy stage
	    dummyPopup.show();
	    return dummyPopup;
	}

	@Override
	public void run() {
		try {
			tModel.decrementSecond();
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					mediaPlayer.stop();
				}
			});
		} catch (TimerDone e) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
//					Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.OK);
//					alert.setHeaderText("Time is up!");
//					alert.setTitle("Done!");
//					alert.showAndWait();

					observer.done();

					Stage stage = (Stage) ((MainApp) observer).root.getScene().getWindow();				    			   
					stage.toFront();
									
					playSound();
				
					
					Notifications n = Notifications.create();
					n.owner(createDummyStage());
					n.title("Pomodoro Timer").text("Time is up.").showInformation();
				

				}
			});

//			e.printStackTrace();
		}
		observer.update();

	}

	public void playSound() {
		mediaPlayer.stop();
		mediaPlayer.setVolume(100);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setStopTime(Duration.seconds(5));
		mediaPlayer.setOnError(() -> System.out.println("Error : " + mediaPlayer.getError().toString()));
		mediaPlayer.setOnEndOfMedia(() -> {
			mediaPlayer.play();
		});
		mediaPlayer.play();
	}

}
