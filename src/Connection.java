import java.net.ServerSocket;
import java.net.Socket;

public class Connection {
	private static Connection instance = null;
	ConnectionFrame connectionDisplay = null;
	ClientFrame client = null;

	private Connection(ConnectionFrame connectionDisplay, ClientFrame client, boolean isServer) {
		this.connectionDisplay = connectionDisplay;
		this.client = client;

		Thread pingThread = new Thread(()->{
			try {
				ServerSocket serverSocket = new ServerSocket(4450);
				while(!Thread.interrupted()) {
					Socket socket = serverSocket.accept();
					socket.close();
				}
				serverSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		pingThread.setDaemon(true);
		pingThread.start();
	}

	public static Connection getInstance() {
		if(instance==null) throw new NullPointerException("there is no instance to get");
		return instance;
	}

	public static Connection createInstance(ConnectionFrame connectionDisplay, ClientFrame client, boolean isServer) {
		if(instance==null) {
			instance = new Connection(connectionDisplay, client, isServer);
		}
		return instance;
	}
}
