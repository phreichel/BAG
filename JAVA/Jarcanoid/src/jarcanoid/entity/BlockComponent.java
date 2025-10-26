//**************************************************************************************************
package jarcanoid.entity;
//**************************************************************************************************

import jade.entity.Component;
import jade.scene.Transform;
import jarcanoid.model.Block;
import jarcanoid.model.Bullet;
import jarcanoid.model.LevelRuntime;
import lombok.Getter;
import lombok.Setter;

//**************************************************************************************************
public class BlockComponent implements Component {

	//==============================================================================================
	private enum SIDE {
		NONE,
		LEFT,
		RIGHT,
		TOP,
		BOTTOM
	}
	//==============================================================================================
	
	//==============================================================================================
	@Getter @Setter private LevelRuntime runtime;
	@Getter @Setter private Block block;
	@Getter @Setter private Transform transform;
	@Getter @Setter private int hitCount = 0;
	//==============================================================================================

	//==============================================================================================
	private float nett = 0f;
	//==============================================================================================
	
	//==============================================================================================
	public String getKey() {
		return Component.KEY_BLOCK;
	}
	//==============================================================================================

	//==============================================================================================
	public void update(float dt) {
		
		if (hitCount >= block.getHitsToBreak()) { return; }
		
		for (Bullet bullet : runtime.getBullets()) {
			if (collides(bullet)) {
				hitCount++;
				bullet.getMomentum().scale(-1f);
				if (Math.random() > .5) {
					bullet.getMomentum().x *= -1f;
				}
				if (hitCount >= block.getHitsToBreak()) {
					transform.getParent().delChild(transform);
				}
			}
		}
		
		nett += dt;
		float dZ = (float) Math.sin(block.getX() + block.getY() + nett) * .001f;
		float dX = (float) Math.cos(block.getX() + block.getY() + nett) * .002f;
		transform.addTranslation(dX, 0f, dZ);
		
	}
	//==============================================================================================

	//==============================================================================================
	private boolean collides(Bullet bullet) {
		float x = bullet.getX(); 
		float y = bullet.getY(); 
		float r = bullet.getRadius();
		float bx = block.getX();
		float by = block.getY();
		float bw = block.getWidth();
		float bh = block.getHeight();
		return
			(x+r) > bx &&
			(x-r) < bx + bw &&
			(y+r) > by &&
			(y-r) < by + bh;
	}
	//==============================================================================================

	//==============================================================================================
	private SIDE collides(float x1, float y1, float x2, float y2, float bx1, float by1, float bx2, float by2) {
		
		float dx1 = (bx1-x1) / (x2-x1); 
		float dx2 = (bx2-x1) / (x2-x1); 
		float dy1 = (by1-y1) / (y2-y1); 
		float dy2 = (by2-y1) / (y2-y1); 
		
		float dist = Float.POSITIVE_INFINITY;
		SIDE side = SIDE.NONE;

		if (dx1 >= 0) {
			float ry1 = y1 + (y2-y1) * dx1;
			if (ry1 >= by1 && ry1 <= by2) {
				float d = (bx1-x1) * (bx1-x1) + (ry1-y1) * (ry1-y1);
				if (d < dist) {
					dist = d;
					side = SIDE.LEFT;
				}
			}
		}

		if (dx2 >= 0) {
			float ry2 = y1 + (y2-y1) * dx2;
			float d = (bx1-x1) * (bx1-x1) + (ry2-y1) * (ry2-y1);
			if (ry2 >= by1 && ry2 <= by2) {
				if (d < dist) {
					dist = d;
					side = SIDE.RIGHT;
				}
			}
		}

		if (dy1 >= 0) {
			float rx1 = x1 + (x2-x1) * dy1;
			float d = (rx1-x1) * (rx1-x1) + (by1-y1) * (by1-y1);
			if (rx1 >= bx1 && rx1 <= bx2) {
				if (d < dist) {
					dist = d;
					side = SIDE.RIGHT;
				}
			}
		}

		if (dy1 >= 0) {
			float rx2 = x1 + (x2-x1) * dy2;
			float d = (rx2-x1) * (rx2-x1) + (by2-y1) * (by2-y1);
			if (rx2 >= bx1 && rx2 <= bx2) {
				if (d < dist) {
					dist = d;
					side = SIDE.RIGHT;
				}
			}
		}
		
		return side;
		
	}
	//==============================================================================================

}
//**************************************************************************************************
