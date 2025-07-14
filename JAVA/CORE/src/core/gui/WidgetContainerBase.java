//************************************************************************************************
package core.gui;
//************************************************************************************************

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.event.GameEvent;
import core.platform.IGraphics;

//************************************************************************************************
public abstract class WidgetContainerBase extends WidgetBase implements IWidgetContainer, IWidgetContainerIntern {

	//============================================================================================
	private final List<IWidget> children = new ArrayList<>();
	private final List<IWidget> childrenReadonly = Collections.unmodifiableList(children);
	//============================================================================================

	//============================================================================================
	@Override
	public int getChildCount() {
		return children.size();
	}
	//============================================================================================

	//============================================================================================
	@Override
	public List<IWidget> getChildren() {
		return childrenReadonly;
	}
	//============================================================================================

	//============================================================================================
	@Override
	public IWidget getChild(int idx) {
		return children.get(idx);
	}
	//============================================================================================
	
	//============================================================================================
	@Override
	public void _addChild(IWidget child) {
		if (checkCanAdd(child)) {
			_addChild(children.size(), child);
		}
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void _addChild(int idx, IWidget child) {
		if (checkCanAdd(child)) {
			var _child = (IWidgetIntern) child;
			var _parent = (IWidgetContainerIntern) child.getParent();
			if (_parent == this) return;
			if (_parent != null) _parent._removeChild(child);
			_child._setParent(this);
			children.add(idx, child);
			_setLayoutDirty(true);
		}
	}
	//============================================================================================

	//============================================================================================
	private boolean checkCanAdd(IWidget child) {

		if (child == null) return false;
		var ancestor = (IWidget) this;
		while (ancestor != null) {
			if (ancestor == child) return false;
			ancestor = ancestor.getParent();
		}
		return true;
	}
	//============================================================================================
	
	//============================================================================================
	@Override
	public boolean _removeChild(IWidget child) {
		if (children.remove(child)) {
			var _child = (IWidgetIntern) child;
			_child._setParent(null);
			_setLayoutDirty(true);
			return true;
		}
		return false;
	}
	//============================================================================================

	//============================================================================================
	@Override
	public IWidget _removeChild(int idx) {
		var child = children.remove(idx);
		var _child = (IWidgetIntern) child;
		_child._setParent(null);
		_setLayoutDirty(true);
		return child; 
	}
	//============================================================================================

	//============================================================================================
	@Override
	public boolean _relocateChild(int fromIdx, int toIdx) {
		var child = children.remove(fromIdx);
		if (child == null) return false;
		children.add(toIdx, child);
		_setLayoutDirty(true);
		return true;
	}
	//============================================================================================

	//============================================================================================
	@Override
	public boolean _relocateChild(IWidget child, int toIdx) {
		if (children.remove(child)) {
			children.add(toIdx, child);
			_setLayoutDirty(true);
			return true;
		}
		return false;
	}
	//============================================================================================

	//============================================================================================
	@Override
	public boolean _relocateChild(IWidget child, IWidget before) {
		if (children.remove(child)) {
			int idx = children.indexOf(before);
			if (idx == -1) return false;
			children.add(idx, child);
			_setLayoutDirty(true);
			return true;
		}
		return false;
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void onPaint(IGraphics graphics) {
		graphics.push();
		onPaintWidget(graphics);
		graphics.pop();
		for (var child : getChildren()) {
			graphics.push();
			graphics.translate(
				child.getLocation().x,
				child.getLocation().y
			);
			child.onPaint(graphics);
			graphics.pop();
		}
	}
	//============================================================================================

	//============================================================================================
	@Override
	public void update(int nFrames, long periodNs) {
		updateWidget(nFrames, periodNs);
		for (var child : getChildren()) {
			child.update(nFrames, periodNs);
		}
	}
	//============================================================================================

	//============================================================================================
	protected void updateWidget(int nFrames, long periodNs) {}
	//============================================================================================
	
	//============================================================================================
	protected void onPaintWidget(IGraphics graphics) {
		
	}
	//============================================================================================
	
	//============================================================================================
	@Override
	public void onGameEvent(GameEvent e) {
		onEventBeforeChildren(e);
		for (var child : getChildren()) {
			child.onGameEvent(e);
		}
		onEventAfterChildren(e);
	}
	//============================================================================================

	//============================================================================================
	protected void onEventBeforeChildren(GameEvent e) {
		
	}
	//============================================================================================

	//============================================================================================
	protected void onEventAfterChildren(GameEvent e) {
		
	}
	//============================================================================================
	
	//============================================================================================
	@Override
	public void updateLayout() {
		super.updateLayout();
		for (var child : getChildren()) {
			child.updateLayout();
		}
		
	}
	//============================================================================================
	
}
//************************************************************************************************
