//****************************************************************************
package spark.particle;
//****************************************************************************

import java.util.List;
import javax.vecmath.Vector3f;
import java.util.ArrayList;

//****************************************************************************
public class ParticleSystem {

    //=======================================================================
    private List<Spawner>  spawners;
    private List<Particle> particles;
    private List<Particle> cache;
    //=======================================================================

    //=======================================================================
    public ParticleSystem() {
    
        spawners = new ArrayList<>();
        particles = new ArrayList<>();
        cache = new ArrayList<>();

    }
    //=======================================================================

    //=======================================================================
    public void addSpawner(Spawner spawner) {
        spawner.setSystem(this);
        spawners.add(spawner);
    }
    //=======================================================================
    
    //=======================================================================
    public void update(float dT) {
        for (var spawner : spawners) {
            spawner.update(dT);
        }
        for (var particle : particles) {
            particle.update(dT);
        }
    }
    //=======================================================================

    //=======================================================================
    public Particle spawn(
            int type,
            Vector3f location,
            Vector3f velocity,
            int generation) {
        var particle = cache.remove(0);
        if (particle == null)
            particle = new Particle();
        particles.add(particle);
        particle.type = type;
        particle.time = 0f;
        particle.location.set(location);
        particle.velocity.set(velocity);
        particle.generation = generation;
        particle.children = 0;
        return particle;
    }
    //=======================================================================

    //=======================================================================
    public void despawn(Particle particle) {
        particles.remove(particle);
        cache.add(particle);
    }
    //=======================================================================

};
//****************************************************************************
