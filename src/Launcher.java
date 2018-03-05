import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Launcher extends JFrame {

	private static final long serialVersionUID = 8111960529946411805L;
	public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	JButton scanBtn = new JButton("Search for available rooms");
	JButton serverBtn = new JButton("Start a new room");
	JLabel roomTextDisplay = new JLabel("");
	JPanel connectBtnPanel = new JPanel();
	JPanel namePanel = new JPanel();
	JLabel name = new JLabel("Name:");
	JTextField nameField = new JTextField("Player "+new Random().nextInt(9999), 10);

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(false);
		new Launcher();
	}

	/**
	 * Run a new Chatterbox instance and exit the old one.
	 */
	public static void reset() {
		try {
			final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
			File currentJar = new File(Launcher.class.getProtectionDomain().getCodeSource().getLocation().toURI());

			/* is it a jar file? */
			if(!currentJar.getName().endsWith(".jar"))
				throw new Exception("I don't know how to re-launch this");

			/* Build command: java -jar application.jar */
			final ArrayList<String> command = new ArrayList<String>();
			command.add(javaBin);
			command.add("-jar");
			command.add(currentJar.getPath());

			final ProcessBuilder builder = new ProcessBuilder(command);
			builder.start();
		} catch (Exception e) {e.printStackTrace();} finally {System.exit(0);}

	}

	/**
	 * The launcher for the program. Sets up the layout for all the components and shows the JFrame.
	 */
	public Launcher() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Chatterbox");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(scanBtn);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(serverBtn);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(roomTextDisplay);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(namePanel);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(connectBtnPanel);

		namePanel.add(name);
		namePanel.add(nameField);

		scanBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		serverBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		roomTextDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);

		Listener listener = new Listener();
		scanBtn.addActionListener(listener);
		serverBtn.addActionListener(listener);

		setVisible(true);
	}

	/**
	 * Start the program. If the serverIP is "localhost" then the program will launch as a server.
	 * @param serverIP the LAN IP address of the server to connect to
	 */
	public void launch(String serverIP) {
		ConnectionFrame connectionFrame = new ConnectionFrame();
		ClientFrame clientFrame = new ClientFrame(nameField.getText());
		Connection.createInstance(connectionFrame, clientFrame, serverIP);
		dispose();
	}

	/**
	 * Scan for servers to connect to on a specified port. Beware this is a blocking call, and the thread will wait until it is completed.
	 * @param port the port to try and connect to
	 * @return an array of the servers found
	 */
	public String[] scan(int port) {
		NetworkHelper nHelper = new NetworkHelper();
		Socket[] servers = nHelper.findServers(port);
		String[] serversStr = new String[servers.length];
		for(int i=0;i<servers.length;i++) { //TODO this stuff here could probably be done in NetworkHelper, as it isn't really relevant to Launcher.
			Socket socket = servers[i];
			String addr = socket.getRemoteSocketAddress().toString();
			serversStr[i] = addr.substring(1).substring(0, addr.indexOf(":")-1);
			//System.out.println(serversStr[i]);
		}
		return serversStr;
	}

	/**
	 * It's scan() and createConnectButtons(), but it's asynchronous.
	 * @param port the port to connect to
	 */
	public void asyncUpdate(int port) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				connectBtnPanel.removeAll();
				redraw();
				roomTextDisplay.setText("Scanning... this will take several seconds.");
				scanBtn.setEnabled(false);
				serverBtn.setEnabled(false);
				String[] servers = scan(port);
				createConnectButtons(servers);
				scanBtn.setEnabled(true);
				serverBtn.setEnabled(true);
				roomTextDisplay.setText("found "+servers.length+" available room(s)");
			}
		});
		thread.start();
	}

	/**
	 * Makes buttons for the user to connect to the available servers
	 * @param servers a list of the available servers to connect to
	 */
	public void createConnectButtons(String[] servers) {
		try {
			for(String server : servers) {
				JButton connectBtn = new JButton("Connect to "+InetAddress.getByName(server).getCanonicalHostName());
				connectBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						launch(server);
					}
				});
				connectBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
				connectBtnPanel.add(connectBtn);
				redraw();
			}
		} catch (UnknownHostException e) {}
	}

	/**
	 * Refresh the JPanel by revalidating it's components and repainting it.
	 */
	public void redraw() {
		revalidate();
		repaint();
	}

	/**
	 * Listens for two buttons: the scan button and the server button. It reacts to them accordingly, and does nothing else if added to a different button.
	 */
	private class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==scanBtn) {
				asyncUpdate(4450);
			}
			if(e.getSource()==serverBtn) {
				launch("localhost");
			}
		}
	}

}
