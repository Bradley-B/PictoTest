import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientFrame extends JFrame {

	private static final long serialVersionUID = -2993583039552289024L;
	private DrawingArea drawPanel;
	private BufferedImage drawImage;
	
	public ClientFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLocation((int)Launcher.SCREEN_SIZE.width/3, (int)(Launcher.SCREEN_SIZE.height/2));
		setTitle("picto-test-client");
		setLayout(new FlowLayout());
		Mouse mouse = new Mouse(this);

		drawPanel = new DrawingArea();
		drawPanel.setBackground(Color.WHITE);
		drawPanel.setPreferredSize(new Dimension(600, 400));
		drawPanel.setVisible(true);
		drawPanel.addMouseListener(mouse);
		drawPanel.addMouseMotionListener(mouse);
		
		add(drawPanel);
		setVisible(true);
		
		drawImage = new BufferedImage(drawPanel.getWidth(), drawPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
		clearImage();
	}
	
	public void clearImage() {
		Graphics graphics = drawImage.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, drawImage.getWidth(), drawImage.getHeight());
		drawPanel.repaint();
	}
	
	public void erasePoint(int x, int y) {
		drawPoint(x, y, Color.WHITE);
	}
	
	public void drawPoint(int x, int y, Color color) {
		Graphics graphics = drawImage.getGraphics();
		graphics.setColor(color);
		graphics.fillOval(x, y, 10, 10);
		drawPanel.repaint();
	}
	
	private class DrawingArea extends JPanel {
		private static final long serialVersionUID = -6796575090864175797L;
	
		@Override
		protected void paintComponent(Graphics g) {
			g.drawImage(drawImage, 0, 0, this);
		}
	}
	
}
