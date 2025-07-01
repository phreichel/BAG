//**************************************************************************************************
package jarcanoid.game;
//**************************************************************************************************

import jade.ExampleActions;
import jade.ExampleTriggers;
import jade.Platform;
import jade.clock.Clock;
import jarcanoid.model.JarkanoidSimulation;
import jarcanoid.model.Level;
import jarcanoid.model.LevelGenerator;
import jarcanoid.model.LevelRuntime;
import jarcanoid.model.SimpleLevelGenerator;

//**************************************************************************************************
public class Application extends jade.Application implements Runnable {

	//==============================================================================================
	private Clock clock = new Clock();
	private Platform platform = new Platform();
	private LevelGenerator generator = new SimpleLevelGenerator();
	private Level level = null;
	private LevelRuntime levelRuntime = new LevelRuntime();
	private JarcanoidScene scene = new JarcanoidScene();
	private JarcanoidActions actions = new JarcanoidActions();
	private JarcanoidTriggers triggers = new JarcanoidTriggers(actions);
	private JarkanoidSimulation simulation = null;
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
	public void run() {

		quit = false;
		
		level = generator.generate();
		levelRuntime.setup(level);
		scene.createScene(levelRuntime);
		simulation = new JarkanoidSimulation(scene, levelRuntime);
		actions.setApplication(this);
		actions.setSliderComponent(simulation.getSliderComponent());
		actions.setMotionController(simulation.getCamController());
		platform.getInput().setTriggerList(triggers);
		platform.setScene(scene);
		//platform.setCanvas(null);
		
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
