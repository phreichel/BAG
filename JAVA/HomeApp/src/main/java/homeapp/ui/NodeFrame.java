//*****************************************************************************
package homeapp.ui;
//*****************************************************************************

//*****************************************************************************
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
//*****************************************************************************

//*****************************************************************************
@Route(registerAtStartup = true, value="/node/:nodeid", layout = MainLayout.class)
@CssImport("./styles.css")
public class NodeFrame extends VerticalLayout implements BeforeEnterObserver {

	//=========================================================================
	private static final long serialVersionUID = 1L;
	//=========================================================================

	//=========================================================================
	private TextField nodeIDComponent = new TextField("Node ID");
	//=========================================================================
	
	//=========================================================================
	public NodeFrame() {
		Paragraph headerText = new Paragraph("NODE VIEW");
		headerText.setMinWidth("100%");
		headerText.setClassName("homeapp-header");
		add(headerText);
		add(nodeIDComponent);
	}
	//=========================================================================

	//=========================================================================
	public void beforeEnter(BeforeEnterEvent event) {
        String nodeIdString = event
        		.getRouteParameters()
        		.get("nodeid")
        		.orElse("NOID");
        nodeIDComponent.setValue(nodeIdString);
    }	
	//=========================================================================
}
//*****************************************************************************
	