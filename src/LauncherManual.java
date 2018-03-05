import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class LauncherManual extends Launcher {
	 
	private static final long serialVersionUID = -2559568882737631976L;
	
	public LauncherManual() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Chatterbox");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(0, 10)));
	
		add(namePanel);
		add(Box.createRigidArea(new Dimension(0, 20)));

		namePanel.add(name);
		namePanel.add(nameField);

		setVisible(true);
	}

}
