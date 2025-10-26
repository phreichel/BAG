//*********************************************************************************************************************
package spark.test.world;

import spark.app.Application;
import spark.scene.Box;
import spark.world.Galaxy;
import spark.world.Starsystem;
import spark.world.Universe;
import spark.world.World;
import spark.world.generated.GeneratedWorld;

//*********************************************************************************************************************

//*********************************************************************************************************************
public class UniverseTest extends Application {

	//=================================================================================================================
	private World world = null;
	//=================================================================================================================

	//=================================================================================================================
	private Universe   universe   = null;
	private Galaxy     galaxy     = null;
	private Starsystem starsystem = null;
	//=================================================================================================================
	
	//=================================================================================================================
	protected void init() {
		
		long seed = System.currentTimeMillis();
		world = new GeneratedWorld(seed);
		world.generate();
		
		universe = world.universe();
		galaxy = universe.galaxy(world.spawnGalaxy());
		starsystem = galaxy.system(world.spawnStarsystem());
		
		super.init();
		
	}
	//=================================================================================================================
	
	//=================================================================================================================
	protected void initScene() {
		
		for (var i=0; i<universe.galaxyCount(); i++) {
			var galaxy = universe.galaxy(i);
			var location = galaxy.location();
			Box box = new Box(location);
			scene.galaxies.add(box);
		}

		for (var i=0; i<galaxy.systemCount(); i++) {
			var starsys = galaxy.system(i);
			var location = starsys.location();
			Box box = new Box(location);
			scene.stars.add(box);
		}

		var galloc = starsystem.location();
		scene.galaxyref.set(galloc);
		var starloc = starsystem.location();
		scene.camera.location.set(starloc);
		
	}
	//=================================================================================================================
	
	//=================================================================================================================
	public static final void main(String ... args) {
		var app = new UniverseTest();
		app.run();
	}
	//=================================================================================================================
	
}
//*********************************************************************************************************************
