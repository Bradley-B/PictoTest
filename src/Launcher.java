import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Launcher {
	
	public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	private ClientFrame client;
	
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		Launcher launcher = new Launcher();
	}

	public Launcher() {
		this.client = new ClientFrame();
		//ConnectionFrame remote = new ConnectionFrame();
	}
	
}
