//**************************************************************************************************
package jarcanoid.entity;

import jade.entity.Component;
import jade.scene.Transform;
import jarcanoid.model.Level;
import jarcanoid.model.LevelRuntime;
import jarcanoid.model.Slider;
import lombok.Getter;
import lombok.Setter;

//**************************************************************************************************

//**************************************************************************************************
public class SliderComponent implements Component {

	//==============================================================================================
	@Getter @Setter private LevelRuntime runtime;
	@Getter @Setter private Transform transform;
	@Getter @Setter private boolean sticky = true;
	@Getter @Setter private float speed = 50f;
	@Getter @Setter private boolean left = false;
	@Getter @Setter private boolean right = false;
//	@Getter @Setter private boolean up = false;
//	@Getter @Setter private boolean down = false;
	//==============================================================================================

	//==============================================================================================
	public String getKey() {
		return Component.KEY_SLIDER;
	}
	//==============================================================================================
	
	//==============================================================================================
	public void update(float dt) {

		float dhorz = 0f;
//		float dvert = 0f;
		
		if (left) dhorz -= speed * dt;
		if (right) dhorz += speed * dt;
		
//		if (up) dvert += speed * dt;
//		if (down) dvert -= speed * dt;
		
		Level level = runtime.getLevel();
		Slider slider = runtime.getSlider();
		
		float newX = slider.getX() + dhorz;
//		float newY = slider.getY() + dvert;

		newX = Math.max(newX, 0f);
		newX = Math.min(newX, level.getWidth() - slider.getWidth()); 

//		newY = Math.max(newX, 0f);
//		newY = Math.min(newX, level.getHeight() - );
		
		slider.setX(newX);
		
		updateSliderInScene();
		
	}
	//==============================================================================================

	//==============================================================================================
	private void updateSliderInScene() {
		Level level = runtime.getLevel();
		Slider slider = runtime.getSlider();
		float dx = - level.getWidth() / 2f ;
		float dy = - level.getHeight() / 2f ;
		transform.setTranslation(dx + slider.getX() + slider.getWidth() / 2f, dy + slider.getY() + slider.getHeight() / 2f, 0f);
	}
	//==============================================================================================
	
}
//**************************************************************************************************
