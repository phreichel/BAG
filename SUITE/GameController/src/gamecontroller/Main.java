//*****************************************************************************
package gamecontroller;
//*****************************************************************************

import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;

//*****************************************************************************
public class Main  {

	//=========================================================================
	public static void main(String[] args) {
		new Main();
	}
	//=========================================================================

	//=========================================================================
	public Main() {
		run();
	}
	//=========================================================================
	
	//=========================================================================
	public void run() {

		//System.load(new File("./lib/native/jinput-dx8_64.dll").getAbsolutePath());
		//System.load(new File("./lib/native/jinput-raw_64.dll").getAbsolutePath());		
		
		var environment = ControllerEnvironment.getDefaultEnvironment();
		var controllers = environment.getControllers();		
		
		for (var controller : controllers) {

			System.out.println("*****************************************************");
			System.out.println("NAME: " + controller.getName());
			System.out.println("TYPE: " + controller.getType());
			System.out.println("PORT-TYPE: " + controller.getPortType());
			System.out.println("PORT: " + controller.getPortNumber());
			System.out.println("*****************************************************");
			System.out.println();
			
		}
		
		while (true) {
			for (var controller : controllers) {
				if (controller.poll()) {
					var queue = controller.getEventQueue();
					Event event = new Event();
					while (queue.getNextEvent(event)) {
						var msg = String.format(
							"%s %s %s",
							controller.getName(),
							event.getComponent(),						
							event.getValue()
						);
						System.out.println(msg);
					}
				}
			}
		}
		
		
	}
	//=========================================================================
	
}
//*****************************************************************************
