//**************************************************************************************************
package jarcanoid.game;
//**************************************************************************************************

import jade.scene.Box;
import jade.scene.Camera;
import jade.scene.Node;
import jade.scene.Scene;
import jade.scene.Sphere;
import jade.scene.Transform;
import jarcanoid.model.Block;
import jarcanoid.model.Bullet;
import jarcanoid.model.Level;
import jarcanoid.model.LevelRuntime;
import jarcanoid.model.Slider;
import lombok.Getter;

//**************************************************************************************************
public class JarcanoidScene extends Scene {

	//==============================================================================================
	@Getter private Transform cameraTransform = null;
	//==============================================================================================
	
	//==============================================================================================
	public void createScene(LevelRuntime levelRuntime) {
		Level level = levelRuntime.getLevel();
		Node camera  = createCamera(level);
		Node borders = createBorders(level);
		this.addChild(camera);
		this.addChild(borders);
	}
	//==============================================================================================

	//==============================================================================================
	private Node createCamera(Level level) {

		Camera camera = new Camera();
		this.setCamera(camera);
		cameraTransform = new Transform();
		cameraTransform.setTranslation(0f, -level.getHeight() * 0.6f, level.getHeight() * .5f);
		cameraTransform.addChild(camera);
		
		return cameraTransform; 

	}
	//==============================================================================================

	//==============================================================================================
	private Node createBorders(Level level) {
		
		Box basePlate = new Box();
		basePlate.setColor(0f, 0f, .5f);
		basePlate.setSize(level.getWidth(), level.getHeight(), 1f);
		Transform basePlateTx = new Transform();
		basePlateTx.setTranslation(0f, 0f, -2.5f);
		basePlateTx.addChild(basePlate);
		
		Box topBorder = new Box();
		topBorder.setColor(.5f, 0f, .2f);
		topBorder.setSize(level.getWidth() + 10f, 5f, 2f);
		Transform topBorderTx = new Transform();
		topBorderTx.setTranslation(0f, (level.getHeight() + 5f) / 2f, 0f);
		topBorderTx.addChild(topBorder);
		
		Box leftBorder = new Box();
		leftBorder.setColor(.5f, 0f, .2f);
		leftBorder.setSize(5f, level.getHeight(), 2f);
		Transform leftBorderTx = new Transform();
		leftBorderTx.setTranslation(-(level.getWidth() + 5f) / 2f, 0f, 0f);
		leftBorderTx.addChild(leftBorder);

		Box rightBorder = new Box();
		rightBorder.setColor(.5f, 0f, .2f);
		rightBorder.setSize(5f, level.getHeight(), 2f);
		Transform rightBorderTx = new Transform();
		rightBorderTx.setTranslation(+(level.getWidth() + 5f) / 2f, 0f, 0f);
		rightBorderTx.addChild(rightBorder);

		Transform bordersTx = new Transform();
		bordersTx.addChild(basePlateTx);
		bordersTx.addChild(topBorderTx);
		bordersTx.addChild(leftBorderTx);
		bordersTx.addChild(rightBorderTx);
		
		return bordersTx;
		
	}
	//==============================================================================================

	//==============================================================================================
	public Transform createSlider(LevelRuntime levelRuntime) {
		Level level = levelRuntime.getLevel();
		Slider slider = levelRuntime.getSlider();
		Box sliderBox = new Box();
		sliderBox.setColor(1f, .5f, 0f);
		sliderBox.setSize(slider.getWidth(), slider.getHeight(), 1.5f);
		Transform sliderTx = new Transform();
		float dx = - (level.getWidth() - slider.getWidth()) / 2f ;
		float dy = - (level.getHeight() - slider.getHeight()) / 2f ;
		sliderTx.setTranslation(dx + slider.getX(), dy + slider.getY(), 0);
		sliderTx.addChild(sliderBox);
		addChild(sliderTx);
		return sliderTx;
	}
	//==============================================================================================
	
	//==============================================================================================
	public Transform createBullet(LevelRuntime levelRuntime, Bullet bullet) {
		Level level = levelRuntime.getLevel();
		Sphere bulletSphere = new Sphere();
		bulletSphere.setRadius(bullet.getRadius());
		bulletSphere.setColor(1f, 0f, 1f);
		Transform bulletTx = new Transform();
		bulletTx.addChild(bulletSphere);
		float dx = - level.getWidth() / 2f ;
		float dy = - level.getHeight() / 2f ;
		bulletTx.setTranslation(dx + bullet.getX(), dy + bullet.getY(), 0);
		addChild(bulletTx);
		return bulletTx;
		
	}
	//==============================================================================================
	
	//==============================================================================================
	public Transform createBlock(Level level, Block block) {
		Box blockBox = new Box();
		blockBox.setColor(0f, .8f, 0f);
		blockBox.setSize(block.getWidth() * .9f, block.getHeight() * .9f, 2f * .9f);
		Transform blockTx = new Transform();
		float dx = - (level.getWidth() - block.getWidth()) / 2f ;
		float dy = - (level.getHeight() - block.getHeight()) / 2f ;
		blockTx.setTranslation(dx + block.getX(), dy + block.getY(), 0);
		blockTx.addChild(blockBox);
		addChild(blockTx);
		return blockTx; 
	}
	//==============================================================================================
	
}
//**************************************************************************************************
