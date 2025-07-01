//*********************************************************************************************************************
package spark.world.generator;
//*********************************************************************************************************************

import java.util.Random;

import javax.vecmath.Vector3f;

import spark.math.MathUtil;
import spark.world.generated.GeneratedGalaxy;
import spark.world.generated.GeneratedStarsystem;

//*********************************************************************************************************************
public class GalaxyGenerator extends Generator<GeneratedGalaxy> {

	//=================================================================================================================
	public static final int MINIMUM_SYSTEM_COUNT = 100;
	public static final int MAXIMUM_SYSTEM_COUNT = 10000;
	//=================================================================================================================

	//=================================================================================================================
	public static final GalaxyGenerator INSTANCE = new GalaxyGenerator();
	//=================================================================================================================

	//=================================================================================================================
	private Random rnd = new Random();
	//=================================================================================================================
	
	//=================================================================================================================
	public void generate(GeneratedGalaxy galaxy) {
		
		if (galaxy.generated()) return;
		
		var seed = galaxy.seed();
		
		var generatedName = generateName(seed);
		galaxy.generatedName(generatedName);
		
		rnd.setSeed(seed);
		var systemCount = rnd.nextInt(
			MINIMUM_SYSTEM_COUNT,
			MAXIMUM_SYSTEM_COUNT+1);

		for (var i=0; i<systemCount; i++) {
			var length = (float) Math.cbrt(rnd.nextDouble()) * 150;
			var azimut = rnd.nextFloat(0f, (float) Math.PI * 2);
			var elevation = rnd.nextFloat((float) -Math.PI, (float) Math.PI);
			var systemLocation = MathUtil.sphericalToCartesian(new Vector3f(), length, azimut, elevation);
			var systemSeed = generateDerivedSeed(seed, systemLocation);
			var system = new GeneratedStarsystem(systemSeed, systemLocation, galaxy);
			galaxy.system(system);
		}
		
	}
	//=================================================================================================================
	
	//=================================================================================================================
	private String generateName(long seed) {
		return null;
	}
	//=================================================================================================================
	
}
//*********************************************************************************************************************
