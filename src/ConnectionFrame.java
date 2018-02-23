
import javax.swing.JFrame;

public class ConnectionFrame extends JFrame {

	private static final long serialVersionUID = -7716427264572734984L;
	int width = 600, height = 400;
	private ImagePanel imagePanel;
	
	public ConnectionFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setTitle("Room Dialog");
		setVisible(false);
		
		imagePanel = new ImagePanel(width, height);
		add(imagePanel);	
	}

	public ImagePanel getImagePanel() {
		return imagePanel;
	}
	
	
}
