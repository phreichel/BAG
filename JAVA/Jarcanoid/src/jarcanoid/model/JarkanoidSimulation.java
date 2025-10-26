//**************************************************************************************************
package jarcanoid.model;
//**************************************************************************************************

import jade.entity.Entity;
import jade.entity.MotionController;
import jade.entity.PoseComponent;
import jade.scene.Transform;
import jade.sim.Simulation;
import jarcanoid.entity.BlockComponent;
import jarcanoid.entity.BulletComponent;
import jarcanoid.entity.SliderComponent;
import jarcanoid.game.JarcanoidScene;
import lombok.Getter;

//**************************************************************************************************
public class JarkanoidSimulation extends Simulation {

	//==============================================================================================
	@Getter private MotionController camController = null;
	@Getter private LevelRuntime levelRuntime = null;
	@Getter private SliderComponent sliderComponent = null;
	//==============================================================================================

	//==============================================================================================
	public JarkanoidSimulation(JarcanoidScene scene, LevelRuntime levelRuntime) {
		createCameraControls(scene.getCameraTransform());
		createSliderControls(scene, levelRuntime);
		createBulletControls(scene, levelRuntime);
		createBlockControls(scene, levelRuntime);
	}
	//==============================================================================================

	//==============================================================================================
	private void createCameraControls(Transform camTransform) {
		Entity camViewer = new Entity();
		camController = new MotionController();
		PoseComponent camPose = new PoseComponent();
		camController.setPoseComponent(camPose);
		camPose.getPose().set(camTransform.getRelative());
		camPose.setTransform(camTransform);
		camViewer.add(camController);
		camViewer.add(camPose);
		this.addEntity(camViewer);
	}
	//==============================================================================================

	//==============================================================================================
	private void createSliderControls(JarcanoidScene scene, LevelRuntime levelRuntime) {
		Transform tx = scene.createSlider(levelRuntime);
		sliderComponent = new SliderComponent();
		sliderComponent.setRuntime(levelRuntime);
		sliderComponent.setTransform(tx);
		Entity sliderEntity = new Entity();
		sliderEntity.add(sliderComponent);
		addEntity(sliderEntity);
	}
	//==============================================================================================

	//==============================================================================================
	private void createBulletControls(JarcanoidScene scene, LevelRuntime levelRuntime) {
		for (Bullet bullet : levelRuntime.getBullets()) {
			Transform tx = scene.createBullet(levelRuntime, bullet);
			BulletComponent c = new BulletComponent();
			c.setRuntime(levelRuntime);
			c.setBullet(bullet);
			c.setTransform(tx);
			Entity entity = new Entity();
			entity.add(c);			
			addEntity(entity);
		}
	}
	//==============================================================================================

	//==============================================================================================
	private void createBlockControls(JarcanoidScene scene, LevelRuntime levelRuntime) {
		Level level = levelRuntime.getLevel();
		for (Block block : level.getBlocks()) {			
			Transform tx = scene.createBlock(level, block);
			BlockComponent c = new BlockComponent();
			c.setRuntime(levelRuntime);
			c.setBlock(block);
			c.setTransform(tx);
			Entity entity = new Entity();
			entity.add(c);			
			addEntity(entity);
		}
	}
	//==============================================================================================
	
}
//**************************************************************************************************


