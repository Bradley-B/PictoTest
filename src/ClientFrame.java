import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientFrame extends JFrame {

	private static final long serialVersionUID = -2993583039552289024L;
	private int width = 600, height = 400;
	private Color penColor = Color.BLACK;
	private JPanel toolPanel = new JPanel();
	private DrawingPanel drawPanel;
	private Tool selectedTool = Tool.RAINBOW_PEN;
	private Clicked listener = new Clicked();
	private ToolButton rainbowPenBtn = new ToolButton(new ImageIcon("rainbowPen.png")), 
			customPenBtn = new ToolButton(new ImageIcon("blackPen.png")), 
			textBtn = new ToolButton(new ImageIcon("text.png")), 
			cameraBtn = new ToolButton(new ImageIcon("camera.png"));
	private static enum Tool {RAINBOW_PEN, TEXT, CUSTOM_PEN, CAMERA;}
	private Map<ToolButton, Tool> buttonMap = new LinkedHashMap<ToolButton, Tool>();

	public ClientFrame() {	
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setResizable(false);
		setLocation((int)Launcher.SCREEN_SIZE.width/3, (int)(Launcher.SCREEN_SIZE.height/2));
		setTitle("picto-test-client");
		setLayout(new BorderLayout());
		Input input = new Input(this);

		buttonMap.put(rainbowPenBtn, Tool.RAINBOW_PEN);
		buttonMap.put(customPenBtn, Tool.CUSTOM_PEN);
		buttonMap.put(textBtn, Tool.TEXT);

		toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
		toolPanel.setBackground(Color.WHITE);
		Set<ToolButton> buttons = buttonMap.keySet();
		for(ToolButton button : buttons) {
			toolPanel.add(button);
		}
		add(toolPanel, BorderLayout.EAST);

		drawPanel = new DrawingPanel(width-toolPanel.getWidth(), height);
		drawPanel.addMouseListener(input);
		drawPanel.addMouseMotionListener(input);
		drawPanel.addKeyListener(input);
		add(drawPanel, BorderLayout.CENTER);

		drawPanel.requestFocus();
	}

	public void drawText(String text) {
		drawPanel.drawText(text, Color.BLACK);
	}

	public void drawLeftMouse(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		switch (getSelectedTool()) {
			case RAINBOW_PEN:
				drawPanel.drawPoint(x, y,  new Color(Color.HSBtoRGB((float)Math.random(), 1f, 1f)));
				break;
			case CUSTOM_PEN: 
				drawPanel.drawPoint(x, y, penColor);
				break;
			case TEXT: 
				drawPanel.setTextCursor(x, y);
			default:
				break;
		}
	}

	public void drawRightMouse(MouseEvent e) {
		drawPanel.erasePoint(e.getX(), e.getY());
	}

	public DrawingPanel getDrawPanel() {
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
			setSelectedTool(buttonMap.get(e.getSource()));
			if(selectedTool==Tool.CUSTOM_PEN) {
				penColor = JColorChooser.showDialog(null, "Pick A Pen Color", penColor);
			}	
			drawPanel.requestFocus();	
		}
	}

}
