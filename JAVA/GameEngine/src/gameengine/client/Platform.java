//*****************************************************************************
package gameengine.client;
//*****************************************************************************

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.jogamp.nativewindow.WindowClosingProtocol.WindowClosingMode;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.newt.event.WindowUpdateEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.awt.TextRenderer;
import gameengine.event.Button;
import gameengine.event.EventPump;
import gameengine.event.Ident;
import gameengine.event.Key;

//*****************************************************************************
public class Platform implements WindowListener, GLEventListener, KeyListener, MouseListener {

	//=========================================================================
	private EventPump eventPump;
	private GLWindow window = null;
	private List<SurfaceHandler>      surfaceHandlers = new ArrayList<>();
	private Map<String, TextRenderer> textRenderers   = new HashMap<>();
	//=========================================================================

	//=========================================================================
	public Platform(EventPump eventPump) {
		this.eventPump = eventPump;
	}
	//=========================================================================

	//=========================================================================
	public void addSurfaceHandler(SurfaceHandler surfaceHandler) {
		this.surfaceHandlers.add(surfaceHandler);
	}
	//=========================================================================

	//=========================================================================
	public void delSurfaceHandler(SurfaceHandler surfaceHandler) {
		this.surfaceHandlers.remove(surfaceHandler);
	}
	//=========================================================================

	//=========================================================================
	public void init() {

		var glProfile = GLProfile.getDefault();
		var glCapabilities = new GLCapabilities(glProfile);
		window = GLWindow.create(glCapabilities);
		window.setTitle("Game Engine Client");
		window.setSize(800, 600);
		window.setMaximized(true, true);
		window.addWindowListener(this);
		window.addKeyListener(this);
		window.addMouseListener(this);
		window.addGLEventListener(this);
		window.setDefaultCloseOperation(WindowClosingMode.DO_NOTHING_ON_CLOSE);
		window.setVisible(true);

	}
	//=========================================================================

	//=========================================================================
	public void update() {
		window.display();
	}
	//=========================================================================

	//=========================================================================
	public void done() {
		window.setVisible(false);
		window.destroy();
		window = null;
	}
	//=========================================================================

	//=========================================================================
	@Override
	public void windowResized(WindowEvent e) {}
	//=========================================================================

	//=========================================================================
	@Override
	public void windowMoved(WindowEvent e) {}
	//=========================================================================

	//=========================================================================
	@Override
	public void windowDestroyNotify(WindowEvent e) {

		var event = eventPump.allocate();
		event.setCommand(Ident.ON_PLATFORM_CLOSE);
		eventPump.submit(event);

	}
	//=========================================================================

	//=========================================================================
	@Override
	public void windowDestroyed(WindowEvent e) {}
	//=========================================================================

	//=========================================================================
	@Override
	public void windowGainedFocus(WindowEvent e) {}
	//=========================================================================

	//=========================================================================
	@Override
	public void windowLostFocus(WindowEvent e) {}
	//=========================================================================

	//=========================================================================
	@Override
	public void windowRepaint(WindowUpdateEvent e) {}
	//=========================================================================

	//=========================================================================
	@Override
	public void mouseClicked(MouseEvent e) {

		var event  = eventPump.allocate();
		var button = mapButton(e.getButton());		
		int count  = e.getClickCount();
		eventPump.setPointerLocation(e.getX(), window.getSurfaceHeight() - e.getY() - 1);
		event.setPointerClicked(button, count);
		eventPump.submit(event);

	}
	//=========================================================================

	//=========================================================================
	@Override
	public void mouseEntered(MouseEvent e) {
		eventPump.setPointerLocation(e.getX(), window.getSurfaceHeight() - e.getY() - 1);
	}
	//=========================================================================

	//=========================================================================
	@Override
	public void mouseExited(MouseEvent e) {
		eventPump.setPointerLocation(e.getX(), window.getSurfaceHeight() - e.getY() - 1);
	}
	//=========================================================================

	//=========================================================================
	@Override
	public void mousePressed(MouseEvent e) {

		var event = eventPump.allocate();
		var button = mapButton(e.getButton());
		eventPump.setButtonPressed(button);
		eventPump.setPointerLocation(e.getX(), window.getSurfaceHeight() - e.getY() - 1);
		event.setPointerPressed(button);
		eventPump.submit(event);

	}
	//=========================================================================

	//=========================================================================
	@Override
	public void mouseReleased(MouseEvent e) {

		var event = eventPump.allocate();
		var button = mapButton(e.getButton());
		eventPump.setButtonReleased(button);
		eventPump.setPointerLocation(e.getX(), window.getSurfaceHeight() - e.getY() - 1);
		event.setPointerReleased(button);
		eventPump.submit(event);

	}
	//=========================================================================

	//=========================================================================
	@Override
	public void mouseMoved(MouseEvent e) {

		var event = eventPump.allocate();
		eventPump.setPointerLocation(e.getX(), window.getSurfaceHeight() - e.getY() - 1);
		event.setPointerMoved();
		eventPump.submit(event);

	}
	//=========================================================================

	//=========================================================================
	@Override
	public void mouseDragged(MouseEvent e) {

		var event = eventPump.allocate();
		eventPump.setPointerLocation(e.getX(), window.getSurfaceHeight() - e.getY() - 1);
		event.setPointerMoved();
		eventPump.submit(event);

	}
	//=========================================================================

	//=========================================================================
	@Override
	public void mouseWheelMoved(MouseEvent e) {}
	//=========================================================================

	//=========================================================================
	@Override
	public void keyPressed(KeyEvent e) {

		if (!e.isAutoRepeat()) {
			var event = eventPump.allocate();
			var key = mapKey(e.getKeySymbol());
			eventPump.setKeyPressed(key);
			event.setKeyPressed(key);
			eventPump.submit(event);
		}

		{
			if (e.isPrintableKey()) {
				var event = eventPump.allocate();
				var key = mapKey(e.getKeySymbol());
				var character = e.getKeyChar();
				event.setKeyTyped(key, character);
				eventPump.submit(event);
			}
		}

	}
	//=========================================================================

	//=========================================================================
	@Override
	public void keyReleased(KeyEvent e) {

		if (!e.isAutoRepeat()) {
			var event = eventPump.allocate();
			var key = mapKey(e.getKeySymbol());
			eventPump.setKeyReleased(key);
			event.setKeyReleased(key);
			eventPump.submit(event);
		}

	}
	//=========================================================================

	//=========================================================================
	@Override
	public void init(GLAutoDrawable drawable) {

		var surface = new Surface(Surface.CONTEXT_INIT, drawable, textRenderers);
		surface.font("SYSTEM", "Arial-BOLD-12");
		for (var surfaceHandler : surfaceHandlers) {
			surfaceHandler.handleSurface(surface);
		}

	}
	//=========================================================================

	//=========================================================================
	@Override
	public void dispose(GLAutoDrawable drawable) {

		var surface = new Surface(Surface.CONTEXT_DONE, drawable, textRenderers);
		for (var surfaceHandler : surfaceHandlers) {
			surfaceHandler.handleSurface(surface);
		}

		for (var name : textRenderers.keySet()) {
			var tr = textRenderers.get(name);
			tr.dispose();
		}
		textRenderers.clear();

	}
	//=========================================================================

	//=========================================================================
	@Override
	public void display(GLAutoDrawable drawable) {

		var api = drawable.getGL().getGL2();
		api.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		
		api.glDisable(GL2.GL_LIGHTING);
		api.glDisable(GL2.GL_DEPTH_TEST);
		var surface = new Surface(Surface.CONTEXT_UPDATE, drawable, textRenderers);
		for (var surfaceHandler : surfaceHandlers) {
			surfaceHandler.handleSurface(surface);
		}

	}
	//=========================================================================

	//=========================================================================
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
	//=========================================================================

	//=========================================================================
	private Key mapKey(int nativeKey) {
		switch (nativeKey) {
		case KeyEvent.VK_ESCAPE       -> { return Key.ESCAPE;      }
		case KeyEvent.VK_F1           -> { return Key.F1;          }
		case KeyEvent.VK_F2           -> { return Key.F2;          }
		case KeyEvent.VK_F3           -> { return Key.F3;          }
		case KeyEvent.VK_F4           -> { return Key.F4;          }
		case KeyEvent.VK_F5           -> { return Key.F5;          }
		case KeyEvent.VK_F6           -> { return Key.F6;          }
		case KeyEvent.VK_F7           -> { return Key.F7;          }
		case KeyEvent.VK_F8           -> { return Key.F8;          }
		case KeyEvent.VK_F9           -> { return Key.F9;          }
		case KeyEvent.VK_F10          -> { return Key.F10;         }
		case KeyEvent.VK_F11          -> { return Key.F11;         }
		case KeyEvent.VK_F12          -> { return Key.F12;         }
		case KeyEvent.VK_PRINTSCREEN  -> { return Key.PRINT;       }
		case KeyEvent.VK_SCROLL_LOCK  -> { return Key.SCROLL_LOCK; }
		case KeyEvent.VK_PAUSE        -> { return Key.PAUSE;       }
		case KeyEvent.VK_1            -> { return Key.KB1;         }
		case KeyEvent.VK_2            -> { return Key.KB2;         }
		case KeyEvent.VK_3            -> { return Key.KB3;         }
		case KeyEvent.VK_4            -> { return Key.KB4;         }
		case KeyEvent.VK_5            -> { return Key.KB5;         }
		case KeyEvent.VK_6            -> { return Key.KB6;         }
		case KeyEvent.VK_7            -> { return Key.KB7;         }
		case KeyEvent.VK_8            -> { return Key.KB8;         }
		case KeyEvent.VK_9            -> { return Key.KB9;         }
		case KeyEvent.VK_0            -> { return Key.KB0;         }
		case KeyEvent.VK_BACK_SPACE   -> { return Key.BACK_SPACE;  }
		case KeyEvent.VK_TAB          -> { return Key.TAB;         }
		case KeyEvent.VK_Q            -> { return Key.Q;           }
		case KeyEvent.VK_W            -> { return Key.W;           }
		case KeyEvent.VK_E            -> { return Key.E;           }
		case KeyEvent.VK_R            -> { return Key.R;           }
		case KeyEvent.VK_T            -> { return Key.T;           }
		case KeyEvent.VK_Z            -> { return Key.Z;           }
		case KeyEvent.VK_U            -> { return Key.U;           }
		case KeyEvent.VK_I            -> { return Key.I;           }
		case KeyEvent.VK_O            -> { return Key.O;           }
		case KeyEvent.VK_P            -> { return Key.P;           }
		case KeyEvent.VK_PLUS         -> { return Key.PLUS;        }
		case KeyEvent.VK_ENTER        -> { return Key.ENTER;       }
		case KeyEvent.VK_CAPS_LOCK    -> { return Key.CAPS_LOCK;   }
		case KeyEvent.VK_A            -> { return Key.A;           }
		case KeyEvent.VK_S            -> { return Key.S;           }
		case KeyEvent.VK_D            -> { return Key.D;           }
		case KeyEvent.VK_F            -> { return Key.F;           }
		case KeyEvent.VK_G            -> { return Key.G;           }
		case KeyEvent.VK_H            -> { return Key.H;           }
		case KeyEvent.VK_J            -> { return Key.J;           }
		case KeyEvent.VK_K            -> { return Key.K;           }
		case KeyEvent.VK_L            -> { return Key.L;           }
		case KeyEvent.VK_NUMBER_SIGN  -> { return Key.HASH;        }
		case KeyEvent.VK_SHIFT        -> { return Key.SHIFT;       }
		case KeyEvent.VK_Y            -> { return Key.Y;           }
		case KeyEvent.VK_X            -> { return Key.X;           }
		case KeyEvent.VK_C            -> { return Key.C;           }
		case KeyEvent.VK_V            -> { return Key.V;           }
		case KeyEvent.VK_B            -> { return Key.B;           }
		case KeyEvent.VK_N            -> { return Key.N;           }
		case KeyEvent.VK_M            -> { return Key.M;           }
		case KeyEvent.VK_COMMA        -> { return Key.COMMA;       }
		case KeyEvent.VK_PERIOD       -> { return Key.DOT;         }
		case KeyEvent.VK_MINUS        -> { return Key.MINUS;       }
		case KeyEvent.VK_CONTROL      -> { return Key.CONTROL;     }
		case KeyEvent.VK_WINDOWS      -> { return Key.SYSTEM;      }
		case KeyEvent.VK_ALT          -> { return Key.ALT;         }
		case KeyEvent.VK_SPACE        -> { return Key.SPACE;       }
		case KeyEvent.VK_CONTEXT_MENU -> { return Key.MENU;        }
		case KeyEvent.VK_INSERT       -> { return Key.INSERT;      }
		case KeyEvent.VK_DELETE       -> { return Key.DELETE;      }
		case KeyEvent.VK_HOME         -> { return Key.HOME;        }
		case KeyEvent.VK_END          -> { return Key.END;         }
		case KeyEvent.VK_PAGE_UP      -> { return Key.PAGEUP;      }
		case KeyEvent.VK_PAGE_DOWN    -> { return Key.PAGEDOWN;    }
		case KeyEvent.VK_UP           -> { return Key.UP;          }
		case KeyEvent.VK_LEFT         -> { return Key.LEFT;        }
		case KeyEvent.VK_DOWN         -> { return Key.DOWN;        }
		case KeyEvent.VK_RIGHT        -> { return Key.RIGHT;       }
		case KeyEvent.VK_NUM_LOCK     -> { return Key.NUM_LOCK;    }
		case KeyEvent.VK_DIVIDE       -> { return Key.DIVIDE;      }
		case KeyEvent.VK_MULTIPLY     -> { return Key.MULTIPLY;    }
		case KeyEvent.VK_SUBTRACT     -> { return Key.SUBTRACT;    }
		case KeyEvent.VK_NUMPAD7      -> { return Key.KP7;         }
		case KeyEvent.VK_NUMPAD8      -> { return Key.KP8;         }
		case KeyEvent.VK_NUMPAD9      -> { return Key.KP9;         }
		case KeyEvent.VK_ADD          -> { return Key.ADD;         }
		case KeyEvent.VK_NUMPAD4      -> { return Key.KP4;         }
		case KeyEvent.VK_NUMPAD5      -> { return Key.KP5;         }
		case KeyEvent.VK_NUMPAD6      -> { return Key.KP6;         }
		case KeyEvent.VK_NUMPAD1      -> { return Key.KP1;         }
		case KeyEvent.VK_NUMPAD2      -> { return Key.KP2;         }
		case KeyEvent.VK_NUMPAD3      -> { return Key.KP3;         }
		case KeyEvent.VK_NUMPAD0      -> { return Key.KP0;         }
		case KeyEvent.VK_DECIMAL      -> { return Key.DECIMAL;     }
		default                       -> { return Key.NONE;        }
		}
	}
	//=========================================================================

	//=========================================================================
	private Button mapButton(int nativeButton) {
		switch (nativeButton) {
		case MouseEvent.BUTTON1 -> { return Button.BUTTON1; }
		case MouseEvent.BUTTON2 -> { return Button.BUTTON2; }
		case MouseEvent.BUTTON3 -> { return Button.BUTTON3; }
		case MouseEvent.BUTTON4 -> { return Button.BUTTON4; }
		case MouseEvent.BUTTON5 -> { return Button.BUTTON5; }
		case MouseEvent.BUTTON6 -> { return Button.BUTTON6; }
		case MouseEvent.BUTTON7 -> { return Button.BUTTON7; }
		case MouseEvent.BUTTON8 -> { return Button.BUTTON8; }
		case MouseEvent.BUTTON9 -> { return Button.BUTTON9; }
		default -> { return Button.NONE; }
		}
	}
	//=========================================================================

}
//*****************************************************************************
