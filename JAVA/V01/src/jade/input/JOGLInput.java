//**************************************************************************************************
package jade.input;
//**************************************************************************************************

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import jade.gui.Canvas;
import jade.gui.Event;
import jade.gui.Ident;
import lombok.Getter;
import lombok.Setter;

//**************************************************************************************************
public class JOGLInput implements Input, KeyListener, MouseListener {

	//==============================================================================================
	@Getter private InternalPointer pointer = new InternalPointer();
	@Getter private InternalKeyboard keyboard = new InternalKeyboard();
	@Getter @Setter private TriggerList triggerList = null;
	@Getter @Setter private Canvas canvas = null;
	private Event event = new Event();
	//==============================================================================================

	//==============================================================================================
	public void mouseClicked(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			Button button = translateButton(e.getButton());
			pointer.setPosition(e.getX(), e.getY());
			fireEvent(Ident.POINTER_CLICKED, null, button, null, (int) e.getClickCount());
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void mouseEntered(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			pointer.setPosition(e.getX(), e.getY());
			fireEvent(Ident.POINTER_ENTERED, null, null, null, (int) e.getClickCount());
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void mouseExited(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			pointer.setPosition(e.getX(), e.getY());
			fireEvent(Ident.POINTER_EXITED, null, null, null, (int) e.getClickCount());
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void mousePressed(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			Button button = translateButton(e.getButton());
			pointer.setPosition(e.getX(), e.getY());
			pointer.setButton(button, true);
			if (triggerList != null) {
				triggerList.test(this, button, Flank.PRESSED);
			}
			fireEvent(Ident.POINTER_PRESSED, null, button, null, (int) e.getClickCount());
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void mouseReleased(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			Button button = translateButton(e.getButton());
			pointer.setPosition(e.getX(), e.getY());
			pointer.setButton(button, false);
			if (triggerList != null) {
				triggerList.test(this, button, Flank.RELEASED);
			}
			fireEvent(Ident.POINTER_RELEASED, null, button, null, (int) e.getClickCount());
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void mouseMoved(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			pointer.setPosition(e.getX(), e.getY());
			fireEvent(Ident.POINTER_MOVED, null, null, null, (int) e.getClickCount());
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void mouseDragged(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			pointer.setPosition(e.getX(), e.getY());
			fireEvent(Ident.POINTER_MOVED, null, null, null, (int) e.getClickCount());
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void mouseWheelMoved(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			pointer.setPosition(e.getX(), e.getY());
			//float[] rot = e.getRotation();
			//pointer.triggerWheel(e.getX(), e.getY(), rot[1], rot[0], e.getRotationScale());
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void keyPressed(KeyEvent e) {
		short keySymbol = e.getKeySymbol();
		Key key = translateKey(keySymbol);
		if (!e.isAutoRepeat()) {
			keyboard.setKey(key, true);
			if (triggerList != null) {
				triggerList.test(this, key, Flank.PRESSED);
			}
			fireEvent(Ident.KEY_PRESSED, key, null, e.getKeyChar(), null);
		}
		fireEvent(Ident.KEY_TYPED, key, null, e.getKeyChar(), null);
	}
	//==============================================================================================

	//==============================================================================================
	public void keyReleased(KeyEvent e) {
		short keySymbol = e.getKeySymbol();
		Key key = translateKey(keySymbol);
		if (!e.isAutoRepeat()) {
			keyboard.setKey(key, false);
			if (triggerList != null) {
				triggerList.test(this, key, Flank.RELEASED);
			}
			fireEvent(Ident.KEY_RELEASED, key, null, e.getKeyChar(), null);
		}
	}
	//==============================================================================================

	//==============================================================================================
	private Button translateButton(short button) {
		switch (button) {
		case MouseEvent.BUTTON1: return Button.BTN1;
		case MouseEvent.BUTTON2: return Button.BTN2;
		case MouseEvent.BUTTON3: return Button.BTN3;
		case MouseEvent.BUTTON4: return Button.BTN4;
		case MouseEvent.BUTTON5: return Button.BTN5;
		case MouseEvent.BUTTON6: return Button.BTN6;
		default: return null;
		}
		
	}
	//==============================================================================================
	
	//==============================================================================================
	private Key translateKey(short code) {
		switch (code) {
		case KeyEvent.VK_ESCAPE: return Key.ESCAPE;
		case KeyEvent.VK_F1: return Key.F1;
		case KeyEvent.VK_F2: return Key.F2;
		case KeyEvent.VK_F3: return Key.F3;
		case KeyEvent.VK_F4: return Key.F4;
		case KeyEvent.VK_F5: return Key.F5;
		case KeyEvent.VK_F6: return Key.F6;
		case KeyEvent.VK_F7: return Key.F7;
		case KeyEvent.VK_F8: return Key.F8;
		case KeyEvent.VK_F9: return Key.F9;
		case KeyEvent.VK_F10: return Key.F10;
		case KeyEvent.VK_F11: return Key.F11;
		case KeyEvent.VK_F12: return Key.F12;
		case KeyEvent.VK_PRINTSCREEN: return Key.PRINT_SCREEN;
		case KeyEvent.VK_SCROLL_LOCK: return Key.SCROLL_LOCK;
		case KeyEvent.VK_PAUSE: return Key.PAUSE;
		case KeyEvent.VK_1: return Key.KB1;
		case KeyEvent.VK_2: return Key.KB2;
		case KeyEvent.VK_3: return Key.KB3;
		case KeyEvent.VK_4: return Key.KB4;
		case KeyEvent.VK_5: return Key.KB5;
		case KeyEvent.VK_6: return Key.KB6;
		case KeyEvent.VK_7: return Key.KB7;
		case KeyEvent.VK_8: return Key.KB8;
		case KeyEvent.VK_9: return Key.KB9;
		case KeyEvent.VK_0: return Key.KB0;
		case KeyEvent.VK_BACK_SPACE: return Key.BACK_SPACE;
		case KeyEvent.VK_TAB: return Key.TAB;
		case KeyEvent.VK_Q: return Key.Q;
		case KeyEvent.VK_W: return Key.W;
		case KeyEvent.VK_E: return Key.E;
		case KeyEvent.VK_R: return Key.R;
		case KeyEvent.VK_T: return Key.T;
		case KeyEvent.VK_Z: return Key.Z;
		case KeyEvent.VK_U: return Key.U;
		case KeyEvent.VK_I: return Key.I;
		case KeyEvent.VK_O: return Key.O;
		case KeyEvent.VK_P: return Key.P;
		case KeyEvent.VK_PLUS: return Key.PLUS;
		case KeyEvent.VK_ENTER: return Key.ENTER;
		case KeyEvent.VK_CAPS_LOCK: return Key.CAPS_LOCK;
		case KeyEvent.VK_A: return Key.A;
		case KeyEvent.VK_S: return Key.S;
		case KeyEvent.VK_D: return Key.D;
		case KeyEvent.VK_F: return Key.F;
		case KeyEvent.VK_G: return Key.G;
		case KeyEvent.VK_H: return Key.H;
		case KeyEvent.VK_J: return Key.J;
		case KeyEvent.VK_K: return Key.K;
		case KeyEvent.VK_L: return Key.L;
		case KeyEvent.VK_NUMBER_SIGN: return Key.HASH;
		case KeyEvent.VK_SHIFT: return Key.SHIFT;
		case KeyEvent.VK_Y: return Key.Y;
		case KeyEvent.VK_X: return Key.X;
		case KeyEvent.VK_C: return Key.C;
		case KeyEvent.VK_V: return Key.V;
		case KeyEvent.VK_B: return Key.B;
		case KeyEvent.VK_N: return Key.N;
		case KeyEvent.VK_M: return Key.M;
		case KeyEvent.VK_COMMA: return Key.COMMA;
		case KeyEvent.VK_COLON: return Key.DOT;
		case KeyEvent.VK_MINUS: return Key.MINUS;
		case KeyEvent.VK_CONTROL: return Key.CONTROL;
		case KeyEvent.VK_WINDOWS: return Key.WINDOWS;
		case KeyEvent.VK_ALT: return Key.ALT;
		case KeyEvent.VK_SPACE: return Key.SPACE;
		case KeyEvent.VK_CONTEXT_MENU: return Key.MENU;
		case KeyEvent.VK_INSERT: return Key.INSERT;
		case KeyEvent.VK_HOME: return Key.HOME;
		case KeyEvent.VK_PAGE_UP: return Key.PAGE_UP;
		case KeyEvent.VK_DELETE: return Key.DELETE;
		case KeyEvent.VK_END: return Key.END;
		case KeyEvent.VK_PAGE_DOWN: return Key.PAGE_DOWN;
		case KeyEvent.VK_UP: return Key.UP;
		case KeyEvent.VK_LEFT: return Key.LEFT;
		case KeyEvent.VK_DOWN: return Key.DOWN;
		case KeyEvent.VK_RIGHT: return Key.RIGHT;
		case KeyEvent.VK_NUM_LOCK: return Key.NUM_LOCK;
		case KeyEvent.VK_DIVIDE: return Key.DIVIDE;
		case KeyEvent.VK_MULTIPLY: return Key.MULTIPLY;
		case KeyEvent.VK_SUBTRACT: return Key.SUBTRACT;
		case KeyEvent.VK_NUMPAD7: return Key.NP7;
		case KeyEvent.VK_NUMPAD8: return Key.NP8;
		case KeyEvent.VK_NUMPAD9: return Key.NP9;
		case KeyEvent.VK_ADD: return Key.ADD;
		case KeyEvent.VK_NUMPAD4: return Key.NP4;
		case KeyEvent.VK_NUMPAD5: return Key.NP5;
		case KeyEvent.VK_NUMPAD6: return Key.NP6;
		case KeyEvent.VK_NUMPAD1: return Key.NP1;
		case KeyEvent.VK_NUMPAD2: return Key.NP2;
		case KeyEvent.VK_NUMPAD3: return Key.NP3;
		case KeyEvent.VK_NUMPAD0: return Key.NP0;
		case KeyEvent.VK_DECIMAL: return Key.DECIMAL;
		default: return null;
		}
	}
	//==============================================================================================

	//==============================================================================================
	private void fireEvent(Ident ident, Key key, Button button, Character keyChar, Integer clickCount) {
		event.setIdent(ident);
		event.setKey(key);
		event.setButton(button);
		event.setKeyboard(keyboard);
		event.setPointer(pointer);
		event.setKeyChar(keyChar);
		event.setClickCount(clickCount);
		event.getLocalPointerPositon().set(pointer.getX(), pointer.getY());
		if (canvas != null) {
			canvas.handleEvent(event);
		}
	}
	//==============================================================================================
	
}
//**************************************************************************************************
