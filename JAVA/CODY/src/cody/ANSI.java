//*****************************************************************************
package cody;
//*****************************************************************************

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;

//*****************************************************************************
public final class ANSI {

	//=========================================================================
	public static void ansi(PrintStream s, String code) throws Exception {
		s.write(0x1b);
		s.write('[');
		s.append(code);
		s.write('m');
	}
	//=========================================================================

	//=========================================================================
	public static void print(String resource, PrintStream target) throws Exception {
		
		InputStream is = Main.class.getClassLoader().getResourceAsStream(resource);
		Reader      ir = new InputStreamReader(is);
		print(ir, target);
		
	}
	//=========================================================================

	//=========================================================================
	public static void print(PrintStream target, String text) throws Exception {
		
		Reader ir = new StringReader(text);
		print(ir, target);
		
	}
	//=========================================================================
	
	//=========================================================================
	public static void print(Reader source, PrintStream target) throws Exception {
		
		int res = source.read();
		while (res != -1) {
			if (((char)res) == '#') {
				res = source.read();
				switch ((char) res) {
				case '0' -> { ansi(target, "0");  }
				case 'n' -> { ansi(target, "30"); }
				case 'r' -> { ansi(target, "31"); }
				case 'g' -> { ansi(target, "32"); }
				case 'y' -> { ansi(target, "33"); }
				case 'b' -> { ansi(target, "34"); }
				case 'm' -> { ansi(target, "35"); }
				case 'a' -> { ansi(target, "36"); }
				case 'w' -> { ansi(target, "37"); }
				case 'N' -> { ansi(target, "40"); }
				case 'R' -> { ansi(target, "41"); }
				case 'G' -> { ansi(target, "42"); }
				case 'Y' -> { ansi(target, "43"); }
				case 'B' -> { ansi(target, "44"); }
				case 'M' -> { ansi(target, "45"); }
				case 'A' -> { ansi(target, "46"); }
				case 'W' -> { ansi(target, "47"); }
				default -> {
					target.write('#');
					target.write(res);
				}
				}
			} else {
				target.write(res);
			}
			res = source.read();
		}
		source.close();
		target.flush();
		
	}
	//=========================================================================

}
//*****************************************************************************
