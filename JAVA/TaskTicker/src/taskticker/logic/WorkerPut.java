//*****************************************************************************
package taskticker.logic;
//*****************************************************************************

import taskticker.dao.WorkerDao;
import taskticker.db.DbSession;
import taskticker.dto.Worker;

//*****************************************************************************
public class WorkerPut {

	//=========================================================================
	private DbSession dbSession;
	private Worker worker;
	//=========================================================================
	
	//=========================================================================
	public WorkerPut(DbSession dbSession, Worker worker) {		
		this.dbSession = dbSession;
		this.worker = worker;
	}
	//=========================================================================

	//=========================================================================
	public void run() {
		var ident = worker.getIdent();
		try {
			dbSession.begin();
			WorkerDao daoWorker = dbSession.workerGet(ident);
			if (worker.getIdent() == null) worker.setIdent(daoWorker.getIdent());
			if (worker.getName() == null) worker.setName(daoWorker.getName());
			daoWorker.setIdent(worker.getIdent());
			daoWorker.setName(worker.getName());
			dbSession.workerUpdate(daoWorker);
			dbSession.commit();
		} catch (Exception e) {
			dbSession.rollback();
			throw e;
		} finally {
			dbSession.close();
		}
	}
	//=========================================================================
	
}
//*****************************************************************************
