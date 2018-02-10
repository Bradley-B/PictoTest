import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Launcher extends JFrame {
	
	private static final long serialVersionUID = 8111960529946411805L;
	public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	JButton scanBtn = new JButton("Search for available rooms");
	JLabel roomTextDisplay = new JLabel("");
	
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(false);
		new Launcher();
	}

	public Launcher() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("picto-test");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(scanBtn);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(roomTextDisplay);
		add(Box.createRigidArea(new Dimension(0, 20)));
		
		scanBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		roomTextDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Listener listener = new Listener();
		scanBtn.addActionListener(listener);
		
		setVisible(true);
	}
	
	public void launch(String serverIP) {
		reset();
		//setVisible(false);
		new ClientFrame();
		new ConnectionFrame();
	}
	
	public void reset() {
		dispose();
		new Launcher();
	}
	
	/**
	 * Scan for servers to connect to on a specified port. Beware this is a blocking call, and the thread will wait until it is completed.
	 * @param port the port to try and connect to
	 * @return an array of the servers found
	 */
	public String[] scan(int port) {
		NetworkHelper nHelper = new NetworkHelper();
		Socket[] servers = nHelper.findServers(4450);
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
	 * It's scan() and createConnectButtons(), but it's asynchronous. You're welcome.
	 * @param port
	 */
	public void asyncRoomUpdate(int port) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				roomTextDisplay.setText("Scanning... this will take several seconds.");
				scanBtn.setEnabled(false);
				String[] servers = scan(4450);
				createConnectButtons(servers);
				scanBtn.setEnabled(true);
				roomTextDisplay.setText("found "+servers.length+" available rooms(s)");
			}
		});
		thread.start();
	}
	
	public void createConnectButtons(String[] servers) {
		for(String server : servers) {
			JButton connectBtn = new JButton("Connect to "+server);
			connectBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					launch(server);
				}
			});
			connectBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(connectBtn);
			revalidate();
			repaint();
		}
	}
	
	private class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==scanBtn) {
				//TODO if scanBtn is hit we need to remove all the connectBtns currently in the room. No free lunches!
				asyncRoomUpdate(4450);
			}
		}
	}
	
}
