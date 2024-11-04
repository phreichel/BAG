//*********************************************************************************************************************
package spark.platform;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

//*********************************************************************************************************************

//*********************************************************************************************************************
public interface Graphics {

	//=================================================================================================================
	public void setup2D();
	public void setup3D();
	public void push();
	public void push(float dx, float dy);
	public void pop();
	//=================================================================================================================
	
	//=================================================================================================================
	public float colorChannel(ColorChannel channel);
	public void color(float r, float g, float b);
	public void color(float r, float g, float b, float a);
	public void materialAmbient(float r, float g, float b, float a);
	public void materialDiffuse(float r, float g, float b, float a);
	public void materialEmission(float r, float g, float b, float a);
	public void materialSpecular(float r, float g, float b, float a);
	public void materialShininess(float s);
	//=================================================================================================================
	
	//=================================================================================================================
	public void drawLine(float ... coords);
	public void drawClosedLine(float ... coords);
	public void drawBox(float x1, float y1, float x2, float y2);
	public void drawCircle(float x, float y, float radius, int segments);
	public void drawCircleArc(float x, float y, float radius, float start, float end, int segments, boolean pie);
	public void drawOval(float x1, float y1, float x2, float y2, int segments);
	public void drawOval(float x, float y, float rh, float rv, float angle, int segments);
	public void drawOvalArc(float x, float y, float rh, float rv, float angle, float start, float end, int segments, boolean pie);
	//=================================================================================================================

	//=================================================================================================================
	public void fillPolygon(float ... coords);
	public void fillBox(float x1, float y1, float x2, float y2);
	public void fillCircle(float x, float y, float radius, int segments);
	public void fillCircleArc(float x, float y, float radius, float start, float end, int segments);
	public void fillOval(float x1, float y1, float x2, float y2, int segments);
	public void fillOval(float x, float y, float rh, float rv, float angle, int segments);
	public void fillOvalArc(float x, float y, float rh, float rv, float angle, float start, float end, int segments);
	//=================================================================================================================

	//=================================================================================================================
	public String font();
	public void font(String name);
	public void fontCreate(String name, String style);
	public void fontDestroy(String name);
	//=================================================================================================================

	//=================================================================================================================
	public TextSize textSize(String text);
	public void text(float x, float y, String text);
	//=================================================================================================================

	//=================================================================================================================
	public void place3DCamera(Vector3f location, Matrix3f orientation);
	public void draw3DPoint(float x, float y, float z);
	public void draw3DBox(float x1, float y1, float z1, float x2, float y2, float z2);
	//=================================================================================================================
	
}
//*********************************************************************************************************************
