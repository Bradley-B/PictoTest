import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.PortUnreachableException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

public class NetworkHelper {

	public static final Object LOCK = new Object();
	
	public Socket[] findServers(int port) {
		ExecutorService threadPool = Executors.newFixedThreadPool(32);
		ArrayList<Future> tasks = new ArrayList<>();
		ArrayList<NetworkHelperConnectThread> threads = new ArrayList<>();
		
		for(String ip : getActiveIPv4Addresses()) {
			String subnet = ip.substring(0, ip.lastIndexOf(".")+1);
			for(int i=0;i<255;i++) {
				NetworkHelperConnectThread networkHelperConnectThread = new NetworkHelperConnectThread(this, subnet+i, port, 250);
				tasks.add(threadPool.submit(networkHelperConnectThread));
				threads.add(networkHelperConnectThread);
			}
		}

		while(!tasks.get(tasks.size()-1).isDone()) { //wait until all the possible server locations have been checked
			try {Thread.sleep(500);} catch (InterruptedException e) {}
		}
		
		ArrayList<Socket> connectedSockets = new ArrayList<>();
		for(NetworkHelperConnectThread n : threads) { //look at all the connect threads and see if any were successful
			Socket socket = n.getSocket();
			if(socket!=null) {
				connectedSockets.add(socket);
			}
		}
		
		return Arrays.copyOf(connectedSockets.toArray(), connectedSockets.toArray().length, Socket[].class);
	}
	
	private String[] getActiveIPv4Addresses() {
		ArrayList<String> addresses = new ArrayList<>();
		try {
			Enumeration<NetworkInterface> nEnumeration = NetworkInterface.getNetworkInterfaces();
			while(nEnumeration.hasMoreElements()) {
				NetworkInterface nInterface = nEnumeration.nextElement();
				if(!nInterface.isLoopback() && !nInterface.isVirtual() && nInterface.isUp()) {
					for (InterfaceAddress address : nInterface.getInterfaceAddresses()) {
						if(validateIP(address.getAddress().getHostAddress())) {
							addresses.add(address.getAddress().getHostAddress());
						}
					}    			
				}
			}
		} catch (SocketException e) {e.printStackTrace();}
		return Arrays.copyOf(addresses.toArray(), addresses.toArray().length, String[].class);
	}

	private boolean validateIP(final String ip) {
		return Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$").matcher(ip).matches();
	}

}
