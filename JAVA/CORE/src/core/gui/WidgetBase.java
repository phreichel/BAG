//************************************************************************************************
package core.gui;
//************************************************************************************************

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import javax.vecmath.Vector2f;

//************************************************************************************************
public abstract class WidgetBase implements IWidget, IWidgetIntern {

	//============================================================================================
	private GuiManager         guiManager    = null;
	private IWidget            parent        = null;
	private final Set<GuiFlag> flags         = EnumSet.noneOf(GuiFlag.class);
	private final Set<GuiFlag> flagsReadonly = Collections.unmodifiableSet(flags);
	private final Vector2f     location      = new Vector2f();     
	private final Vector2f     outerExtent   = new Vector2f();     
	private final Vector2f     innerExtent   = new Vector2f();     
	private final Vector2f     borderOffset  = new Vector2f();     
	private       Insets4f     borderInsets  = Insets4f.NONE;     
	//============================================================================================

	//============================================================================================
	@Override
	public GuiManager getGuiManager() {
		return guiManager;
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void _setGuiManager(GuiManager guiManager) {
		this.guiManager = guiManager;
	}
	//============================================================================================

	//============================================================================================
	@Override
	public Set<GuiFlag> getFlags() {
		return this.flagsReadonly;
	}
	//============================================================================================
	
	//============================================================================================
	@Override
	public boolean hasFlags(GuiFlag ... flags) {
		return this.flags.containsAll(Arrays.asList(flags));
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void _setFlags(boolean state, GuiFlag ... flags) {
		if (state)
			this.flags.addAll(Arrays.asList(flags));
		else
			this.flags.removeAll(Arrays.asList(flags));
	}
	//============================================================================================
	
	//============================================================================================
	@Override
	public IWidget getParent() {
		return parent;
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void _setParent(IWidget parent) {
		this.parent = parent;
	}
	//============================================================================================

	//============================================================================================
	@Override
	public Vector2f getLocation() {
		return this.location;
	}
	//============================================================================================

	//============================================================================================
	@Override
	public Vector2f getBorderOffset() {
		return this.borderOffset;
	}
	//============================================================================================

	//============================================================================================
	@Override
	public Insets4f getBorderInsets() {
		return this.borderInsets;
	}
	//============================================================================================

	//============================================================================================
	@Override
	public Vector2f getOuterExtent() {
		return this.outerExtent;
	}
	//============================================================================================

	//============================================================================================
	@Override
	public Vector2f getInnerExtent() {
		return this.innerExtent;
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void _setLocation(Vector2f location) {
		_setLocation(location.x, location.y);
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void _setLocation(float x, float y) {
		this.location.set(x, y);
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void _setOuterExtent(Vector2f outerExtent) {
		_setOuterExtent(outerExtent.x, outerExtent.y);
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void _setOuterExtent(float extX, float extY) {
		this.outerExtent.set(extX, extY);
		this.innerExtent.set(
			extX - (borderInsets.left+borderInsets.right),
			extY - (borderInsets.bottom+borderInsets.top)
		);
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void _setInnerExtent(Vector2f innerExtent) {
		_setInnerExtent(innerExtent.x, innerExtent.y);
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void _setInnerExtent(float extX, float extY) {
		this.innerExtent.set(extX, extY);
		this.outerExtent.set(
			extX + (borderInsets.left+borderInsets.right),
			extY + (borderInsets.bottom+borderInsets.top)
		);
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void _setBorderInsets(Insets4f borderInsets) {
		this.borderInsets = borderInsets;
		this.borderOffset.set(this.borderInsets.left, this.borderInsets.bottom);
		this.innerExtent.set(
		    this.outerExtent.x - (borderInsets.left + borderInsets.right),
		    this.outerExtent.y - (borderInsets.top + borderInsets.bottom)
		);
	}
	//============================================================================================
	
}
//************************************************************************************************
