import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 2078001331110271834L;
	private BufferedImage drawImage;
	
	public ImagePanel(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		drawImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		setBackground(Color.WHITE);
		clearImage();
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(drawImage, 0, 0, this);
	}
	
	public void clearImage() {
		Graphics graphics = getImage().getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, getImage().getWidth(), getImage().getHeight());
		repaint();
	}
	
	public BufferedImage getImage() {
		return drawImage;
	}
	
	public void setImage(BufferedImage image) {
		drawImage = image;
		repaint();
	}
}
