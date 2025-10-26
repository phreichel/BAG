//*****************************************************************************
package tt.web;
//*****************************************************************************

import io.javalin.Javalin;
import io.javalin.http.Context;
import tt.logic.LogicFactory;

//*****************************************************************************
public class WebController {

	//=========================================================================
	private static final int WEB_PORT = 8050; 
	//=========================================================================
	
	//=========================================================================
	private Javalin      server;
	private LogicFactory logicFactory;
	//=========================================================================

	//=========================================================================
	public WebController(LogicFactory logicFactory) {
		
		this.logicFactory = logicFactory;
		
		server = Javalin.create();
		
		server.get("/user/list", this::doNothing);
		server.get("/user/create", this::doNothing);
		server.get("/user/{name}/delete", this::doNothing);
		server.get("/user/{name}/show", this::doNothing);
		server.get("/task/{user}/create", this::doNothing);
		server.get("/task/{user}/list", this::doNothing);

	}
	//=========================================================================
	
	//=========================================================================
	public void start() {
		server.start(WEB_PORT);
	}
	//=========================================================================

	//=========================================================================
	public void stop() {
		server.stop();
	}
	//=========================================================================

	//=========================================================================
	private void doNothing(Context ctx) {
		var nothing = logicFactory.createNothing();
		nothing.toDo();
		System.out.println("HELO");
		ctx.result("HELO!");
	}
	//=========================================================================
	
}
//*****************************************************************************
