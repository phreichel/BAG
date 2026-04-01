//*****************************************************************************
package pr.x.nn.ui;
//*****************************************************************************

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

//*****************************************************************************
public class MainMenu extends JMenuBar {

	//=========================================================================
	private static final long serialVersionUID = 1L;
	//=========================================================================

	//=========================================================================
	public static MainMenu create() {
		return new MainMenu();
	}
	//=========================================================================

	//=========================================================================
	public MainMenu() {
		setDefaults();
	}
	//=========================================================================
	
	//=========================================================================
	private void setDefaults() {
		
		var appItemNew    = new JMenuItem(Asset.msg("ui.mainmenu.app.new.label"));
		var appItemOpen   = new JMenuItem(Asset.msg("ui.mainmenu.app.open.label"));
		var appItemClose  = new JMenuItem(Asset.msg("ui.mainmenu.app.close.label"));
		var appItemSave   = new JMenuItem(Asset.msg("ui.mainmenu.app.save.label"));
		var appItemSaveAs = new JMenuItem(Asset.msg("ui.mainmenu.app.saveas.label"));
		var appItemQuit   = new JMenuItem(Asset.msg("ui.mainmenu.app.quit.label"));
		
		var appMenu = new JMenu(Asset.msg("ui.mainmenu.app.label"));
		appMenu.add(appItemNew);
		appMenu.add(appItemOpen);
		appMenu.add(appItemClose);
		appMenu.add(appItemSave);
		appMenu.add(appItemSaveAs);
		appMenu.addSeparator();
		appMenu.add(appItemQuit);
		add(appMenu);
		
	}
	//=========================================================================
	
}
//*****************************************************************************
