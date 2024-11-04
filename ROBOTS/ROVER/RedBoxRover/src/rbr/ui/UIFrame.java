//*************************************************************************************************
package rbr.ui;
//*************************************************************************************************

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

//*************************************************************************************************
public class UIFrame extends JFrame {

	//=============================================================================================
	private static final long serialVersionUID = 1L;
	//=============================================================================================

	//=============================================================================================
	private JTabbedPane tabPane;
	//=============================================================================================
	
	//=============================================================================================
	public UIFrame() {
		
		this.tabPane = new JTabbedPane();
		this.tabPane.addTab("Connection", new UIConnection());
		this.tabPane.addTab("Control", new JPanel());
		
		this.setTitle("Red Box Rover Control Panel");
		this.setMinimumSize(new Dimension(800, 600));
		this.getContentPane().add(tabPane, BorderLayout.CENTER);
		this.pack();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
	}
	//=============================================================================================

}
//*************************************************************************************************
