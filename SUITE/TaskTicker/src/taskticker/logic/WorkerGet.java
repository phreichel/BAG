//*****************************************************************************
package taskticker.logic;
//*****************************************************************************

import taskticker.dao.WorkerDao;
import taskticker.db.DbSession;
import taskticker.dto.Worker;

//*****************************************************************************
public class WorkerGet {

	//=========================================================================
	private DbSession dbSession;
	private Worker worker;
	//=========================================================================
	
	//=========================================================================
	public WorkerGet(DbSession dbSession, Worker worker) {		
		this.dbSession = dbSession;
		this.worker = worker;
	}
	//=========================================================================

	//=========================================================================
	public void run() {
		var ident = worker.getIdent();
		WorkerDao daoWorker = dbSession.workerGet(ident);
		worker.setName(daoWorker.getName());
		dbSession.close();
	}
	//=========================================================================
	
}
//*****************************************************************************
