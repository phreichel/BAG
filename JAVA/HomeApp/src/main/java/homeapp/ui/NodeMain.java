//*****************************************************************************
package homeapp.ui;
//*****************************************************************************

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.html.HTMLTableElement;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import homeapp.sv.NodeDTA;
import homeapp.sv.NodeService;

//*****************************************************************************
@Route(registerAtStartup = true, value="/node", layout = MainLayout.class)
@CssImport("./styles.css")
public class NodeMain extends VerticalLayout {

	//=========================================================================
	private static final long serialVersionUID = 1L;
	//=========================================================================

	//=========================================================================
	private NodeService   nodeService;
	private List<NodeDTA> nodeList;
	//=========================================================================
	
	//=========================================================================
	public NodeMain(NodeService nodeService) {
		this.nodeService = nodeService;
		this.nodeList = new ArrayList<>(); 
		Paragraph headerText = new Paragraph("NODE MANAGEMENT");
		headerText.setMinWidth("100%");
		headerText.setClassName("homeapp-header");
		add(headerText);

		this.nodeService.getAll(nodeList);
		
		/*
		var body = UI.getCurrent().getElement();		
		HTMLTableElement table = body.  
		body.appendChild(null);
		*/
		
		var grid = new Grid<NodeDTA>(nodeList);
		grid.setWidth("55%");
		grid.setAllRowsVisible(true);
		grid.addItemClickListener(NodeMain::onItemClick);
		grid.addColumn(NodeMain::nodeIDRenderer)
			.setHeader("ID")
			.setWidth("10em");
		grid.addColumn(NodeMain::nodeLabelRenderer)
			.setHeader("LABEL")
			.setWidth("50em");
		grid.addColumn(NodeMain::nodeSerialRenderer)
			.setHeader("SERIAL NUMBER")
			.setWidth("20em");
		
		add(grid);

	}
	//=========================================================================

	//=========================================================================
	public static String nodeIDRenderer(NodeDTA node) {
		return "" + node.id;
	}
	//=========================================================================

	//=========================================================================
	public static String nodeLabelRenderer(NodeDTA node) {
		return node.label != null ? node.label : "n/a";
	}
	//=========================================================================

	//=========================================================================
	public static String nodeSerialRenderer(NodeDTA node) {
		return node.serialNumber != null ? node.serialNumber : "n/a";
	}
	//=========================================================================

	//=========================================================================
	public static void onItemClick(ItemClickEvent<NodeDTA> event) {
		var node = event.getItem();
		UI.getCurrent().navigate("/node/" + node.id);
	}
	//=========================================================================
	
}
//*****************************************************************************
	