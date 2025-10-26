//*********************************************************************************************************************
package spark.gui;
//*********************************************************************************************************************

import spark.platform.Graphics;

//*********************************************************************************************************************
public interface Renderer {

	//=================================================================================================================
	public void render(Component c, Graphics g);
	//=================================================================================================================

	//=================================================================================================================
	public static void DEFAULT(Component c, Graphics g) {
		
		if (!c.state(State.DISPLAYED)) return;
		
		var w = c.width();
		var h = c.height();
				
		g.color(1f, 0f, 0f, .4f);
		if (c.state(State.HOVERED)) g.color(1f, 0f, 0f, .6f);
		if (c.state(State.ARMED)) g.color(1f, 0f, 0f, 1f);
		if (c.state(State.ACTIVATED)) g.color(0f, 0f, 1f, 1f);
		g.fillBox(0, 0, w, h);
		
		g.color(1f, 0f, 0f);
		g.drawBox(0, 0, w, h);

		var text = (String) c.property(Property.TEXT);
		if (text == null) return;
		g.color(1f, 1f, 1f);
		g.font("DEFAULT");
		var textSize = g.textSize(text);
		g.text(
			(c.width() - textSize.width) * .5f,
			(c.height() - textSize.height + textSize.baseline) * .5f - c.height(),
			text);
		
	}
	//=================================================================================================================

	//=================================================================================================================
	public static void BUTTON(Component c, Graphics g) {
		
		if (!c.state(State.DISPLAYED)) return;
		
		var w = c.width();
		var h = c.height();
				
		g.color(1f, 0f, 0f, .4f);
		if (c.state(State.HOVERED)) g.color(1f, 0f, 0f, .6f);
		if (c.state(State.ARMED)) g.color(1f, 0f, 0f, 1f);
		if (c.state(State.ACTIVATED)) g.color(0f, 0f, 1f, 1f);
		g.fillBox(0, 0, w, h);
		
		g.color(1f, 0f, 0f);
		g.drawBox(0, 0, w, h);

		var text = (String) c.property(Property.TEXT);
		if (text == null) return;
		g.color(1f, 1f, 1f);
		g.font("Button");
		var textSize = g.textSize(text);
		g.text(
			(c.width() - textSize.width) * .5f,
			(c.height() - textSize.height + textSize.baseline) * .5f - c.height(),
			text);
		
	}
	//=================================================================================================================
	
	//=================================================================================================================
	public static void GROUP(Component c, Graphics g) {

		if (!c.state(State.DISPLAYED)) return;
		
		var w = c.width();
		var h = c.height();
				
		g.color(1f, 0f, .2f, .3f);
		g.fillBox(0, 0, w, h);
		
		g.color(1f, 0f, .2f);
		g.drawBox(0, 0, w, h);
		
		var children = (Container) c.property(Property.CHILDREN);
		children.render(g);
		
	}
	//=================================================================================================================
	
}
//*********************************************************************************************************************
