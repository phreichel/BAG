//**************************************************************************************************
package jade;
//**************************************************************************************************

import jade.entity.Entity;
import jade.entity.MotionController;
import jade.entity.PoseComponent;
import jade.scene.Transform;
import jade.sim.Simulation;
import lombok.Getter;

//**************************************************************************************************
public class ExampleSimulation extends Simulation {

	//==============================================================================================
	@Getter private MotionController camController = null;
	//==============================================================================================

	//==============================================================================================
	public ExampleSimulation(Transform camTransform) {
		create(camTransform);
	}
	//==============================================================================================
	
	//==============================================================================================
	private void create(Transform camTransform) {
		Entity camViewer = new Entity();
		camController = new MotionController();
		PoseComponent camPose = new PoseComponent();
		camController.setPoseComponent(camPose);
		camPose.setTransform(camTransform);
		camViewer.add(camController);
		camViewer.add(camPose);
		this.addEntity(camViewer);
	}
	//==============================================================================================

}
//**************************************************************************************************
