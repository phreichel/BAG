//**************************************************************************************************
package jarcanoid.entity;
//**************************************************************************************************

import jade.entity.Component;
import jade.scene.Transform;
import jarcanoid.model.Bullet;
import jarcanoid.model.Level;
import jarcanoid.model.LevelRuntime;
import jarcanoid.model.Slider;
import lombok.Getter;
import lombok.Setter;

//**************************************************************************************************
public class BulletComponent implements Component {

	//==============================================================================================
	@Getter @Setter private LevelRuntime runtime;
	@Getter @Setter private Bullet bullet;
	@Getter @Setter private Transform transform;
	//==============================================================================================

	//==============================================================================================
	private boolean glued = true;
	private float wait = 0f;
	//==============================================================================================
	
	//==============================================================================================
	public String getKey() {
		return Component.KEY_BULLET;
	}
	//==============================================================================================

	//==============================================================================================
	public void update(float dt) {

		Slider slider = runtime.getSlider();
		
		if (glued) {
			bullet.reset(slider);
			wait += dt;
			if (wait >= 5f) {
				glued = false;
			}
		} else {
		
			float x = bullet.getX();
			float y = bullet.getY();
			
			Level level = runtime.getLevel();
	
			if (y  <= -10) {
				reset();
			} else {
				
				if ((x <= bullet.getRadius()) || (x >= level.getWidth() - bullet.getRadius())) { bullet.getMomentum().x *= -1f; }
				if ((y >= level.getHeight() - bullet.getRadius())) { bullet.getMomentum().y *= -1f; }
				if (
						(x >= slider.getX()) &&
						(x <= slider.getX()+slider.getWidth()) &&
						(y <= slider.getY()+slider.getHeight()*.5f+bullet.getRadius()) &&
						(y >= -5)
				) {
					bullet.getMomentum().y *= -1f;
				}
				
				float dx = bullet.getMomentum().x * dt;
				float dy = bullet.getMomentum().y * dt;
				
				bullet.setX(x + dx);
				bullet.setY(y + dy);
			}
		}
		
		updateBulletInScene();

	}
	//==============================================================================================

	//==============================================================================================
	private void updateBulletInScene() {
		Level level = runtime.getLevel();
		float dx = - level.getWidth() / 2f ;
		float dy = - level.getHeight() / 2f ;
		transform.setTranslation(dx + bullet.getX(), dy + bullet.getY(), 0f);
	}
	//==============================================================================================

	//==============================================================================================
	private void reset() {
		
		glued = true;
		wait = 0f;
		
		Level  level  = runtime.getLevel();
		Slider slider = runtime.getSlider();
		Bullet bullet = runtime.getBullets().get(0);
		runtime.getBullets().clear();
		runtime.getBullets().add(bullet);
		slider.reset(level.getWidth());
		bullet.reset(slider);
		
	}
	//==============================================================================================
	
}
//**************************************************************************************************
