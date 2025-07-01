//**************************************************************************************************
package jade;
//**************************************************************************************************

import jade.scene.Box;
import jade.scene.Camera;
import jade.scene.Scene;
import jade.scene.Sphere;
import jade.scene.Transform;
import lombok.Getter;

//**************************************************************************************************
public class ExampleScene extends Scene {

	//==============================================================================================
	@Getter private Transform cameraTransform = null;
	//==============================================================================================
	
	//==============================================================================================
	public ExampleScene() {
		createScene();
	}
	//==============================================================================================

	//==============================================================================================
	private void createScene() {

		Box box = new Box();
		box.setColor(.6f, .5f, .3f);
		Transform boxTransform = new Transform();
		boxTransform.getRelative().rotZ((float)Math.toRadians(-30f));
		boxTransform.setTranslation(2, 1, -5);
		boxTransform.addChild(box);
		
		Box camBox = new Box();
		camBox.setColor(.3f, .3f, 0f);
		Transform camBoxTransform = new Transform();
		camBoxTransform.getRelative().rotZ((float)Math.toRadians(30f));
		camBoxTransform.setTranslation(-2, 1, -5);
		camBoxTransform.addChild(camBox);

		Camera camera = new Camera();
		cameraTransform = new Transform();
		cameraTransform.setTranslation(-2, -3, 0);
		cameraTransform.addChild(camera);
		cameraTransform.addChild(camBoxTransform);

		Sphere sphere = new Sphere();
		sphere.setRadius(1f);
		sphere.setColor(.7f, 0f, 0f);
		Transform sphereTransform = new Transform();
		sphereTransform.setTranslation(0, 0, -10);
		sphereTransform.addChild(sphere);
		
		addChild(cameraTransform);
		addChild(sphereTransform);
		addChild(boxTransform);
		setCamera(camera);
		
	}
	//==============================================================================================
	
}
//**************************************************************************************************
