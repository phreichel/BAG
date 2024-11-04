//*********************************************************************************************************************
package spark.world.generator;
//*********************************************************************************************************************

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Vector3f;

import spark.math.MathUtil;
import spark.world.generated.GeneratedGalaxy;
import spark.world.generated.GeneratedUniverse;

//*********************************************************************************************************************
public class UniverseGenerator extends Generator<GeneratedUniverse> {

	//=================================================================================================================
	public static final int MINIMUM_ATTRACTOR_COUNT = 10;
	public static final int MAXIMUM_ATTRACTOR_COUNT = 1000;
	//=================================================================================================================
	
	//=================================================================================================================
	public static final int MINIMUM_GALAXY_COUNT = 1000;
	public static final int MAXIMUM_GALAXY_COUNT = 100000;
	//=================================================================================================================

	//=================================================================================================================
	public static final UniverseGenerator INSTANCE = new UniverseGenerator();
	//=================================================================================================================

	//=================================================================================================================
	private Random rnd = new Random();
	//=================================================================================================================
	
	//=================================================================================================================
	public void generate(GeneratedUniverse universe) {
		
		if (universe.generated()) return;
		
		var seed = universe.seed();
		rnd.setSeed(seed);
		
		var generatedName = generateName(seed);
		universe.generatedName(generatedName);
		
		var voidCount = rnd.nextInt(
			MINIMUM_ATTRACTOR_COUNT,
			MAXIMUM_ATTRACTOR_COUNT+1);
		List<Vector3f> attractorCenters = new ArrayList<>();
		for (var i=0; i<voidCount; i++) {
			var x = rnd.nextFloat(-200, 200);
			var y = rnd.nextFloat(-200, 200);
			var z = rnd.nextFloat(-200, 200);
			var center = new Vector3f(x, y, z);
			attractorCenters.add(center);
		}
		
		int galaxyCount = rnd.nextInt(
			MINIMUM_GALAXY_COUNT,
			MAXIMUM_GALAXY_COUNT+1);
		
		for (var i=0; i<galaxyCount; i++) {
			var length = (float) Math.sqrt(rnd.nextDouble()) * 150;
			var azimut = rnd.nextFloat(0f, (float) Math.PI * 2);
			var elevation = rnd.nextFloat((float) -Math.PI, (float) Math.PI);
			var tieFactor = 0.2f;
			var seedLocation = MathUtil.sphericalToCartesian(new Vector3f(), length, azimut, elevation);
			var finalLocation = tieAttraction(seedLocation, attractorCenters, tieFactor);
			var finalSeed = generateDerivedSeed(seed, finalLocation);
			var galaxy = new GeneratedGalaxy(finalSeed, finalLocation, universe);
			universe.galaxy(galaxy);
		}
		
	}
	//=================================================================================================================

	//=================================================================================================================
	private String generateName(long seed) {
		return null;
	}
	//=================================================================================================================

	//=================================================================================================================
	private static Vector3f tieAttraction(Vector3f seedLocation, List<Vector3f> attractorCenters, float tieFactor) {
		
		Vector3f a = null;
		Vector3f b = null;
		Vector3f c = null;
		
		var da = Float.POSITIVE_INFINITY;
		for (var attractor : attractorCenters) {
			var d = new Vector3f();
			d.sub(seedLocation, attractor);
			var ds = d.lengthSquared();
			if (ds < da) {
				da = ds;
				a = attractor;
			}
		}

		var db = Float.POSITIVE_INFINITY;
		for (var attractor : attractorCenters) {
			var d = new Vector3f();
			d.sub(seedLocation, attractor);
			var ds = d.lengthSquared();
			if ((a != attractor) && (ds < db)) {
				db = ds;
				b = attractor;
			}
		}

		var dc = Float.POSITIVE_INFINITY;
		for (var attractor : attractorCenters) {
			var d = new Vector3f();
			d.sub(seedLocation, attractor);
			float ds = d.lengthSquared();
			if ((a != attractor) && (b != attractor) && (ds < dc)) {
				dc = ds;
				c = attractor;
			}
		}
		
		var dab = new Vector3f();
		var dac = new Vector3f();
		var n   = new Vector3f();
		
		dab.sub(b, a);
		dac.sub(c, a);
		n.cross(dab, dac);
		n.normalize();
		var d = a.dot(n);		
		var distance = seedLocation.dot(n) - d;
		
		var delta = new Vector3f();
		delta.scale(- distance * (1f-tieFactor), n);

		var result = new Vector3f();
		result.add(seedLocation, delta);
		
		return result;
	}
	//=================================================================================================================
		
}
//*********************************************************************************************************************
