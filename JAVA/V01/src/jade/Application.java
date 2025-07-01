//**************************************************************************************************
package jade;
//**************************************************************************************************

import jade.gui.Flag;
import lombok.Getter;
import jade.clock.Clock;

//**************************************************************************************************
public class Application {
	
	//==============================================================================================
	private Clock clock = new Clock();
	@Getter private Platform platform = new Platform();
	private ExampleActions actions = new ExampleActions();
	private ExampleTriggers triggers = new ExampleTriggers(actions);
	private GUIExampleTriggers guiTriggers = new GUIExampleTriggers(actions);
	private ExampleScene scene = new ExampleScene();
	private ExampleCanvas canvas = new ExampleCanvas((w,e) -> doQuit());
	private ExampleSimulation simulation = new ExampleSimulation(scene.getCameraTransform());
	//==============================================================================================

	//==============================================================================================
	private boolean quit = false;
	//==============================================================================================

	//==============================================================================================
	public void doQuit() {
		quit = true;
	}
	//==============================================================================================

	//==============================================================================================
	public void toggleGUIVisiblity() {
		boolean state = !canvas.getFlags().contains(Flag.DISPLAYED);
		float timeScale = state ? 0f : 1f;
		simulation.setTimeScale(timeScale);
		platform.setPointerVisible(state);
		platform.getInput().setTriggerList(state ? guiTriggers : triggers);
		canvas.setFlag(Flag.DISPLAYED, state);
	}
	//==============================================================================================
	
	//==============================================================================================
	public void run() {

		quit = false;
		
		actions.setApplication(this);
		actions.setMotionController(simulation.getCamController());
		platform.getInput().setTriggerList(triggers);
		platform.setScene(scene);
		platform.setCanvas(canvas);
		
		clock.add(1000000000L/60L, (p, n) -> platform.update());
		clock.add(1000000000L/60L, (p, n) -> simulation.update((float) p / 1000000000f * (float) n));
		
		platform.init();
		
		while (!quit) {
			clock.update();
			Thread.yield();
		}
		
		platform.done();
		
	}
	//==============================================================================================

	
	//==============================================================================================
	public static void main(String[] args) {
		Application application = new Application();
		application.run();
	}
	//==============================================================================================

}
//**************************************************************************************************
