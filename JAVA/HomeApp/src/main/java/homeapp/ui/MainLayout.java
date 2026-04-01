//*****************************************************************************
package homeapp.ui;
//*****************************************************************************

//*****************************************************************************
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
//*****************************************************************************

//*****************************************************************************
public class MainLayout extends AppLayout {

	//=========================================================================
	private static final long serialVersionUID = 1L;
	//=========================================================================

	//=========================================================================
	public MainLayout() {

		Anchor homeAnchor = new Anchor("/");
		homeAnchor.add(VaadinIcon.HOME.create());
		homeAnchor.add(new Text("Home"));
		
		Anchor inventoryAnchor = new Anchor("/inv");
		inventoryAnchor.add(VaadinIcon.STORAGE.create());
		inventoryAnchor.add(new Text("Inventory"));
		
		Anchor nodeAnchor = new Anchor("/node");
		nodeAnchor.add(VaadinIcon.PACKAGE.create());
		nodeAnchor.add(new Text("Nodes"));
		
		MenuBar menuBar = new MenuBar();
		menuBar.addItem(homeAnchor);
		menuBar.addItem(inventoryAnchor);
		menuBar.addItem(nodeAnchor);
		
		this.addToNavbar(menuBar);
	}
	//=========================================================================
	
}
//*****************************************************************************
