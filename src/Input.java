import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Input implements MouseListener, MouseMotionListener, KeyListener {

	private ClientFrame client;
	
	public Input(ClientFrame client) {
		this.client = client;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(e.getModifiers()==16) {
			client.drawLeftMouse(e);
		} else if(e.getModifiers()==4) {
			client.drawRightMouse(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1) {
			client.drawLeftMouse(e);
		} else if(e.getButton()==MouseEvent.BUTTON3) {
			client.drawRightMouse(e);
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


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		client.drawText(""+e.getKeyChar());	
	}

}
