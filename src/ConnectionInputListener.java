import java.awt.image.BufferedImage;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ConnectionInputListener extends Thread {
	Socket connection = null;
	
	public ConnectionInputListener(Socket connection) {
		this.connection = connection;
	}
	
	public Socket getConnection() {
		return connection;
	}
	
	@Override
	public void run() {
		try {
			ObjectInputStream inputStream = new ObjectInputStream(connection.getInputStream());
			while(!Thread.interrupted() && !connection.isClosed()) {
				Object object = inputStream.readObject();
				if(object instanceof BufferedImage) {
					BufferedImage image = (BufferedImage) object;
					Connection.getInstance().broadcastImage(image);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
