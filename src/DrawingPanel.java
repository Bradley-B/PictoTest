import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends ImagePanel {
	private static final long serialVersionUID = -6796575090864175797L;
	int cursorX, cursorY;
	int clickCursorX, clickCursorY;
	String name;
	int pointSize = 10;
	final int WIDTH;
	final int HEIGHT;
	
	/**
	 * An <code>ImagePanel</code> that can be drawn on.
	 * @param width the width of the <code>BufferedImage</code>
	 * @param height the height of the <code>BufferedImage</code>
	 * @param name the name to sign the image with
	 */
	public DrawingPanel(int width, int height, String name) {
		super(width, height);
		this.name = name;
		this.WIDTH = width;
		this.HEIGHT = height;
		sign();
	}

	/**
	 * Set the location on screen on which to draw text
	 * @param x
	 * @param y
	 */
	public void setTextCursor(int x, int y) {
		cursorX = x;
		cursorY = y;
		clickCursorX = cursorX;
		clickCursorY = cursorY;
	}

	/**
	 * Increase the pen size
	 * @param pixels the size to increase by
	 */
	public void addPointSize(int pixels) {
		pointSize += pixels;
	}

	/**
	 * Get the current pen size
	 * @return the current pen size
	 */
	public int getPointSize() {
		return pointSize;
	}

	/**
	 * Draw text on screen at the current cursor location.
	 * @param text the text to draw
	 * @param color the color to draw the text in
	 */
	public void drawText(String text, Color color) {
		Graphics graphics = getImage().getGraphics();
		graphics.setColor(color);
		graphics.setFont(new Font("Comic Sans", Font.PLAIN, pointSize*2));

		if(text.equals("\n"))  {
			cursorY+= graphics.getFontMetrics().getHeight();
			cursorX = clickCursorX;
			return;
		} else if(text.equals("\b")) {
			return;
		}

		int textWidth = graphics.getFontMetrics().stringWidth(text);
		graphics.drawString(text, cursorX, cursorY);
		cursorX+=textWidth;
		repaint();
	}

	/**
	 * Draw an oval on screen at a specified location
	 * @param x the x-coordinate to draw the oval
	 * @param y the y-coordinate to draw the oval
	 * @param color the color to draw the oval
	 */
	public void drawPoint(int x, int y, Color color) {
		Graphics graphics = getImage().getGraphics();
		graphics.setColor(color);
		graphics.fillOval(x, y, pointSize, pointSize);
		repaint();
	}

	public void setPixelColor(int x, int y, Color color) {
		Graphics graphics = getImage().getGraphics();
		graphics.setColor(color);
		graphics.drawRect(x, y, 1, 1);
	}
	
	public Color getPixelColor(int x, int y) {
		int pixel = getImage().getRGB(x, y);
		return new Color((pixel & 0x00ff0000) >> 16, (pixel & 0x0000ff00) >> 8, pixel & 0x000000ff);
	}
	
	public void floodFill(int x, int y, Color replacementColor) {
		Color targetColor = getPixelColor(x, y);
		if(targetColor.equals(replacementColor)) return;
		List<Point> queue = new ArrayList<>();
		setPixelColor(x, y, replacementColor);
		queue.add(new Point(x, y));
		while(!queue.isEmpty()) {
			Point n = queue.get(0);
			queue.remove(0);
			if(n.x-1>0 && getPixelColor(n.x-1, n.y).equals(targetColor)) {
				setPixelColor(n.x-1, n.y, replacementColor);
				queue.add(new Point(n.x-1, n.y));
			}
			if(n.x<WIDTH-2 && getPixelColor(n.x+2, n.y).equals(targetColor)) {
				setPixelColor(n.x+2, n.y, replacementColor);
				queue.add(new Point(n.x+2, n.y));
			}
			if(n.y-1>0 && getPixelColor(n.x, n.y-1).equals(targetColor)) {
				setPixelColor(n.x, n.y-1, replacementColor);
				queue.add(new Point(n.x, n.y-1));
			}
			if(n.y<HEIGHT-2 && getPixelColor(n.x, n.y+2).equals(targetColor)) {
				setPixelColor(n.x, n.y+2, replacementColor);
				queue.add(new Point(n.x, n.y+2));
			}
		}
		repaint();
	}
	
	/**
	 * Sign the stored name onto the panel.
	 */
	public void sign() {
		Graphics graphics = getImage().getGraphics();
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("Comic Sans", Font.PLAIN, 15));
		graphics.drawString("-"+name, 400, 350);
		repaint();
	}

	/**
	 * Clears the image and then signs the stored name back onto the panel.
	 */
	@Override
	public void clearImage() {
		super.clearImage();
		if(name!=null) {
			sign();			
		}
	}

	/**
	 * Erase a point by coloring it with white.
	 * @param x the x-coordinate of the point to erase
	 * @param y the y-coordinate of the point to erase
	 */
	public void erasePoint(int x, int y) {
		drawPoint(x, y, Color.WHITE);
	}
}