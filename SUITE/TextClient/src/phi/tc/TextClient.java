//*****************************************************************************
package phi.tc;
//*****************************************************************************

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

//*****************************************************************************
public class TextClient {

	//=========================================================================
	public static void main(String[] args) {
		try {
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            var screen = new TerminalScreen(terminal);
            screen.startScreen();            
            screen.setCursorPosition(null); // Cursor ausblenden
            var tgborder = screen.newTextGraphics();
            tgborder.setForegroundColor(TextColor.ANSI.RED);
            tgborder.setBackgroundColor(TextColor.ANSI.BLUE_BRIGHT);
            tgborder.drawRectangle(TerminalPosition.TOP_LEFT_CORNER, screen.getTerminalSize(), ' ');
            TextGraphics tg = screen.newTextGraphics();
            tg.setForegroundColor(TextColor.ANSI.CYAN);
            tg.putString(10, 5, "Hallo Welt im Textmodus!");

            screen.setCursorPosition(TerminalPosition.TOP_LEFT_CORNER); 
            screen.doResizeIfNecessary();
            screen.refresh();

            screen.readInput(); // wartet auf Tasteneingabe
            screen.stopScreen(); // Aufräumen
            screen.close();

      } catch (Exception e) {
            e.printStackTrace();
      }
	}
	//=========================================================================

}
//*****************************************************************************
