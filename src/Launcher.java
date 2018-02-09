import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Launcher extends JFrame {
	
	private static final long serialVersionUID = 8111960529946411805L;
	public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	JButton scanBtn = new JButton("Search for available rooms");
	JLabel roomTextDisplay = new JLabel();
	
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(false);
		new Launcher();
	}

	public Launcher() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("picto-test");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		add(scanBtn);
		add(roomTextDisplay);
		
		Listener listener = new Listener();
		scanBtn.addActionListener(listener);
		
		setVisible(true);
	}
	
	public void launch() {
		setVisible(false);
		new ClientFrame();
		new ConnectionFrame();
	}
	
	/**
	 * Scan for servers to connect to on a specified port. Beware this is a blocking call, and the thread will wait until it is completed.
	 * @param port the port to try and connect to
	 * @return an array of the servers found.
	 */
	public String[] scan(int port) {
		NetworkHelper nHelper = new NetworkHelper();
		Socket[] servers = nHelper.findServers(4450);
		String[] serversStr = new String[servers.length];
		for(int i=0;i<servers.length;i++) {
			Socket socket = servers[i];
			String addr = socket.getRemoteSocketAddress().toString();
			serversStr[i] = addr.substring(1).substring(0, addr.indexOf(":")-1);
		}
		return serversStr;
	}
	
	private class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==scanBtn) {
				scan(4450);
				
				//launch();
			}
		}
	}
	
}
