import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class DrawingArea extends JPanel {
	private BufferedImage drawImage;
	private static final long serialVersionUID = -6796575090864175797L;

	public DrawingArea(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		drawImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(drawImage, 0, 0, this);
	}

	public void drawPoint(int x, int y, Color color) {
		Graphics graphics = drawImage.getGraphics();
		graphics.setColor(color);
		graphics.fillOval(x, y, 10, 10);
		repaint();
	}

	public void clearImage() {
		Graphics graphics = drawImage.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, drawImage.getWidth(), drawImage.getHeight());
		repaint();
	}

	public void erasePoint(int x, int y) {
		drawPoint(x, y, Color.WHITE);
	}
}