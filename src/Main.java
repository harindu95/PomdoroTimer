import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main extends JFrame {
	JLabel timerLbl;
	JPanel contentPanel;
	JButton startBtn;
	Timer timer;
	
	Main(){
		this.setTitle("Pomdoro Timer");
		timerLbl = new JLabel("00:20:00");
		timerLbl.setFont(new Font("Monospace", Font.TRUETYPE_FONT, 28));
		timerLbl.setHorizontalAlignment(JLabel.CENTER);
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(timerLbl, BorderLayout.CENTER );
		this.setContentPane(contentPanel);
		
		startBtn = new JButton("Start");
		JPanel southPanel = new JPanel();
		southPanel.add(startBtn);
		contentPanel.add(southPanel, BorderLayout.SOUTH);
		timer = new Timer(true);
//		timer.
	}
	
	public static void main(String[] args) {
		JFrame main = new Main();
		main.setSize(300, 200);
		main.setVisible(true);
		main.setLocation(400, 400);
		main.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

}
