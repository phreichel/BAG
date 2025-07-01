//*****************************************************************************
package taskticker;
//*****************************************************************************

import taskticker.db.DbFactory;
import taskticker.logic.LogicFactory;
import taskticker.web.WebController;

//*****************************************************************************
public class TaskTicker {

	//=========================================================================
	private DbFactory     dbFactory; 
	private LogicFactory  logicFactory;
	private WebController webController;
	//=========================================================================

	//=========================================================================
	public TaskTicker() {
		dbFactory = new DbFactory();
		logicFactory = new LogicFactory(dbFactory);
		webController = new WebController(logicFactory);
	}
	//=========================================================================

	//=========================================================================
	public void run() {
		var alive = true;
		while (alive) {
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		webController.close();
	}
	//=========================================================================
	
	//=========================================================================
	public static void main(String[] args) {
		var tt = new TaskTicker();
		tt.run();
	}
	//=========================================================================
	
}
//*****************************************************************************

