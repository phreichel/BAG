//*****************************************************************************
package tt;
//*****************************************************************************

import java.io.BufferedReader;
import java.io.InputStreamReader;

import tt.db.DataFactory;
import tt.logic.LogicFactory;
import tt.web.WebController;

//*****************************************************************************
public class Application {

	//=========================================================================
	public static Application create() {
		return new Application();
	}
	//=========================================================================

	//=========================================================================
	private DataFactory   dataFactory;
	private LogicFactory  logicFactory;
	private WebController webController;
	//=========================================================================
	
	//=========================================================================
	public Application() {}
	//=========================================================================
	
	//=========================================================================
	public void run() {
		dataFactory   = new DataFactory();
		logicFactory  = new LogicFactory(dataFactory);
		webController = new WebController(logicFactory);
		try {
			var isr = new InputStreamReader(System.in);
			var bir = new BufferedReader(isr);
			var terminated = false;
			webController.start();
			while (!terminated) {
				System.out.print("?:>");
				bir.readLine();
				terminated = true;
			}
			bir.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			webController.stop();
			dataFactory.close();
		}
	}
	//=========================================================================
	
}
//*****************************************************************************
