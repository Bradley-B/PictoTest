import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Launcher extends JFrame {
	
	private static final long serialVersionUID = 8111960529946411805L;
	public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	JButton scanBtn = new JButton("Search for available rooms");
	JLabel roomTextDisplay = new JLabel();
	
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(false);
		new Launcher();
	}

	public Launcher() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("picto-test");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		add(scanBtn);
		add(roomTextDisplay);
		
		Listener listener = new Listener();
		scanBtn.addActionListener(listener);
		
		setVisible(true);
	}
	
	public void launch() {
		setVisible(false);
		new ClientFrame();
		new ConnectionFrame();
	}
	
	private class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==scanBtn) {
				//scan for rooms (servers) and display connection buttons
			}
		}
	}
	
}
