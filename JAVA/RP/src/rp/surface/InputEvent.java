//*************************************************************************************************
package rp.surface;
//*************************************************************************************************

//*************************************************************************************************
public interface InputEvent {

	//=============================================================================================
	public static final int TYPE_NONE    = 0x00000000;
	public static final int KEY_PRESSED  = 0x00010000;
	public static final int KEY_RELEASED = 0x00010001;
	public static final int KEY_TYPED    = 0x00010002;
	public static final int PTR_PRESSED  = 0x00020000;
	public static final int PTR_RELEASED = 0x00020001;
	public static final int PTR_CLICKED  = 0x00020002;
	public static final int PTR_MOVED    = 0x00020003;
	public static final int PTR_SCROLLED = 0x00020003;
	//=============================================================================================

	//=============================================================================================
	public static final int BTN_NONE     = 0x00000000;
	public static final int KB_ESCAPE    = 0x00010000;
	public static final int KB_F1        = 0x00010001;
	public static final int KB_F2        = 0x00010002;
	public static final int KB_F3        = 0x00010003;
	public static final int KB_F4        = 0x00010004;
	public static final int KB_F5        = 0x00010005;
	public static final int KB_F6        = 0x00010006;
	public static final int KB_F7        = 0x00010007;
	public static final int KB_F8        = 0x00010008;
	public static final int PT_BTN1      = 0x00010000;
	public static final int PT_BTN2      = 0x00010001;
	public static final int PT_BTN3      = 0x00010002;
	public static final int PT_BTN4      = 0x00010003;
	public static final int PT_BTN5      = 0x00010004;
	public static final int PT_BTN6      = 0x00010005;
	//=============================================================================================
	
	//=============================================================================================
	public long  time();
	public int   type();
	public int   button();
	public char  character();
	public int   count();
	public float xpos();
	public float ypos();
	//=============================================================================================
	
}
//*************************************************************************************************
