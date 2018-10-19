import java.io.InputStream;
import java.net.URL;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
					Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.OK);
					alert.setHeaderText("Time is up!");
					alert.setTitle("Done!");
					observer.done();
					playSound();
					alert.showAndWait();

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
