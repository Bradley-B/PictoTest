import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class NetworkHelperConnectThread implements Runnable {

	private Socket socket;
	private final String ip;
	private final int port;
	private final int timeoutMs;

	public NetworkHelperConnectThread(NetworkHelper helper, String ip, int port, int timeoutMs) {
		this.ip = ip;
		this.port = port;
		this.timeoutMs = timeoutMs;
	}

	public Socket tryConnection(String ip, int port, int timeoutMs) {
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(ip, port), timeoutMs);
			try{socket.close();} catch (IOException e) {}
			return socket;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void run() {
		socket = tryConnection(ip, port, timeoutMs);
		//System.out.println("I just scanned "+ip);
	}

	public Socket getSocket() {
		return socket;
	}
	
}