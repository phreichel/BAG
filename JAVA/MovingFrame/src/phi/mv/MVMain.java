//*************************************************************************************************
package phi.mv;
//*************************************************************************************************

import phi.mv.data.MVConfiguration;
import phi.mv.ui.MVConfigurationPanel;
import phi.mv.ui.MVControlPanel;
import phi.mv.ui.MVFrame;
import phi.mv.ui.MVMap;
import phi.mv.ui.MVMenuBar;

//*************************************************************************************************
public class MVMain {

	//=============================================================================================
	public static void main(String[] args) {
		
		if (!MVConfiguration.load()) return;
		
		MVMenuBar menuBar = new MVMenuBar();
		MVMap map = new MVMap();
		MVConfigurationPanel configurationPanel = new MVConfigurationPanel(map);
		MVControlPanel controlPanel = new MVControlPanel(configurationPanel);
		MVFrame frame = new MVFrame("MV Control Tool", menuBar, configurationPanel, controlPanel, map);
		frame.setVisible(true);
		
	}
	//=============================================================================================

}
//*************************************************************************************************
