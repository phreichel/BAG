//*********************************************************************************************************************
package cody;

import java.nio.charset.Charset;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

//*********************************************************************************************************************



//*********************************************************************************************************************
public class Main {

	//=================================================================================================================
	public static void main(String[] args) throws Exception  {

		DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory(System.out, System.in, Charset.defaultCharset());
		Terminal terminal = terminalFactory.createTerminal();
		
		terminal.clearScreen();
		terminal.setCursorPosition(0, 0);
		terminal.putString("Hallo.Errr");
		terminal.readInput();
		terminal.close();
		
	}
	//=================================================================================================================
	
}
//*********************************************************************************************************************
