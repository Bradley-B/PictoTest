import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.BlockingDeque;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.IconUIResource;

public class ClientFrame extends JFrame {

	private static final long serialVersionUID = -2993583039552289024L;
	private int width = 600, height = 400;
	private JPanel toolPanel = new JPanel();
	private DrawingArea drawPanel;
	private Tool selectedTool = Tool.blackPen;
	private Clicked listener = new Clicked();
	private ToolButton rainbowPenBtn = new ToolButton(new ImageIcon("rainbowPen.png")), 
			blackPenBtn = new ToolButton(new ImageIcon("blackPen.png")), 
			textBtn = new ToolButton(new ImageIcon("text.png")), 
			redPenBtn = new ToolButton(new ImageIcon("redPen.png")), 
			cameraBtn = new ToolButton(new ImageIcon("camera.png"));
	
	private enum Tool {
		rainbowPen, text, blackPen, redPen, camera;
	}
	
	public ClientFrame() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setResizable(false);
		setLocation((int)Launcher.SCREEN_SIZE.width/3, (int)(Launcher.SCREEN_SIZE.height/2));
		setTitle("picto-test-client");
		setLayout(new BorderLayout());
		Mouse mouse = new Mouse(this);
		
		toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
		toolPanel.setBackground(Color.WHITE);
		toolPanel.add(rainbowPenBtn);
		toolPanel.add(blackPenBtn);
		add(toolPanel, BorderLayout.EAST);
		
		drawPanel = new DrawingArea(width-toolPanel.getWidth(), height);
		drawPanel.setBackground(Color.WHITE);
		drawPanel.addMouseListener(mouse);
		drawPanel.addMouseMotionListener(mouse);
		add(drawPanel, BorderLayout.CENTER);
				
		drawPanel.clearImage();
	}
	
	public DrawingArea getDrawPanel() {
		return drawPanel;
	}

	public Tool getSelectedTool() {
		return selectedTool;
	}
	
	public void setSelectedTool(Tool tool) {
		selectedTool = tool;
	}
	
	private class ToolButton extends JButton {
		private static final long serialVersionUID = 5266133043328726640L;
		public ToolButton(ImageIcon icon) {
			super(icon);
			addActionListener(listener);
			setPreferredSize(new Dimension(36, 36));
		}
	}
	
	private class Clicked implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
}
