//*****************************************************************************
package taskticker.db;
//*****************************************************************************

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import taskticker.dao.WorkerDao;

//*****************************************************************************
public class DbFactory {

	//=========================================================================
	private SessionFactory sessionFactory;
	//=========================================================================
	
	//=========================================================================
	public DbFactory() {
		
		var configuration = new Configuration();
		
		configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
		configuration.setProperty("hibernate.connection.url", "jdbc:h2:./taskticker;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE");
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		configuration.setProperty("hibernate.show_sql", "true");        
		
		configuration.addAnnotatedClass(WorkerDao.class);
		
		sessionFactory = configuration.buildSessionFactory();
		
	}
	//=========================================================================

	//=========================================================================
	public DbSession newSession() {
		return new DbSession(sessionFactory.openSession());
	}
	//=========================================================================
	
	//=========================================================================
	public void close() {
		sessionFactory.close();
	}
	//=========================================================================
	
}
//*****************************************************************************
