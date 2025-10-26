//*************************************************************************************************
package rp.surface;
//*************************************************************************************************

import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Vector2f;

//*************************************************************************************************
public interface Graphics {

	//=============================================================================================
	public float red();
	public float green();
	public float blue();
	public float alpha();
	public Color4f color();
	public void color(Color3f color);
	public void color(Color4f color);
	public void color(float red, float green, float blue);
	public void color(float red, float green, float blue, float alpha);
	//=============================================================================================

	//=============================================================================================
	public void point(Vector2f position);
	public void point(float x, float y);
	//=============================================================================================

	//=============================================================================================
	public void line(Vector2f a, Vector2f b);
	public void line(boolean closed, Vector2f ... positions);
	public void line(float ax, float ay, float bx, float by);
	//=============================================================================================

	//=============================================================================================
	public void box(Vector2f position, Vector2f size);
	public void box(float x, float y, float width, float height);
	//=============================================================================================
	
}
//*************************************************************************************************
