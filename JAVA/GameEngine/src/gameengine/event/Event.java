//*****************************************************************************
package gameengine.event;
//*****************************************************************************

import java.util.EnumSet;
import java.util.Set;
import javax.vecmath.Vector2f;
import gameengine.widget.Widget;

//*****************************************************************************
public class Event {

	//=========================================================================	
	private Ident       ident           = Ident.ON_NONE;
	private Set<Aspect> aspectsConsumed = EnumSet.noneOf(Aspect.class);
	private Key         key             = Key.NONE;
	private char        character       = '\0';
	private Button      button          = Button.NONE;
	private int         clickCount      = 0;
	private Set<Key>    keysPressed     = EnumSet.noneOf(Key.class);
	private Set<Button> buttonsPressed  = EnumSet.noneOf(Button.class);
	private Vector2f    pointerLocation = new Vector2f();
	private Widget      origin          = null;
	private Widget      target          = null;
	private String      action          = null;
	//=========================================================================

	//=========================================================================
	public void setCommand(Ident ident) {
		clear();
		this.ident = ident;
	}
	//=========================================================================

	//=========================================================================
	public void setAction(Widget origin, String action) {
		clear();
		this.ident = Ident.ON_ACTION;
		this.origin = origin;
		this.action = action;
	}
	//=========================================================================
	
	//=========================================================================
	public boolean isAspectSet(Aspect aspect) {
		return aspectsConsumed.contains(aspect);
	}
	//=========================================================================

	//=========================================================================
	public void setAspect(Aspect aspect) {
		aspectsConsumed.add(aspect);
	}
	//=========================================================================

	//=========================================================================
	public void removeAspect(Aspect aspect) {
		aspectsConsumed.remove(aspect);
	}
	//=========================================================================
	
	//=========================================================================
	public void setKeyPressed(Key key) {
		clear();
		this.ident = Ident.ON_PLATFORM_KEY_PRESSED;
		this.key = key;
	}
	//=========================================================================

	//=========================================================================
	public void setKeyReleased(Key key) {
		clear();
		this.ident = Ident.ON_PLATFORM_KEY_RELEASED;
		this.key = key;
	}
	//=========================================================================

	//=========================================================================
	public void setKeyTyped(Key key, char character) {
		clear();
		this.ident = Ident.ON_PLATFORM_KEY_TYPED;
		this.key = key;
		this.character = character;
	}
	//=========================================================================

	//=========================================================================
	public void setPointerPressed(Button button) {
		clear();
		this.ident = Ident.ON_PLATFORM_POINTER_PRESSED;
		this.button = button;
	}
	//=========================================================================

	//=========================================================================
	public void setPointerReleased(Button button) {
		clear();
		this.ident = Ident.ON_PLATFORM_POINTER_RELEASED;
		this.button = button;
	}
	//=========================================================================

	//=========================================================================
	public void setPointerClicked(Button button, int count) {
		clear();
		this.ident = Ident.ON_PLATFORM_POINTER_CLICKED;
		this.button = button;
		this.clickCount = count;
	}
	//=========================================================================

	//=========================================================================
	public void setPointerMoved() {
		clear();
		this.ident = Ident.ON_PLATFORM_POINTER_MOVED;
	}
	//=========================================================================
	
	//=========================================================================
	public Ident getIdent() {
		return ident;
	}
	//=========================================================================

	//=========================================================================
	public Key getKey() {
		return key;
	}
	//=========================================================================

	//=========================================================================
	public char getCharacter() {
		return character;
	}
	//=========================================================================

	//=========================================================================
	public Button getButton() {
		return button;
	}
	//=========================================================================

	//=========================================================================
	public int getClickCount() {
		return clickCount;
	}
	//=========================================================================
	
	//=========================================================================
	public Vector2f getPointerLocation() {
		return pointerLocation;
	}
	//=========================================================================

	//=========================================================================
	public boolean isKeyPressed(Key key) {
		return keysPressed.contains(key); 
	}
	//=========================================================================
	
	//=========================================================================
	public Set<Key> keysPressed() {
		return keysPressed; 
	}
	//=========================================================================

	//=========================================================================
	public boolean isButtonPressed(Button button) {
		return buttonsPressed.contains(button); 
	}
	//=========================================================================
	
	//=========================================================================
	public Set<Button> buttonsPressed() {
		return buttonsPressed; 
	}
	//=========================================================================
	
	//=========================================================================
	public Widget getOrigin() {
		return origin;
	}
	//=========================================================================

	//=========================================================================
	public Widget getTarget() {
		return target;
	}
	//=========================================================================

	//=========================================================================
	public String getAction() {
		return action;
	}
	//=========================================================================
	
	//=========================================================================
	public void clear() {
		ident      = Ident.ON_NONE;
		key        = Key.NONE;
		character  = '\0';
		button     = Button.NONE;
		clickCount = 0;
		origin = null;
		target = null;
		pointerLocation.set(0, 0);
		aspectsConsumed.clear();
		keysPressed.clear();
		buttonsPressed.clear();
		action = null;
	}
	//=========================================================================

	//=========================================================================
	public void fillDefaults(EventPump eventPump) {
		this.pointerLocation.set(eventPump.getPointerLocation());
		this.keysPressed.addAll(eventPump.getKeysPressed());
		this.buttonsPressed.addAll(eventPump.getButtonsPressed());
	}
	//=========================================================================
	
}
//*****************************************************************************
