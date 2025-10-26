//*****************************************************************************
package gameengine.widget.layout;
//*****************************************************************************

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.vecmath.Vector2f;
import gameengine.client.Surface;
import gameengine.widget.SplitNode;
import gameengine.widget.Widget;
import gameengine.widget.SplitNode.Direction;

//*****************************************************************************
public class SplitLayout extends Layout {

	//=========================================================================
	private SplitNode root = null;
	//=========================================================================

	//=========================================================================
	public SplitNode getRoot() {
		return root;
	}
	//=========================================================================

	//=========================================================================
	public void setRoot(SplitNode root) {
		this.root = root;
	}
	//=========================================================================
	
	//=========================================================================
	@Override
	public void validate(Widget widget, Surface surface) {
		Set<Widget> widgets = new HashSet<>();
		validateNodes(widgets, root);
		var list = new ArrayList<Widget>(widget.getChildren());
		for (var child : list) {
			if (!widgets.contains(child)) {
				child.setParent(null);
			}
		}
		for (var newChild : widgets) {
			if (!list.contains(newChild)) {
				newChild.setParent(widget);
			}
		}
	}
	//=========================================================================

	//=========================================================================
	private void validateNodes(Set<Widget> widgets, SplitNode node) {
		if (node == null) return;
		if (node.widget != null) {
			widgets.add(node.widget);
			if ((node.leading != null) || (node.trailing != null)) {
				throw new RuntimeException("Split Layout Leaf Node has invalid node children");
			}
		} else {
			if ((node.leading == null) && (node.trailing == null)) {
				throw new RuntimeException("Split Layout Node is empty");
			}
			validateNodes(widgets, node.leading);
			validateNodes(widgets, node.trailing);
		}
	}
	//=========================================================================
	
	//=========================================================================
	public void calcMinExtent(Widget widget, Surface surface) {
		if (!isDirty()) return;
		var minExtent = new Vector2f(widget.getMinExtent());
		var minExtentNode = calcMinExtentNode(root);
		minExtent.x = Math.max(minExtent.x, minExtentNode.x);
		minExtent.y = Math.max(minExtent.y, minExtentNode.y);
		widget.setMinExtent(minExtent);
	}
	//=========================================================================
	
	//=========================================================================
	private Vector2f calcMinExtentNode(SplitNode node) {
		
		if (node == null) return new Vector2f(0f, 0f);

		if (node.widget != null) {
			return new Vector2f(node.widget.getMinExtent());
		}

		Vector2f leading = calcMinExtentNode(node.leading);
		Vector2f trailing = calcMinExtentNode(node.trailing);
		Vector2f result = new Vector2f();

		switch (node.direction) {
			case HORIZONTAL -> {
				// Aufteilung: oben / unten → übereinander
				result.x = Math.max(leading.x, trailing.x); // Breite: größtes Kind
				result.y = leading.y + trailing.y;          // Höhe: Summe
			}
			case VERTICAL -> {
				// Aufteilung: links / rechts → nebeneinander
				result.x = leading.x + trailing.x;          // Breite: Summe
				result.y = Math.max(leading.y, trailing.y); // Höhe: größtes Kind
			}
			default -> throw new RuntimeException("Unbekannte Split-Richtung");
		}

		return result;
	}
	//=========================================================================
	
	//=========================================================================
	@Override
	public void layout(Widget widget, Surface surface) {
		layoutNode(
			root,
			new Vector2f(0f,0f),
			widget.getExtent()
		);
	}
	//=========================================================================

	//=========================================================================
	private void layoutNode(SplitNode node, Vector2f origin, Vector2f extent) {

		if (node == null) return;
		if (node.widget == null) {

			var value = node.value;			

			var leadingExtent = new Vector2f(extent);
			var trailingExtent = new Vector2f(extent);
			var trailingOrigin = new Vector2f(origin);

			switch (node.direction) {
				case HORIZONTAL -> {
					switch (node.behaviour) {
						case PROPORTION    -> { value *= extent.y; }
						case FIX_LEADING   -> {}
						case FIX_TRAILING  -> { value = extent.y - value; }
						case AUTO_LEADING  -> { value = calcAutoValueY(node.leading); }	
						case AUTO_TRAILING -> { value = extent.y - calcAutoValueY(node.trailing); }	
					}
					leadingExtent.y = value;
					trailingExtent.y -= value;
					trailingOrigin.y += value;
				}
				case VERTICAL -> {
					switch (node.behaviour) {
						case PROPORTION    -> { value *= extent.x; }
						case FIX_LEADING   -> {}
						case FIX_TRAILING  -> { value = extent.x - value; }
						case AUTO_LEADING  -> { value = calcAutoValueX(node.leading); }	
						case AUTO_TRAILING -> { value = extent.x - calcAutoValueX(node.trailing); }	
					}
					leadingExtent.x = value;
					trailingExtent.x -= value;
					trailingOrigin.x += value;
				}
			}

			layoutNode(node.leading, origin, leadingExtent);
			layoutNode(node.trailing, trailingOrigin, trailingExtent);

		} else {

			node.widget.setLocation(origin);
			node.widget.setExtent(extent);

		}

	}
	//=========================================================================

	//=========================================================================
	private float calcAutoValueX(SplitNode node) {
		if (node == null) return 0f;
		if (node.widget != null) {
			return node.widget.getMinExtent().x; 
		} else if (node.direction == Direction.HORIZONTAL) {
			return Math.max(
				calcAutoValueX(node.leading),
				calcAutoValueX(node.trailing)
			);
		} else if (node.direction == Direction.VERTICAL) {
			return 
				calcAutoValueX(node.leading) +
				calcAutoValueX(node.trailing);
		} else {
			throw new RuntimeException("Unexpected Branch!");
		}
	}
	//=========================================================================

	//=========================================================================
	private float calcAutoValueY(SplitNode node) {
		if (node == null) return 0f;
		if (node.widget != null) {
			return node.widget.getMinExtent().y; 
		} else if (node.direction == Direction.VERTICAL) {
			return Math.max(
				calcAutoValueY(node.leading),
				calcAutoValueY(node.trailing)
			);
		} else if (node.direction == Direction.HORIZONTAL) {
			return 
				calcAutoValueY(node.leading) +
				calcAutoValueY(node.trailing);
		} else {
			throw new RuntimeException("Unexpected Branch!");
		}
	}
	//=========================================================================
	
}
//*****************************************************************************
