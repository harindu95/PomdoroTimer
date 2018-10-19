import java.util.TimerTask;

import javax.swing.SwingUtilities;

public class BackgroundTask extends TimerTask {
	ClockLabel clock;
	TimerModel tModel;

	public BackgroundTask(ClockLabel clock, TimerModel model) {
		this.clock = clock;
		this.tModel = model;
	}

	@Override
	public void run() {
		try {
			tModel.decrementSecond();
		} catch (TimerDone e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				clock.setTime(tModel.getMins(), tModel.getSecs());

			}
		});
	}

}
