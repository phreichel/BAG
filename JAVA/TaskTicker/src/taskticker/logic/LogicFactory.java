//*****************************************************************************
package taskticker.logic;
//*****************************************************************************

import java.util.List;

import taskticker.db.DbFactory;
import taskticker.dto.Worker;

//*****************************************************************************
public class LogicFactory {

	//=========================================================================
	private DbFactory dbFactory = null;
	//=========================================================================
	
	//=========================================================================
	public LogicFactory(DbFactory dbFactory) {
		this.dbFactory = dbFactory; 
	}
	//=========================================================================

	//=========================================================================
	public void workerCreate(Worker worker) {
		var session = dbFactory.newSession();
		var workerCreate = new WorkerCreate(session, worker);
		workerCreate.run();
	}
	//=========================================================================

	//=========================================================================
	public void workerList(List<Worker> list) {
		var session = dbFactory.newSession();
		var workerList = new WorkerList(session, list);
		workerList.run();
	}
	//=========================================================================

	//=========================================================================
	public void workerGet(Worker worker) {
		var session = dbFactory.newSession();
		var workerGet = new WorkerGet(session, worker);
		workerGet.run();
	}
	//=========================================================================

	//=========================================================================
	public void workerPut(Worker worker) {
		var session = dbFactory.newSession();
		var workerPut = new WorkerPut(session, worker);
		workerPut.run();
	}
	//=========================================================================
	
}
//*****************************************************************************
