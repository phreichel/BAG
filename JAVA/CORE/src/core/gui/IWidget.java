//************************************************************************************************
package core.gui;
//************************************************************************************************

import java.util.Set;

import javax.vecmath.Vector2f;
import core.api.ICanvas;
import core.api.IGameHandler;

//************************************************************************************************
public interface IWidget extends ICanvas, IGameHandler {

	//============================================================================================
	public GuiManager   getGuiManager();
	public Set<GuiFlag> getFlags();
	public boolean      hasFlags(GuiFlag ... flags);
	public IWidget      getParent();
	public Vector2f     getLocation();
	public Vector2f     getBorderOffset();
	public Insets4f     getBorderInsets();
	public Vector2f     getOuterExtent();
	public Vector2f     getInnerExtent();
	//============================================================================================

}
//************************************************************************************************
