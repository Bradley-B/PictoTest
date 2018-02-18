import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;

public class ConnectionInputListener extends Thread {
	Socket connection = null;
	ObjectInputStream inputStream;
	ObjectOutputStream outputStream;
	
	public ConnectionInputListener(Socket connection) {
		this.connection = connection;
		try {
			this.inputStream = new ObjectInputStream(connection.getInputStream());
			this.outputStream = new ObjectOutputStream(connection.getOutputStream());
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public ObjectOutputStream getOutputStream() {
		return outputStream;
	}
	
	public Socket getConnection() {
		return connection;
	}
	
	public BufferedImage toBufferedImage(ImageIcon icon) {
		BufferedImage bi = new BufferedImage(
			    icon.getIconWidth(),
			    icon.getIconHeight(),
			    BufferedImage.TYPE_INT_ARGB);
			Graphics g = bi.createGraphics();
			icon.paintIcon(null, g, 0,0);
			g.dispose();
		return bi;
	}
	
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted() && !connection.isClosed()) {
				Object object = inputStream.readObject();
				System.out.println("recieved object");
				if(object instanceof ImageIcon) {
					BufferedImage image = toBufferedImage(((ImageIcon) object));
					Connection.getInstance().broadcastImage(image);
				}
			}
		} catch (Exception e) {
			Connection.getInstance().removeConnection(this);
			try {connection.close(); inputStream.close();} catch (IOException e2) {e.printStackTrace();}
			System.out.println("connection to "+connection.getInetAddress()+" closed");
		}
	}
	
}
