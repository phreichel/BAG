//************************************************************************************************
package core.gui;
//************************************************************************************************

import javax.vecmath.Vector2f;

//************************************************************************************************
public interface IWidgetIntern {

	//============================================================================================
	public void _setGuiManager(GuiManager guiManager);
	public void _setParent(IWidget parent);
	public void _setFlags(boolean state, GuiFlag ... flags);
	public void _setLocation(Vector2f location);
	public void _setLocation(float x, float y);
	public void _setOuterExtent(Vector2f outerExtent);
	public void _setOuterExtent(float extX, float extY);
	public void _setInnerExtent(Vector2f outerExtent);
	public void _setInnerExtent(float extX, float extY);
	public void _setBorderInsets(Insets4f borderInsets);
	//============================================================================================

}
//************************************************************************************************
