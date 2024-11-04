//*********************************************************************************************************************
package spark.gui;
//*********************************************************************************************************************

import spark.platform.Graphics;

//*********************************************************************************************************************
public interface Layout {

	//=================================================================================================================
	public void layout(Component source, Graphics graphics);
	//=================================================================================================================

	//=================================================================================================================
	public static void LABEL(Component source, Graphics graphics) {
		String text = (String) source.property(Property.TEXT);
		if (text == null) text = "";
		graphics.font("Label");
		var textSize = graphics.textSize(text);
		source.size(textSize.width, textSize.height);
	}
	//=================================================================================================================

	//=================================================================================================================
	public static void BUTTON(Component source, Graphics graphics) {
		String text = (String) source.property(Property.TEXT);
		if (text == null) text = "";
		graphics.font("Button");
		var textSize = graphics.textSize(text);
		source.size(textSize.width + 2 * 5, textSize.height + 2 * 5);
	}
	//=================================================================================================================

	//=================================================================================================================
	public static void HORIZONTAL(Component source, Graphics graphics) {
		
		source.size(25, 25);
		
		Container children = (Container) source.property(Property.CHILDREN);
		if (children == null) return;
		
		float sumX = 0;
		float maxY = 0;
		for (int i=0; i<children.items(); i++) {
			var child = children.item(i);
			sumX += child.width();
			maxY = Math.max(maxY, child.height());
		}
		
		float sizeX = sumX + (children.items() + 1) * 5;
		float sizeY = maxY + 2 * 5;
		source.size(sizeX, sizeY);
		
		float offsetX = 5;
		for (int i=0; i<children.items(); i++) {
			var child = children.item(i);
			float offsetY = (sizeY - child.height()) * .5f;
			child.position(offsetX, offsetY);
			offsetX += child.width();
			offsetX += 5;
		}
		
	}
	//=================================================================================================================

	//=================================================================================================================
	public static void VERTICAL(Component source, Graphics graphics) {

		source.size(25, 25);
		
		Container children = (Container) source.property(Property.CHILDREN);
		if (children == null) return;
		
		float maxX = 0;
		float sumY = 0;
		for (int i=0; i<children.items(); i++) {
			var child = children.item(i);
			maxX = Math.max(maxX, child.width());
			sumY += child.height();
		}
		
		float sizeX = maxX + 2 * 5;
		float sizeY = sumY + (children.items() + 1) * 5;
		source.size(sizeX, sizeY);
		
		float offsetY = 5;
		for (int i=0; i<children.items(); i++) {
			var child = children.item(i);
			float offsetX = (sizeX - child.width()) * .5f;
			child.position(offsetX, offsetY);
			offsetY += child.height();
			offsetY += 5;
		}
		
	}
	//=================================================================================================================
	
}
//*********************************************************************************************************************
