//************************************************************************************************
package core.platform;
//************************************************************************************************

import javax.vecmath.Color3f;
import javax.vecmath.Color4f;

//************************************************************************************************
public interface IGraphics {

	//============================================================================================
	int getWidth();
	int getHeight();
	//============================================================================================

	//============================================================================================
	void setColor(Color3f color);
	void setColor(Color4f color);
	void setColor(float r, float g, float b);
	void setColor(float r, float g, float b, float a);
	//============================================================================================

	//============================================================================================
	void push();
	void pop();
	void translate(float dx, float dy);
	void rotate(float a);
	//============================================================================================
	
	//============================================================================================
	void drawPoints(float ... coords);
	void drawPolyline(float ... coords);
	void drawClosedPolyline(float ... coords);
	void drawPolygon(float ... coords);
	TextProbe probeText(String font, String text, TextProbe probe);
	void drawText(String font, String text, float x, float y);
	void startTextRaw(String font);
	void drawTextRaw(String text, float x, float y);
	void endTextRaw();
	//============================================================================================
	
}
//************************************************************************************************
