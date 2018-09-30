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
	Timer bgTimer;
	TimerTask updateTimeTask;
	TimerModel tModel;
	ClockLabel timerLbl;

	Main() {
		this.setTitle("Pomdoro Timer");
		timerLbl = new ClockLabel();
		tModel = new TimerModel(0, 1, 20);

		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(timerLbl, BorderLayout.CENTER);
		this.setContentPane(contentPanel);

		startBtn = new JButton("Pause");
		JPanel southPanel = new JPanel();
		southPanel.add(startBtn);
		contentPanel.add(southPanel, BorderLayout.SOUTH);
		bgTimer = new Timer(true);

		updateTimeTask = new BackgroundTask(timerLbl, tModel);

		startBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				bgTimer.cancel();
				startBtn.setText("Resume");
			}
		});
		timerLbl.setTime(0, 0, 0);
		bgTimer.schedule(updateTimeTask, 200, 1000);

	}

	public static void main(String[] args) {
		JFrame main = new Main();
		main.setSize(300, 200);
		main.setVisible(true);
		main.setLocation(400, 400);
		main.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

}
