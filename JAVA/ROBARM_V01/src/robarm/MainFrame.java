//**************************************************************************************************
package robarm;
//**************************************************************************************************

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

//**************************************************************************************************
public class MainFrame extends JFrame implements WindowListener {

	//==============================================================================================
	private static final long serialVersionUID = 1L;
	//==============================================================================================

	//==============================================================================================
	public MainFrame() {
		super();
		setup();
	}
	//==============================================================================================

	//==============================================================================================
	private final JMenuBar setupMenuBar() {
		
		var miSystemQuit = new JMenuItem("Quit");
		miSystemQuit.addActionListener(this::doQuitAction);
		
		var smSystem = new JMenu("System");
		smSystem.add(miSystemQuit);

		var mb = new JMenuBar();
		mb.add(smSystem);
		
		return mb;
	}
	//==============================================================================================
	
	//==============================================================================================
	private final void setup() {
		setTitle("ROBARM-V01 - Manager");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		var menuBar = setupMenuBar();
		setJMenuBar(menuBar);
	}
	//==============================================================================================
	
	//==============================================================================================
	@Override
	public void windowClosing(WindowEvent e) {
		doQuitAction(null);
	}
	//==============================================================================================
	
	//==============================================================================================
	@Override public void windowOpened(WindowEvent e) {}
	@Override public void windowClosed(WindowEvent e) {}
	@Override public void windowIconified(WindowEvent e) {}
	@Override public void windowDeiconified(WindowEvent e) {}
	@Override public void windowActivated(WindowEvent e) {}
	@Override public void windowDeactivated(WindowEvent e) {}
	//==============================================================================================

	//==============================================================================================
	private void doQuitAction(ActionEvent e) {
		int result = JOptionPane.showConfirmDialog(this, "Do you really want to quit?");
		if (result == JOptionPane.OK_OPTION) {
			setVisible(false);
		}
	}
	//==============================================================================================
	
}
//**************************************************************************************************

