import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.security.auth.x500.X500Principal;
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
	private ToolButton rainbowPenBtn = new ToolButton(new ImageIcon(ClientFrame.class.getResource("rainbowPen.png"))), 
			customPenBtn = new ToolButton(new ImageIcon(ClientFrame.class.getResource("blackPen.png"))), 
			textBtn = new ToolButton(new ImageIcon(ClientFrame.class.getResource("text.png"))), 
			sendBtn = new ToolButton(new ImageIcon(ClientFrame.class.getResource("send.png"))),
			paintBtn = new ToolButton(new ImageIcon(ClientFrame.class.getResource("eraser.png"))),
			backBtn = new ToolButton(new ImageIcon(ClientFrame.class.getResource("back.png"))),
			clearBtn = new ToolButton(new ImageIcon(ClientFrame.class.getResource("eraser.png"))),
			sizeUpBtn = new ToolButton(new ImageIcon(ClientFrame.class.getResource("eraser.png"))),
			sizeDownBtn = new ToolButton(new ImageIcon(ClientFrame.class.getResource("eraser.png")));

	private static enum Tool {RAINBOW_PEN, TEXT, CUSTOM_PEN, PAINT;}

	public ClientFrame(String name) {	
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Easel");
		setLayout(new BorderLayout());
		Input input = new Input(this);

		toolPanel.add(rainbowPenBtn);
		toolPanel.add(customPenBtn);
		toolPanel.add(textBtn);
		toolPanel.add(paintBtn);
		toolPanel.add(clearBtn);
		toolPanel.add(sizeUpBtn);
		toolPanel.add(sizeDownBtn);
		toolPanel.add(sendBtn);
		toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
		toolPanel.setBackground(Color.WHITE);
		add(toolPanel, BorderLayout.EAST);

		drawPanel = new DrawingPanel(width, height, name);
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
			break;
		case PAINT:
			drawPanel.floodFill(x, y, penColor);
			break;
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
			if(e.getSource()==rainbowPenBtn) {
				setSelectedTool(Tool.RAINBOW_PEN);
			} else if(e.getSource()==customPenBtn) {
				setSelectedTool(Tool.CUSTOM_PEN);
				penColor = JColorChooser.showDialog(null, "Pick A Color", penColor);
			} else if(e.getSource()==textBtn) {
				setSelectedTool(Tool.TEXT);
			} else if(e.getSource()==sendBtn) {
				drawPanel.sign();
				Connection.getInstance().deliverImage(drawPanel.getImage());
			} else if(e.getSource()==clearBtn) {
				getDrawPanel().clearImage();
			} else if(e.getSource()==backBtn) {
				Launcher.reset();
			} else if(e.getSource()==sizeUpBtn) {
				getDrawPanel().addPointSize(3);
				managePointSizeButtons();
			} else if(e.getSource()==sizeDownBtn) {
				getDrawPanel().addPointSize(-3);
				managePointSizeButtons();
			} else if(e.getSource()==paintBtn) {
				setSelectedTool(Tool.PAINT);
				penColor = JColorChooser.showDialog(null, "Pick A Color", penColor);
			}

			toolPanel.setBackground(penColor);
			drawPanel.requestFocus();	
		}

		public void managePointSizeButtons() {
			if(getDrawPanel().getPointSize()<=4) {
				sizeDownBtn.setEnabled(false);
			} else {
				sizeDownBtn.setEnabled(true);
			}
			if(getDrawPanel().getPointSize()>=25) {
				sizeUpBtn.setEnabled(false);
			} else {
				sizeUpBtn.setEnabled(true);
			}
		}
	}
}
