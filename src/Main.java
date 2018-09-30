import java.awt.BorderLayout;
//import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {
//	JLabel timerLbl;
	JPanel contentPanel;
	JButton startBtn;
	JButton resetBtn;
	Timer bgTimer;
	TimerTask updateTimeTask;
	TimerModel tModel;
	ClockLabel timerLbl;
	
	enum TimerState { OFF, PAUSE, RUN};
	TimerState timerState = TimerState.OFF;

	Main() {
		this.setTitle("Pomdoro Timer");
		timerLbl = new ClockLabel();
		tModel = new TimerModel(0, 0, 0);

		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(timerLbl, BorderLayout.CENTER);
		this.setContentPane(contentPanel);

		startBtn = new JButton("Start");
		JPanel southPanel = new JPanel();
		southPanel.add(startBtn);
		contentPanel.add(southPanel, BorderLayout.SOUTH);
		bgTimer = new Timer(true);

		updateTimeTask = new BackgroundTask(timerLbl, tModel);
		
		resetBtn = new JButton("Reset");
		southPanel.add(resetBtn);
		resetBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				bgTimer.cancel();
				tModel.reset();
				timerLbl.setTime(0, 0, 0);
				timerState = TimerState.OFF;
				startBtn.setText("Start");
			}
		});

		startBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(timerState == TimerState.RUN) {
					bgTimer.cancel();
					startBtn.setText("Resume");
					timerState = TimerState.PAUSE;
				}else if(timerState == TimerState.PAUSE) {
					bgTimer = new Timer();
					bgTimer.schedule(new BackgroundTask(timerLbl, tModel), 10, 1000);
					startBtn.setText("Pause");
					timerState = TimerState.RUN;
				}else if(timerState == TimerState.OFF) {
					bgTimer = new Timer();
					bgTimer.schedule(new BackgroundTask(timerLbl, tModel), 10, 1000);
					tModel.setTime(timerLbl.getHours(), timerLbl.getMins(), timerLbl.getSecs());
					startBtn.setText("Pause");
					timerState = TimerState.RUN;
				}
			}
		});
		timerLbl.setTime(0, 0, 0);
//		bgTimer.schedule(updateTimeTask, 200, 1000);

	}

	public static void main(String[] args) {
		JFrame main = new Main();
		main.setSize(300, 200);
		main.setVisible(true);
		main.setLocation(400, 400);
		main.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

}
