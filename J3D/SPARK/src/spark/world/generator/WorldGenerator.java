//*********************************************************************************************************************
package spark.world.generator;
//*********************************************************************************************************************

import java.util.Random;

import spark.world.generated.GeneratedUniverse;
import spark.world.generated.GeneratedWorld;

//*********************************************************************************************************************
public class WorldGenerator {

	//=================================================================================================================
	public static final WorldGenerator INSTANCE = new WorldGenerator();
	//=================================================================================================================

	//=================================================================================================================
	private Random rnd = new Random();
	//=================================================================================================================

	//=================================================================================================================
	public void generate(GeneratedWorld world) {
		
		if (world.generated()) return;
		
		var seed = world.seed();
		rnd.setSeed(seed);
		
		var universe = new GeneratedUniverse(seed);
		world.universe(universe);
		
		universe.generate();
		
		var spawnGalaxy = rnd.nextInt(universe.galaxyCount());
		world.spawnGalaxy(spawnGalaxy);
		var galaxy = universe.galaxy(spawnGalaxy);
		galaxy.generate();
		
		var spawnSystem = rnd.nextInt(galaxy.systemCount());
		world.spawnStarsystem(spawnSystem);
		var starsystem = galaxy.system(spawnSystem);
		starsystem.generate();
		
	}
	//=================================================================================================================

}
//*********************************************************************************************************************
