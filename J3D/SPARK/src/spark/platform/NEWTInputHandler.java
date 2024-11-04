//*********************************************************************************************************************
package spark.platform;
//*********************************************************************************************************************

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

//*********************************************************************************************************************
class NEWTInputHandler implements Input, MouseListener, KeyListener {

	//=================================================================================================================
	private InputHandler handler;
	//=================================================================================================================

	//=================================================================================================================
	private Map<Axis, Float> axes;
	private EnumSet<Toggle> toggles;
	//=================================================================================================================

	//=================================================================================================================
	public NEWTInputHandler() {
		axes = new HashMap<>();
		toggles = EnumSet.noneOf(Toggle.class);
	}
	//=================================================================================================================
	
	//=================================================================================================================
	public void keyPressed(KeyEvent e) {
		Toggle toggle = mapKey(e.getKeySymbol());
		char key = e.isPrintableKey() ? e.getKeyChar() : '\0';
		if (!e.isAutoRepeat()) {
			if (toggle != null) toggles.add(toggle);
			handle(Action.TOGGLE_ARMED, toggle, key, 0);
		}
		if (key != '\0') {
			handle(Action.KEY_TYPED, toggle, key, 0);
		}
	}
	//=================================================================================================================

	//=================================================================================================================
	public void keyReleased(KeyEvent e) {
		if (!e.isAutoRepeat()) {
			Toggle toggle = mapKey(e.getKeySymbol());
			if (toggle != null) toggles.remove(toggle);
			char key = e.isPrintableKey() ? e.getKeyChar() : '\0';
			handle(Action.TOGGLE_RELEASED, toggle, key, 0);
		}
	}
	//=================================================================================================================

	//=================================================================================================================
	public void mouseClicked(MouseEvent e) {
		mapPointerAxes(e);
		Toggle toggle = mapPointerButton(e.getButton());
		if (toggle != null) toggles.add(toggle);		
		handle(Action.POINTER_CLICKED, toggle, '\0', e.getClickCount());
	}
	//=================================================================================================================

	//=================================================================================================================
	public void mouseEntered(MouseEvent e) {
		mapPointerAxes(e);
		handle(Action.POINTER_MOVED, null, '\0', 0);
	}
	//=================================================================================================================

	//=================================================================================================================
	public void mouseExited(MouseEvent e) {
		mapPointerAxes(e);
		handle(Action.POINTER_MOVED, null, '\0', 0);
	}
	//=================================================================================================================

	//=================================================================================================================
	public void mousePressed(MouseEvent e) {
		mapPointerAxes(e);
		if (!e.isAutoRepeat()) {
			Toggle toggle = mapPointerButton(e.getButton());
			if (toggle != null) toggles.add(toggle);
			handle(Action.TOGGLE_ARMED, toggle, '\0', 0);
		}
	}
	//=================================================================================================================

	//=================================================================================================================
	public void mouseReleased(MouseEvent e) {
		mapPointerAxes(e);
		if (!e.isAutoRepeat()) {
			Toggle toggle = mapPointerButton(e.getButton());
			if (toggle != null) toggles.remove(toggle);
			handle(Action.TOGGLE_RELEASED, toggle, '\0', 0);
		}
	}
	//=================================================================================================================

	//=================================================================================================================
	public void mouseMoved(MouseEvent e) {
		mapPointerAxes(e);
		handle(Action.POINTER_MOVED, null, '\0', 0);
	}
	//=================================================================================================================

	//=================================================================================================================
	public void mouseDragged(MouseEvent e) {
		mapPointerAxes(e);
		handle(Action.POINTER_MOVED, null, '\0', 0);
	}
	//=================================================================================================================

	//=================================================================================================================
	public void mouseWheelMoved(MouseEvent e) {
		mapPointerAxes(e);
		handle(Action.POINTER_WHEEL, null, '\0', 0);
	}
	//=================================================================================================================

	//=================================================================================================================
	public InputHandler input() {
		return handler;
	}
	//=================================================================================================================

	//=================================================================================================================
	public void input(InputHandler handler) {
		this.handler = handler;
	}
	//=================================================================================================================

	//=================================================================================================================
	private void mapPointerAxes(MouseEvent e) {
		float x = e.getX();
		float y = e.getY();
		float[] rot = e.getRotation();
		float rotscale = e.getRotationScale();
		axes.put(Axis.POINTER_X, x);
		axes.put(Axis.POINTER_Y, y);
		axes.put(Axis.POINTER_ROTATION_HORIZONTAL, rot[0]);
		axes.put(Axis.POINTER_ROTATION_VERTICAL, rot[1]);
		axes.put(Axis.POINTER_ROTATION_Z, rot[2]);
		axes.put(Axis.POINTER_ROTATION_SCALE, rotscale);
	}
	//=================================================================================================================
	
	//=================================================================================================================
	private Toggle mapKey(int key) {
		switch (key) {
		case KeyEvent.VK_ESCAPE: return Toggle.KEYBOARD_ESCAPE;
		case KeyEvent.VK_F1: return Toggle.KEYBOARD_F1;
		case KeyEvent.VK_F2: return Toggle.KEYBOARD_F2;
		case KeyEvent.VK_F3: return Toggle.KEYBOARD_F3;
		case KeyEvent.VK_F4: return Toggle.KEYBOARD_F4;
		case KeyEvent.VK_F5: return Toggle.KEYBOARD_F5;
		case KeyEvent.VK_F6: return Toggle.KEYBOARD_F6;
		case KeyEvent.VK_F7: return Toggle.KEYBOARD_F7;
		case KeyEvent.VK_F8: return Toggle.KEYBOARD_F8;
		case KeyEvent.VK_F9: return Toggle.KEYBOARD_F9;
		case KeyEvent.VK_F10: return Toggle.KEYBOARD_F10;
		case KeyEvent.VK_F11: return Toggle.KEYBOARD_F11;
		case KeyEvent.VK_F12: return Toggle.KEYBOARD_F12;
		case KeyEvent.VK_PRINTSCREEN: return Toggle.KEYBOARD_PRINT_SCREEN;
		case KeyEvent.VK_SCROLL_LOCK: return Toggle.KEYBOARD_SCROLL_LOCK;
		case KeyEvent.VK_PAUSE: return Toggle.KEYBOARD_PAUSE;
		case KeyEvent.VK_1: return Toggle.KEYBOARD_1;
		case KeyEvent.VK_2: return Toggle.KEYBOARD_2;
		case KeyEvent.VK_3: return Toggle.KEYBOARD_3;
		case KeyEvent.VK_4: return Toggle.KEYBOARD_4;
		case KeyEvent.VK_5: return Toggle.KEYBOARD_5;
		case KeyEvent.VK_6: return Toggle.KEYBOARD_6;
		case KeyEvent.VK_7: return Toggle.KEYBOARD_7;
		case KeyEvent.VK_8: return Toggle.KEYBOARD_8;
		case KeyEvent.VK_9: return Toggle.KEYBOARD_9;
		case KeyEvent.VK_0: return Toggle.KEYBOARD_0;
		case KeyEvent.VK_BACK_SPACE: return Toggle.KEYBOARD_BACK_SPACE;
		case KeyEvent.VK_TAB: return Toggle.KEYBOARD_TAB;
		case KeyEvent.VK_Q: return Toggle.KEYBOARD_Q;
		case KeyEvent.VK_W: return Toggle.KEYBOARD_W;
		case KeyEvent.VK_E: return Toggle.KEYBOARD_E;
		case KeyEvent.VK_R: return Toggle.KEYBOARD_R;
		case KeyEvent.VK_T: return Toggle.KEYBOARD_T;
		case KeyEvent.VK_Z: return Toggle.KEYBOARD_Z;
		case KeyEvent.VK_U: return Toggle.KEYBOARD_U;
		case KeyEvent.VK_I: return Toggle.KEYBOARD_I;
		case KeyEvent.VK_O: return Toggle.KEYBOARD_O;
		case KeyEvent.VK_P: return Toggle.KEYBOARD_P;
		case KeyEvent.VK_CAPS_LOCK: return Toggle.KEYBOARD_CAPS_LOCK;
		case KeyEvent.VK_A: return Toggle.KEYBOARD_A;
		case KeyEvent.VK_S: return Toggle.KEYBOARD_S;
		case KeyEvent.VK_D: return Toggle.KEYBOARD_D;
		case KeyEvent.VK_F: return Toggle.KEYBOARD_F;
		case KeyEvent.VK_G: return Toggle.KEYBOARD_G;
		case KeyEvent.VK_H: return Toggle.KEYBOARD_H;
		case KeyEvent.VK_J: return Toggle.KEYBOARD_J;
		case KeyEvent.VK_K: return Toggle.KEYBOARD_K;
		case KeyEvent.VK_L: return Toggle.KEYBOARD_L;
		case KeyEvent.VK_ENTER: return Toggle.KEYBOARD_ENTER;
		case KeyEvent.VK_SHIFT: return Toggle.KEYBOARD_SHIFT;
		case KeyEvent.VK_Y: return Toggle.KEYBOARD_Y;
		case KeyEvent.VK_X: return Toggle.KEYBOARD_X;
		case KeyEvent.VK_C: return Toggle.KEYBOARD_C;
		case KeyEvent.VK_V: return Toggle.KEYBOARD_V;
		case KeyEvent.VK_B: return Toggle.KEYBOARD_B;
		case KeyEvent.VK_N: return Toggle.KEYBOARD_N;
		case KeyEvent.VK_M: return Toggle.KEYBOARD_M;
		case KeyEvent.VK_CONTROL: return Toggle.KEYBOARD_CONTROL;
		case KeyEvent.VK_WINDOWS: return Toggle.KEYBOARD_SYSTEM;
		case KeyEvent.VK_ALT: return Toggle.KEYBOARD_ALT;
		case KeyEvent.VK_SPACE: return Toggle.KEYBOARD_SPACE;
		case KeyEvent.VK_CONTEXT_MENU: return Toggle.KEYBOARD_MENU;
		case KeyEvent.VK_INSERT: return Toggle.KEYBOARD_INSERT;
		case KeyEvent.VK_DELETE: return Toggle.KEYBOARD_DELETE;
		case KeyEvent.VK_HOME: return Toggle.KEYBOARD_HOME;
		case KeyEvent.VK_END: return Toggle.KEYBOARD_END;
		case KeyEvent.VK_PAGE_UP: return Toggle.KEYBOARD_PAGE_UP;
		case KeyEvent.VK_PAGE_DOWN: return Toggle.KEYBOARD_PAGE_DOWN;
		case KeyEvent.VK_UP: return Toggle.KEYBOARD_UP;
		case KeyEvent.VK_DOWN: return Toggle.KEYBOARD_DOWN;
		case KeyEvent.VK_LEFT: return Toggle.KEYBOARD_LEFT;
		case KeyEvent.VK_RIGHT: return Toggle.KEYBOARD_RIGHT;
		case KeyEvent.VK_NUM_LOCK: return Toggle.KEYBOARD_NUM_LOCK;
		case KeyEvent.VK_DIVIDE: return Toggle.KEYBOARD_DIVIDE;
		case KeyEvent.VK_MULTIPLY: return Toggle.KEYBOARD_MULTIPLY;
		case KeyEvent.VK_SUBTRACT: return Toggle.KEYBOARD_SUBTRACT;
		case KeyEvent.VK_NUMPAD7: return Toggle.KEYBOARD_NUMPAD_7;
		case KeyEvent.VK_NUMPAD8: return Toggle.KEYBOARD_NUMPAD_8;
		case KeyEvent.VK_NUMPAD9: return Toggle.KEYBOARD_NUMPAD_9;
		case KeyEvent.VK_ADD: return Toggle.KEYBOARD_ADD;
		case KeyEvent.VK_NUMPAD4: return Toggle.KEYBOARD_NUMPAD_4;
		case KeyEvent.VK_NUMPAD5: return Toggle.KEYBOARD_NUMPAD_5;
		case KeyEvent.VK_NUMPAD6: return Toggle.KEYBOARD_NUMPAD_6;
		case KeyEvent.VK_NUMPAD1: return Toggle.KEYBOARD_NUMPAD_1;
		case KeyEvent.VK_NUMPAD2: return Toggle.KEYBOARD_NUMPAD_2;
		case KeyEvent.VK_NUMPAD3: return Toggle.KEYBOARD_NUMPAD_3;
		case KeyEvent.VK_NUMPAD0: return Toggle.KEYBOARD_NUMPAD_0;
		case KeyEvent.VK_DECIMAL: return Toggle.KEYBOARD_NUMPAD_DECIMAL;
		default: return null;
		}
	}
	//=================================================================================================================

	//=================================================================================================================
	private Toggle mapPointerButton(int button) {
		switch (button) {
		case MouseEvent.BUTTON1: return Toggle.POINTER_BUTTON_01;
		case MouseEvent.BUTTON2: return Toggle.POINTER_BUTTON_02;
		case MouseEvent.BUTTON3: return Toggle.POINTER_BUTTON_03;
		case MouseEvent.BUTTON4: return Toggle.POINTER_BUTTON_04;
		case MouseEvent.BUTTON5: return Toggle.POINTER_BUTTON_05;
		case MouseEvent.BUTTON6: return Toggle.POINTER_BUTTON_06;
		case MouseEvent.BUTTON7: return Toggle.POINTER_BUTTON_07;
		case MouseEvent.BUTTON8: return Toggle.POINTER_BUTTON_08;
		case MouseEvent.BUTTON9: return Toggle.POINTER_BUTTON_09;
		default: return null;
		}
	}
	//=================================================================================================================

	//=================================================================================================================
	private Action action;
	private Toggle toggle;
	private char   key;
	private int    count;
	//=================================================================================================================

	//=================================================================================================================
	private void handle(Action action, Toggle toggle, char key, int count) {
		this.action = action;		
		this.toggle = toggle;
		this.key = key;
		this.count = count;
		if (handler!= null) handler.handle(this);
	}
	//=================================================================================================================

	//=================================================================================================================
	public Action action() {
		return action;
	}
	//=================================================================================================================

	//=================================================================================================================
	public Toggle toggle() {
		return toggle;
	}
	//=================================================================================================================

	//=================================================================================================================
	public char key() {
		return key;
	}
	//=================================================================================================================

	//=================================================================================================================
	public int count() {
		return count;
	}
	//=================================================================================================================

	//=================================================================================================================
	public float axis(Axis axis) {
		var value = axes.get(axis);
		if (value == null) value = 0f;
		return value;
	}
	//=================================================================================================================

	//=================================================================================================================
	public boolean toggle(Toggle toggle) {
		return toggles.contains(toggle);
	}
	//=================================================================================================================
	
}
//*********************************************************************************************************************
