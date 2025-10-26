//*****************************************************************************
package taskticker.web;
//*****************************************************************************

import java.util.ArrayList;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import taskticker.dto.Worker;
import taskticker.logic.LogicFactory;

//*****************************************************************************
public class WebController {

	//=========================================================================
	private static final int PORT = 8050;
	//=========================================================================
	
	//=========================================================================
	private Javalin javalin;
	private LogicFactory logicFactory;
	//=========================================================================

	//=========================================================================
	public WebController(LogicFactory logicFactory) {

		this.logicFactory = logicFactory;
		
		javalin = Javalin.create();
		
		javalin.post("/worker/create", this::handleWorkerCreate);
		javalin.get("/worker/list", this::handleWorkerList);
		javalin.get("/worker/{ident}/get", this::handleWorkerGet);
		javalin.put("/worker/{ident}/put", this::handleWorkerPut);
		
		javalin.start(PORT);
		
	}
	//=========================================================================

	//=========================================================================
	public void close() {
		javalin.close();
	}
	//=========================================================================
	
	//=========================================================================
	private void handleWorkerCreate(Context context) {
		try {
			Worker worker = context.bodyAsClass(Worker.class);
			logicFactory.workerCreate(worker);
			context.json(worker);
		} catch (Exception e) {
			context.status(HttpStatus.BAD_REQUEST);
			context.result(e.toString());
		}
	}
	//=========================================================================

	//=========================================================================
	private void handleWorkerList(Context context) {
		try {
			var list = new ArrayList<Worker>();
			logicFactory.workerList(list);
			context.json(list);
		} catch (Exception e) {
			context.status(HttpStatus.BAD_REQUEST);
			context.result(e.toString());
		}
	}
	//=========================================================================

	//=========================================================================
	private void handleWorkerGet(Context context) {
		try {
			String ident = context.pathParam("ident");
			var worker = new Worker();
			worker.setIdent(ident);
			logicFactory.workerGet(worker);
			context.json(worker);
		} catch (Exception e) {
			context.status(HttpStatus.BAD_REQUEST);
			context.result(e.toString());
		}
	}
	//=========================================================================

	//=========================================================================
	private void handleWorkerPut(Context context) {
		try {
			String ident = context.pathParam("ident");
			Worker worker = context.bodyAsClass(Worker.class);
			worker.setIdent(ident);
			logicFactory.workerPut(worker);
			context.json(worker);
		} catch (Exception e) {
			context.status(HttpStatus.BAD_REQUEST);
			context.result(e.toString());
		}
	}
	//=========================================================================

}
//*****************************************************************************
