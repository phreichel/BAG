//*************************************************************************************************
package phi.mv.ui;
//*************************************************************************************************

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

//*************************************************************************************************
public class MVFrame extends JFrame {

	//=============================================================================================
	private static final long serialVersionUID = 1L;
	//=============================================================================================

	//=============================================================================================
	private JTabbedPane tabPanel;
	//=============================================================================================
	
	//=============================================================================================
	public MVFrame(
			String title,
			JMenuBar menuBar,
			JPanel configurationPanel,
			JPanel controlPanel,
			JPanel map) {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle(title);
		if (menuBar != null) this.setJMenuBar(menuBar);
		tabPanel = new JTabbedPane();
		tabPanel.addTab("Configuration", configurationPanel);
		tabPanel.addTab("Control", controlPanel);
		this.getContentPane().add(tabPanel, BorderLayout.WEST);
		this.getContentPane().add(map, BorderLayout.CENTER);
		this.pack();
	}
	//=============================================================================================

}
//*************************************************************************************************
