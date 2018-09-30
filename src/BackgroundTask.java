import java.util.TimerTask;

import javax.swing.SwingUtilities;

public class BackgroundTask extends TimerTask{
	Main main;
	TimerModel tModel;
	public BackgroundTask(Main main, TimerModel model) {
		this.main = main;
		this.tModel = model;
	}
	@Override
	public void run() {
		tModel.decrementSecond();
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				main.timerLbl.setText(tModel.toString());
				
			}
		});
	}

}
