//*********************************************************************************************************************
package spark.scene;
//*********************************************************************************************************************

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

import spark.clock.Clock;
import spark.clock.Schedule;
import spark.platform.Action;
import spark.platform.Graphics;
import spark.platform.Input;

//*********************************************************************************************************************
public class Scene {

	//=================================================================================================================
	public Camera    camera = new Camera();
	public List<Box> galaxies = new ArrayList<>();
	public List<Box> stars  = new ArrayList<>();
	public Vector3f galaxyref = new Vector3f();
	private Schedule schedule;
	private float fwd   = 0f;
	private float rgt   = 0f;
	private float up    = 0f;
	private float pitch = 0f;
	private float yaw   = 0f;
	private float roll  = 0f;
	//=================================================================================================================

	//=================================================================================================================
	public Scene(Clock clock) {
		schedule = clock.create(100000L);
	}
	//=================================================================================================================
	
	//=================================================================================================================
	public void handleInput(Input input) {
		var pressed = input.action().equals(Action.TOGGLE_ARMED);
		var released = input.action().equals(Action.TOGGLE_RELEASED);
		if (!(pressed||released)) return;
		switch (input.toggle()) {
			case KEYBOARD_W -> {
				if (pressed) { fwd+=1f; }
				if (released) { fwd-=1f; }
			}
			case KEYBOARD_S -> {
				if (pressed) { fwd-=1f; }
				if (released) { fwd+=1f; }
			}
			case KEYBOARD_A -> {
				if (pressed) { rgt-=1f; }
				if (released) { rgt+=1f; }
			}
			case KEYBOARD_D -> {
				if (pressed) { rgt+=1f; }
				if (released) { rgt-=1f; }
			}
			case KEYBOARD_R -> {
				if (pressed) { up-=1f; }
				if (released) { up+=1f; }
			}
			case KEYBOARD_F -> {
				if (pressed) { up+=1f; }
				if (released) { up-=1f; }
			}
			case KEYBOARD_UP -> {
				if (pressed) { pitch+=1f; }
				if (released) { pitch-=1f; }
			}
			case KEYBOARD_DOWN -> {
				if (pressed) { pitch-=1f; }
				if (released) { pitch+=1f; }
			}
			case KEYBOARD_LEFT -> {
				if (pressed) { yaw+=1f; }
				if (released) { yaw-=1f; }
			}
			case KEYBOARD_RIGHT -> {
				if (pressed) { yaw-=1f; }
				if (released) { yaw+=1f; }
			}
			case KEYBOARD_Q -> {
				if (pressed) { roll+=1f; }
				if (released) { roll-=1f; }
			}
			case KEYBOARD_E -> {
				if (pressed) { roll-=1f; }
				if (released) { roll+=1f; }
			}
			default -> {}
		}
	}
	//=================================================================================================================
	
	//=================================================================================================================
	public void render(Graphics g) {

		g.push();
		Vector3f tmp = new Vector3f(camera.location);
		camera.location.set(galaxyref);		
		camera.render(g);
		g.color(.5f, 0f, 0f);
		for (var galaxy : galaxies) {
			galaxy.render(g);
		}
		g.pop();
		
		g.push();
		camera.location.set(tmp);
		camera.render(g);
		g.color(.5f, 1f, .5f);
		for (var star : stars) {
			star.render(g);
		}
		g.pop();
		
	}
	//=================================================================================================================

	//=================================================================================================================
	public void update() {
		
		if (!schedule.check()) return;
		
		float time = schedule.delta();
		
		float speed = 5f;
		float turn  = 1f / (float) Math.PI;

		float dpitch = - pitch * time * turn;
		float dyaw   = - yaw   * time * turn;
		float droll  = - roll  * time * turn;
		
		float dfwd   = fwd * time * speed;
		float drgt   = rgt * time * speed;
		float dup    = up  * time * speed;

        Matrix3f pitchMatrix = new Matrix3f();
        pitchMatrix.rotX(dpitch);

        Matrix3f yawMatrix = new Matrix3f();
        yawMatrix.rotY(dyaw);
        
        Matrix3f rollMatrix = new Matrix3f();
        rollMatrix.rotZ(droll);

        Matrix3f matrix = new Matrix3f();
        matrix.setIdentity();
        matrix.mul(yawMatrix);
        matrix.mul(pitchMatrix);
        matrix.mul(rollMatrix);
        matrix.mul(camera.orientation);

        camera.orientation.set(matrix);
        
        Vector3f translation = new Vector3f(drgt, -dup, -dfwd);
        matrix.transpose();
        matrix.transform(translation);

		camera.location.add(translation);
		
	}
	//=================================================================================================================
	
}
//*********************************************************************************************************************
