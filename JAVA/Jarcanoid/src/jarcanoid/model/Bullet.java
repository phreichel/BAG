//**************************************************************************************************
package jarcanoid.model;

import javax.vecmath.Vector2f;

import lombok.Getter;
import lombok.Setter;

//**************************************************************************************************

//**************************************************************************************************
public class Bullet {

	//==============================================================================================
	@Setter @Getter private float x;
	@Setter @Getter private float y;
	@Setter @Getter private float radius;
	@Setter @Getter private Vector2f momentum = null;	
	//==============================================================================================

	//==============================================================================================
	public void setup(Slider slider) {

		radius = .5f;
		
		this.x = slider.getX() + slider.getWidth() / 2f;
		this.y = slider.getY() + slider.getHeight() + radius;
		
		float angle = (float) Math.toRadians((Math.random() * 90) + 45);
		float sin = (float) Math.sin(angle);
		float cos = (float) Math.cos(angle);
		momentum = new Vector2f(cos, sin);
		momentum.scale(50f);
		
	}
	//==============================================================================================

}
//**************************************************************************************************
