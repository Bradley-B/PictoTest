import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class DrawingArea extends JPanel {
	private BufferedImage drawImage;
	private static final long serialVersionUID = -6796575090864175797L;
	int cursorX, cursorY;
	Font font = new Font("Comic Sans", Font.PLAIN, 20);
	
	public DrawingArea(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		drawImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(drawImage, 0, 0, this);
	}

	public void setTextCursor(int x, int y) {
		cursorX = x;
		cursorY = y;
	}
	
	public void drawText(String text, Color color) {
		if(text.equals("\b")) return;	
		Graphics graphics = drawImage.getGraphics();
		graphics.setColor(color);
		graphics.setFont(font);
		int textWidth = graphics.getFontMetrics().stringWidth(text);
		graphics.drawString(text, cursorX, cursorY);
		cursorX+=(textWidth);
		repaint();
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