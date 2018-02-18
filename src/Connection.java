import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;

public class Connection {
	private static Connection instance = null;
	private ConnectionFrame connectionDisplay = null;
	private ClientFrame clientFrame = null;
	private List<ConnectionInputListener> clientConnections = Collections.synchronizedList(new ArrayList<ConnectionInputListener>(0));
	private ConnectionInputListener serverConnection = new ConnectionInputListener(new Socket());
	boolean isServer;

	private Connection(ConnectionFrame connectionDisplay, ClientFrame client, String serverIP) {
		this.connectionDisplay = connectionDisplay;
		this.clientFrame = client;

		isServer = serverIP.equals("localhost");
		if(isServer) {
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

			Thread acceptThread = new Thread(()->{
				try {
					ServerSocket serverSocket = new ServerSocket(4451);
					while(!Thread.interrupted()) {

						Socket socket = serverSocket.accept();
						ConnectionInputListener connectionInputListener = new ConnectionInputListener(socket);
						clientConnections.add(connectionInputListener);
						connectionInputListener.setDaemon(true);
						connectionInputListener.start();
					}
					serverSocket.close();
				} catch (Exception e) {e.printStackTrace();}
			});
			acceptThread.setDaemon(true);
			acceptThread.start();

		} else {
			try {serverConnection.getConnection().connect(new InetSocketAddress(serverIP, 4451), 10000);} catch (IOException e) {e.printStackTrace();}
			serverConnection.setDaemon(true);
			serverConnection.start();
		}
	}

	public void removeConnection(ConnectionInputListener connection) {
		clientConnections.remove(connection);
	}

	public void deliverImage(BufferedImage image) {
		System.out.println("delivering image");
		try {
			if(!isServer) {
				ImageIcon sendableImage = new ImageIcon(image);
				ObjectOutputStream out = new ObjectOutputStream(serverConnection.getConnection().getOutputStream());
				out.writeObject(sendableImage);
				out.flush();
			} else { //already here, don't need to deliver. broadcast.
				broadcastImage(image);
			}
		} catch (IOException e) {}
	}

	public void broadcastImage(BufferedImage image) {
		System.out.println("broadcasting image");
		ImageIcon sendableImage = new ImageIcon(image);
		try {
			if(isServer) {
				synchronized (clientConnections) {
					for(ConnectionInputListener connection : clientConnections) { //will be empty if not a server
						ObjectOutputStream out = new ObjectOutputStream(connection.getConnection().getOutputStream());
						out.writeObject(sendableImage);
						out.flush();
					}
				}
			}
		} catch (IOException e) {}
		connectionDisplay.getImagePanel().setImage(image);
	}

	public ClientFrame getClientFrame() {
		return clientFrame;
	}

	public ConnectionFrame getConnectionFrame() {
		return connectionDisplay;
	}

	public static Connection getInstance() {
		if(instance==null) throw new NullPointerException("there is no instance to get");
		return instance;
	}

	public static Connection createInstance(ConnectionFrame connectionDisplay, ClientFrame client, String serverIP) {
		if(instance==null) {
			instance = new Connection(connectionDisplay, client, serverIP);
		}
		return instance;
	}
}
