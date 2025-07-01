//*****************************************************************************
package tt.db;
//*****************************************************************************

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//*****************************************************************************
public class DataFactory {

	//=========================================================================
	private Configuration  configuration;
	private SessionFactory sessionFactory;
	//=========================================================================

	//=========================================================================
	public DataFactory() {

		configuration = new Configuration();

		// Basis-Konfiguration
		configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
		configuration.setProperty("hibernate.connection.url", "jdbc:h2:./tasktool;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE");
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");		
		configuration.setProperty("hibernate.show_sql", "true");        
		
        // Entity-Klassen manuell registrieren
        //configuration.addAnnotatedClass(tt.dto.Ident.class);

		sessionFactory = configuration.buildSessionFactory();

	}
	//=========================================================================

	//=========================================================================
	public DataSession getSession() {
		return new DataSession(sessionFactory);
	}
	//=========================================================================
	
	//=========================================================================
	public void close() {
		sessionFactory.close();
	}
	//=========================================================================

}
//*****************************************************************************
