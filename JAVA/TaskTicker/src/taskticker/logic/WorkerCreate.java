//*****************************************************************************
package taskticker.logic;
//*****************************************************************************

import org.hibernate.exception.ConstraintViolationException;

import taskticker.dao.WorkerDao;
import taskticker.db.DbSession;
import taskticker.dto.Worker;

//*****************************************************************************
public class WorkerCreate {

	//=========================================================================
	private DbSession dbSession;
	private Worker worker;
	//=========================================================================
	
	//=========================================================================
	public WorkerCreate(DbSession dbSession, Worker worker) {		
		this.dbSession = dbSession;
		this.worker = worker;
	}
	//=========================================================================

	//=========================================================================
	private void validate() {
		if (worker == null) throw new RuntimeException("worker is null");
		if (worker.getIdent() == null) throw new RuntimeException("worker ident is null");
		if (worker.getName() == null) throw new RuntimeException("worker name is null");
		if (worker.getName().length() < 3) throw new RuntimeException("worker name must be at least 3 characters long");
		//if (worker.getName() [already exists]) throw new RuntimeException("worker name already taken");
	}
	//=========================================================================

	//=========================================================================
	private void update() {
		var dao = new WorkerDao();
		dao.setIdent(worker.getIdent());
		dao.setName(worker.getName());
		try {
			dbSession.begin();		
			dbSession.workerSave(dao);
			dbSession.commit();
		} catch (ConstraintViolationException e) {
			dbSession.rollback();
			throw e;
		} catch (Exception e) {
			dbSession.rollback();
			throw e;
		}
		finally {
			dbSession.close();
		}
	}
	//=========================================================================
	
	//=========================================================================
	public void run() {
		validate();
		update();
	}
	//=========================================================================
	
}
//*****************************************************************************
