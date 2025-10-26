//*************************************************************************************************
package jlib.platform;
//*************************************************************************************************

import java.io.File;

import javax.vecmath.Color4f;
import javax.vecmath.Vector2f;

//*************************************************************************************************
public interface Graphics {

	//=============================================================================================
	public float width();
	public float height();
	//=============================================================================================
	
	//=============================================================================================
	public Color4f lineColor();
	public void lineColor(float r, float g, float b);
	public void lineColor(float r, float g, float b, float a);
	//=============================================================================================

	//=============================================================================================
	public Color4f fillColor();
	public void fillColor(float r, float g, float b);
	public void fillColor(float r, float g, float b, float a);
	//=============================================================================================
	
	//=============================================================================================
	public void push(float dx, float dy);
	public void pop();
	//=============================================================================================

	//=============================================================================================
	public void lineStrip(float ... xycoords);
	public void lineLoop(float ... xycoords);
	public void lineLoopFilled(float ... xycoords);
	public void box(float x1, float y1, float x2, float y2);
	public void boxFilled(float x1, float y1, float x2, float y2);
	public void oval(float x1, float y1, float x2, float y2);
	public void ovalFilled(float x1, float y1, float x2, float y2);
	//=============================================================================================

	//=============================================================================================
	public void textFontLoad(String name, String style);
	public void text(String font, float x, float y, String text);
	//=============================================================================================
	
	//=============================================================================================
	public void textureLoad(String name, File path);
	public void textureApply(String name, float x, float y);
	public void textureApply(String name, float x, float y, float scale);
	public void textureApply(String name, float x, float y, float scalex, float scaley);
	public void textureApply(String name, float x1, float y1, float x2, float y2, float tx1, float ty1, float tx2, float ty2);
	public Vector2f textureSize(String name, Vector2f size);
	//=============================================================================================

}
//*************************************************************************************************
