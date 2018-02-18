
import javax.swing.JFrame;

public class ConnectionFrame extends JFrame {

	private static final long serialVersionUID = -7716427264572734984L;
	int width = 600, height = 400;
	private ImagePanel imagePanel;
	
	public ConnectionFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocation(Launcher.SCREEN_SIZE.width/3, (Launcher.SCREEN_SIZE.height/8));
		setTitle("picto-test-remote");
		setVisible(true);
		
		imagePanel = new ImagePanel(width, height);
		add(imagePanel);	
	}

	public ImagePanel getImagePanel() {
		return imagePanel;
	}
	
	
}
