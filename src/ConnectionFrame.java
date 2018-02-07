
import javax.swing.JFrame;

public class ConnectionFrame extends JFrame {

	private static final long serialVersionUID = -7716427264572734984L;

	public ConnectionFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 400);
		this.setLocation(Launcher.SCREEN_SIZE.width/3, (Launcher.SCREEN_SIZE.height/8));
		this.setTitle("picto-test-remote");
		this.setVisible(true);
	}
}
