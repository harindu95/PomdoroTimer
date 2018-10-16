import java.util.TimerTask;

interface Observer{
	void update();
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
		tModel.decrementSecond();
		observer.update();
	}

}
