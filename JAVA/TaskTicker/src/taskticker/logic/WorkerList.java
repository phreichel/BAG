//*****************************************************************************
package taskticker.logic;
//*****************************************************************************

import java.util.List;
import taskticker.db.DbSession;
import taskticker.dto.Worker;

//*****************************************************************************
public class WorkerList {

	//=========================================================================
	private DbSession dbSession;
	private List<Worker> workerList;
	//=========================================================================
	
	//=========================================================================
	public WorkerList(DbSession dbSession, List<Worker> workerList) {		
		this.dbSession = dbSession;
		this.workerList = workerList;
	}
	//=========================================================================

	//=========================================================================
	public void run() {
		var daoList = dbSession.workerList();
		for (var dao : daoList) {
			var worker = new Worker();
			worker.setIdent(dao.getIdent());
			worker.setName(dao.getName());
			workerList.add(worker);
		}
		dbSession.close();
	}
	//=========================================================================
	
}
//*****************************************************************************
