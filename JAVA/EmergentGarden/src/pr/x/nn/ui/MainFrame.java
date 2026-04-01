//*****************************************************************************
package pr.x.nn.ui;
//*****************************************************************************

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

//*****************************************************************************
public class MainFrame extends JFrame implements WindowListener {

	//=========================================================================
	private static final long serialVersionUID = 1L;
	//=========================================================================

	//=========================================================================
	private MainCanvas canvas;
	private MainMenu   menu;
	//=========================================================================
	
	//=========================================================================
	public static MainFrame create() {
		return new MainFrame();
	}
	//=========================================================================
	
	//=========================================================================
	public MainFrame() {
		setDefaults();
	}
	//=========================================================================
	
	//=========================================================================
	void setDefaults() {
		addWindowListener(this);
		var title = Asset.msg("ui.mainframe.title");
		setTitle(title);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		menu = MainMenu.create();
		setJMenuBar(menu);
		canvas = MainCanvas.create();
		getContentPane().add(canvas, BorderLayout.CENTER);
		pack();
		setVisible(true);
	}
	//=========================================================================

	//=========================================================================
	@Override
	public void windowActivated(WindowEvent e) {
		System.out.println("Activated");
	}
	//=========================================================================

	//=========================================================================
	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("Closed");
	}
	//=========================================================================

	//=========================================================================
	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("Closing");
		setVisible(false);
		dispose();
	}
	//=========================================================================

	//=========================================================================
	@Override
	public void windowDeactivated(WindowEvent e) {
		System.out.println("Deactivated");
	}
	//=========================================================================

	//=========================================================================
	@Override
	public void windowDeiconified(WindowEvent e) {
		System.out.println("Deiconified");
	}
	//=========================================================================

	//=========================================================================
	@Override
	public void windowIconified(WindowEvent e) {
		System.out.println("Iconified");
	}
	//=========================================================================

	//=========================================================================
	@Override
	public void windowOpened(WindowEvent e) {
		System.out.println("Opened");
	}
	//=========================================================================
	
}
//*****************************************************************************
