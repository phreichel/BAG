	//**************************************************************************************************
package jade.gui;
//**************************************************************************************************

import javax.vecmath.Vector2f;

import jade.input.Button;
import jade.input.Key;
import jade.input.Keyboard;
import jade.input.Pointer;
import lombok.Getter;
import lombok.Setter;

//**************************************************************************************************
public class Event {

	//==============================================================================================
	@Getter @Setter private Ident ident = null;
	@Getter @Setter private Key key = null;
	@Getter @Setter private Button button = null;
	@Getter @Setter private Pointer pointer = null;
	@Getter @Setter private Keyboard keyboard = null;
	@Getter @Setter private Character keyChar = null;
	@Getter @Setter private Integer clickCount = null;
	@Getter private Vector2f localPointerPosition = new Vector2f();
	//==============================================================================================

	//==============================================================================================
	public Vector2f getLocalPointerPositon() {
		return localPointerPosition; 
	}
	//==============================================================================================
	
}
//**************************************************************************************************
