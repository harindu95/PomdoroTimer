import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClockLabel extends JPanel {

	List<JTextField> fields;
	public ClockLabel() {

		fields = new ArrayList<>();
		fields.add(new JTextField());
		fields.add(new JTextField());
		
		FlowLayout layout = new FlowLayout();
		setLayout(layout);
		layout.setVgap(30);
		Font f = new Font("Monospace", Font.TRUETYPE_FONT, 70);
		
		MouseListener l = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				JTextField source = (JTextField) arg0.getSource();
				source.setEditable(true);
				source.selectAll();

			}
		};
		FocusListener focusListener = new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				JTextField source = (JTextField) arg0.getSource();
				source.setEditable(false);
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		};

		ActionListener actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JTextField source = (JTextField) arg0.getSource();
				source.setEditable(false);
			}
		};
		int i = 0;
		for(JTextField field:fields) {
			
			field.setFont(f);
			field.setEditable(false);
			add(field);
			
			field.addActionListener(actionListener);
			field.addMouseListener(l);
			field.addFocusListener(focusListener);
			
			JLabel b = new JLabel(":");
			b.setFont(f);
			if(i != 1)
				add(b);
			i++;
		}
		
	}

	void setTime(int m, int s) {
		
		fields.get(0).setText(String.format("%02d", m));
		fields.get(1).setText(String.format("%02d", s));
	}

	int getMins() {
		return Integer.parseInt(fields.get(0).getText());
	}
	int getSecs() {
		return Integer.parseInt(fields.get(1).getText());
	}
}
