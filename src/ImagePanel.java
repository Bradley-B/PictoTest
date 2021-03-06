import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 2078001331110271834L;
	private BufferedImage drawImage;
	
	/**
	 * A JPanel that has a <code>BufferedImage</code> as its sole component.
	 * @param width the width of the <code>BufferedImage</code>
	 * @param height the height of the <code>BufferedImage</code>
	 */
	public ImagePanel(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		drawImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		setBackground(Color.WHITE);
		clearImage();
		repaint();
	}
	
	/**
	 * Paints the stored <code>BufferedImage</code> onto the JFrame. <code>repaint()</code> will call this method. 
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(drawImage, 0, 0, this);
	}
	
	/**
	 * Set the image to all white
	 */
	public void clearImage() {
		Graphics graphics = getImage().getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, getImage().getWidth(), getImage().getHeight());
		repaint();
	}
	
	/**
	 * Get the <code>BufferedImage</code> currently being displayed
	 * @return the image currently being displayed
	 */
	public BufferedImage getImage() {
		return drawImage;
	}
	
	/**
	 * Set the image currently being displayed
	 * @param image the image to set
	 */
	public void setImage(BufferedImage image) {
		drawImage = image;
		repaint();
	}
}
