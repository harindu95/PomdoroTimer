import java.util.TimerTask;

import javax.swing.SwingUtilities;

public class BackgroundTask extends TimerTask{
	Main main;
	public BackgroundTask(Main main) {
		this.main = main;
	}
	@Override
	public void run() {
		main.secs -= 1;
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				main.setTime(main.hours, main.mins, main.secs);
				
			}
		});
	}

}
