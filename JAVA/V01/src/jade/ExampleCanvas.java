//**************************************************************************************************
package jade;
//**************************************************************************************************

import jade.gui.Canvas;
import jade.gui.Flag;
import jade.gui.Widget;
import jade.gui.Action;
import jade.gui.Button;

//**************************************************************************************************
public class ExampleCanvas extends Canvas {

	//==============================================================================================
	public ExampleCanvas(Action action) {
		createGUI(action);
	}
	//==============================================================================================

	//==============================================================================================
	private void createGUI(Action action) {

		Button b1 = new Button();
		b1.setPosition(10, 10);
		b1.setSize(100, 20);
		b1.setAction(action);
		
		Widget w1 = new Widget();
		w1.setPosition(10, 10);
		w1.setSize(500, 200);
		w1.addChild(b1);

		Widget w2 = new Widget();
		w2.setPosition(520, 10);
		w2.setSize(200, 200);
		
		addChild(w1);
		addChild(w2);
		setFlag(Flag.DISPLAYED, false);
		
	}
	//==============================================================================================
	
}
//**************************************************************************************************
