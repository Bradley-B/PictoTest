import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class DrawingPanel extends ImagePanel {
	private static final long serialVersionUID = -6796575090864175797L;
	int cursorX, cursorY;
	Font font = new Font("Comic Sans", Font.PLAIN, 20);
	
	public DrawingPanel(int width, int height) {
		super(width, height);
	}

	public void setTextCursor(int x, int y) {
		cursorX = x;
		cursorY = y;
	}
	
	public void drawText(String text, Color color) {
		if(text.equals("\b")) return;	
		Graphics graphics = getImage().getGraphics();
		graphics.setColor(color);
		graphics.setFont(font);
		int textWidth = graphics.getFontMetrics().stringWidth(text);
		graphics.drawString(text, cursorX, cursorY);
		cursorX+=(textWidth);
		repaint();
	}
	
	public void drawPoint(int x, int y, Color color) {
		Graphics graphics = getImage().getGraphics();
		graphics.setColor(color);
		graphics.fillOval(x, y, 10, 10);
		repaint();
	}

	public void erasePoint(int x, int y) {
		drawPoint(x, y, Color.WHITE);
	}
}