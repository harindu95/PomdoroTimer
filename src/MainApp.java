

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.UnaryOperator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application implements Observer {

	@FXML
	TextField minsText;
	@FXML
	TextField secsText;
	@FXML
	Button startBtn;
	@FXML
	Button resetBtn;

	Timer bgTimer;
	TimerTask updateTimeTask;
	TimerModel tModel;
	Parent root;

	enum TimerState {
		OFF, PAUSED, RUNNING
	};

	TimerState timerState = TimerState.OFF;

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("gui2.fxml"));
		loader.setController(this);
		root = loader.load();

		tModel = Persistent.read();
		bgTimer = new Timer(true);
		updateTimeTask = new FxTimerTask(this, tModel);

		prepareTextField(minsText);
		prepareTextField(secsText);
		
		Scene myscene = new Scene(root);
		primaryStage.setScene(myscene);
		primaryStage.setTitle("Pomodoro Timer");
		primaryStage.getIcons().add(new Image(MainApp.class.getResourceAsStream("stopwatch.png")));
		primaryStage.show();

		update();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});
		
		myscene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if(event.getCode() == KeyCode.ENTER) {
					handleStartBtn(null);
				}
			}
			
		});;
	}

	public void addFocusListener(TextField text) {
		text.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newVal) {
				if (newVal) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if (text.isFocused() && !text.getText().isEmpty()) {
								text.selectAll();
								stopTimer();
							} else {

							}
						}
					});

				}
			}

		});
	}

	public void pauseTimer() {
		bgTimer.cancel();
		startBtn.setText("Resume");
		timerState = TimerState.PAUSED;
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Reset Timer(Cancel Timer)
	 * 
	 * @param e
	 */
	@FXML
	protected void handleResetBtn(ActionEvent e) {
		tModel = Persistent.read();
		stopTimer();
		update();
	}

	/**
	 * Stop Timer
	 */
	public void stopTimer() {
		bgTimer.cancel();
		startBtn.setText("Start");
		timerState = TimerState.OFF;
	}

	public void readInputFromGUI() {

		int m = 0;
		int s = 0;
		try {
			m = Integer.parseInt(minsText.getText());
			s = Integer.parseInt(secsText.getText());
		} catch (NumberFormatException e) {
			System.err.println("Number format exception!");

		}
		if (!tModel.isEqual(m, s)) {
			tModel.setTime(m, s);
			Persistent.save(tModel);
			stopTimer();
			System.out.println("Time: " + tModel.toString());
		}

	}

	public void prepareTextField(TextField text) {

		UnaryOperator<Change> modifyChange = c -> {
			if (c.isContentChange()) {
				int newLength = c.getControlNewText().length();
				String txt = c.getText();
				if (!txt.matches("[0-9]*")) {
					return null;
				} else if (newLength > 2) {
					return null;
				}
			}
			return c;
		};

		text.setTextFormatter(new TextFormatter<String>(modifyChange));
		text.setBackground(Background.EMPTY);
		text.setBorder(javafx.scene.layout.Border.EMPTY);
		text.setPadding(Insets.EMPTY);
		Font font = Font.font("Noto Mono", FontWeight.LIGHT, 70);

		text.setFont(font);
		text.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				handleStartBtn(arg0);
				root.requestFocus();

			}
		});
		addFocusListener(text);
	}

	public void resumeTimer() {
		bgTimer = new Timer();
		bgTimer.schedule(new FxTimerTask(this, tModel), 10, 1000);
		startBtn.setText("Pause");
		timerState = TimerState.RUNNING;
	}

	public void startTimer() {
		bgTimer = new Timer();
		bgTimer.schedule(new FxTimerTask(this, tModel), 10, 1000);
		startBtn.setText("Pause");
		timerState = TimerState.RUNNING;
	}

	@FXML
	protected void handleStartBtn(ActionEvent e) {
		readInputFromGUI();
		if (timerState == TimerState.RUNNING) {
			pauseTimer();
		} else if (timerState == TimerState.PAUSED) {
			resumeTimer();
		} else if (timerState == TimerState.OFF) {
			startTimer();
		}
	}

	public void done() {
		handleResetBtn(null);
	}

	/**
	 * Update view
	 */
	public void update() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				minsText.setText(String.format("%02d", tModel.getMins()));
				secsText.setText(String.format("%02d", tModel.getSecs()));

			}
		});

	}
}
