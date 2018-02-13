import java.awt.event.MouseMotionListener;
import java.util.Random;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener, MouseMotionListener {

	ClientFrame client;
	Random random = new Random();
	
	public Mouse(ClientFrame client) {
		this.client = client;
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseClicked(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1 || e.getModifiersEx()==MouseEvent.BUTTON1_DOWN_MASK) { //left click or hold click
			client.getDrawPanel().drawPoint(e.getX(), e.getY(), new Color(Color.HSBtoRGB(random.nextFloat(), 1f, 1f)));			
		} else if(e.getButton()==MouseEvent.BUTTON3 || e.getModifiersEx()==MouseEvent.BUTTON3_DOWN_MASK) {
			client.getDrawPanel().erasePoint(e.getX(), e.getY());
		}
		
		if(e.isControlDown()) {
			client.getDrawPanel().clearImage();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
