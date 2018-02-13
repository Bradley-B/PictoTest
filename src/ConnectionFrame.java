
import javax.swing.JFrame;

public class ConnectionFrame extends JFrame {

	private static final long serialVersionUID = -7716427264572734984L;

	public ConnectionFrame(boolean isServer) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLocation(Launcher.SCREEN_SIZE.width/3, (Launcher.SCREEN_SIZE.height/8));
		setTitle("picto-test-remote");
		setVisible(true);
	}
}
