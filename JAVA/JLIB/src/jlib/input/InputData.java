//*************************************************************************************************
package jlib.input;
//*************************************************************************************************

//*************************************************************************************************
public class InputData {

	//=============================================================================================
	public static final int EVENT_MOVED     = 2;
	public static final int EVENT_PRESSED   = 0;
	public static final int EVENT_RELEASED  = 1;
	//=============================================================================================

	//=============================================================================================
	public static final int SOURCE_KEYBOARD = 0;
	public static final int SOURCE_POINTER  = 1;
	//=============================================================================================

	//=============================================================================================
	public static final int BUTTON_NONE       = -1;
	public static final int BUTTON_ESCAPE     =  0;
	public static final int BUTTON_F1         =  1;
	public static final int BUTTON_F2         =  2;
	public static final int BUTTON_F3         =  3;
	public static final int BUTTON_F4         =  4;
	public static final int BUTTON_F5         =  5;
	public static final int BUTTON_F6         =  6;
	public static final int BUTTON_F7         =  7;
	public static final int BUTTON_F8         =  8;
	public static final int BUTTON_F9         =  9;
	public static final int BUTTON_F10        = 10;
	public static final int BUTTON_F11        = 11;
	public static final int BUTTON_F12        = 12;
	public static final int BUTTON_POINTER_01 = 101;
	public static final int BUTTON_POINTER_02 = 102;
	public static final int BUTTON_POINTER_03 = 103;
	public static final int BUTTON_POINTER_04 = 104;
	public static final int BUTTON_POINTER_05 = 105;
	public static final int BUTTON_POINTER_06 = 106;
	//=============================================================================================
	
	//=============================================================================================
	public int  event;
	public int  source;
	public int  button;
	public char symbol;
	public int  x;
	public int  y;
	//=============================================================================================

	//=============================================================================================
	public InputData(int event, int x, int y) {
		this.event = event;
		this.source = SOURCE_POINTER;
		this.button = BUTTON_NONE;
		this.symbol = '\0';
		this.x = x;
		this.y = y;
	}
	//=============================================================================================
	
	//=============================================================================================
	public InputData(int event, int source, int button, int x, int y) {
		this.event = event;
		this.source = source;
		this.button = button;
		this.symbol = '\0';
		this.x = x;
		this.y = y;
	}
	//=============================================================================================

	//=============================================================================================
	public InputData(int event, int button, char symbol, int x, int y) {
		this.event = event;
		this.source = SOURCE_KEYBOARD;
		this.button = button;
		this.symbol = symbol;
		this.x = x;
		this.y = y;
	}
	//=============================================================================================
	
}
//*************************************************************************************************
