import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class DrawingPanel extends ImagePanel {
	private static final long serialVersionUID = -6796575090864175797L;
	int cursorX, cursorY;
	int clickCursorX, clickCursorY;
	String name;
	int pointSize = 10;
	
	public DrawingPanel(int width, int height, String name) {
		super(width, height);
		this.name = name;
		sign();
	}

	public void setTextCursor(int x, int y) {
		cursorX = x;
		cursorY = y;
		clickCursorX = cursorX;
		clickCursorY = cursorY;
	}
	
	public void addPointSize(int pixels) {
		pointSize += pixels;
	}
	
	public int getPointSize() {
		return pointSize;
	}
	
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
	
	public void drawPoint(int x, int y, Color color) {
		Graphics graphics = getImage().getGraphics();
		graphics.setColor(color);
		graphics.fillOval(x, y, pointSize, pointSize);
		repaint();
	}

	public void sign() {
		cursorX = 400;
		cursorY = 350;
		drawText("~"+name, Color.BLACK);
	}
	
	@Override
	public void clearImage() {
		super.clearImage();
		if(name!=null) {
			sign();			
		}
	}
	
	public void erasePoint(int x, int y) {
		drawPoint(x, y, Color.WHITE);
	}
}