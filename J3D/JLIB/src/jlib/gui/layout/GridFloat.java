//*************************************************************************************************
package jlib.gui.layout;
//*************************************************************************************************

import jlib.gui.Element;

//*************************************************************************************************
public class GridFloat implements Layout {

	//=============================================================================================
	private Orientation orientation = Orientation.HORIZONTAL;
	private Direction major = Direction.REGULAR;
	private Direction minor = Direction.REGULAR;
	private float horizontalGap = 0f;
	private float verticalGap = 0f;
	private float span = 30f;
	//=============================================================================================

	//=============================================================================================
	public Orientation orientation() {
		return orientation;
	}
	//=============================================================================================

	//=============================================================================================
	public Direction major() {
		return major;
	}
	//=============================================================================================

	//=============================================================================================
	public Direction minor() {
		return minor;
	}
	//=============================================================================================

	//=============================================================================================
	public void align(Orientation orientation, Direction major, Direction minor) {
		this.orientation = orientation;
		this.major = major;
		this.minor = minor;
	}
	//=============================================================================================

	//=============================================================================================
	public float horizontalGap() {
		return horizontalGap;
	}
	//=============================================================================================

	//=============================================================================================
	public float verticalGap() {
		return verticalGap;
	}
	//=============================================================================================

	//=============================================================================================
	public void gap(float horizontal, float vertical) {
		this.horizontalGap = horizontal;
		this.verticalGap = vertical;
	}
	//=============================================================================================

	//=============================================================================================
	public float span() {
		return span;
	}
	//=============================================================================================

	//=============================================================================================
	public void span(float span) {
		this.span = span;
	}
	//=============================================================================================
	
	//=============================================================================================
	public void apply(Element element) {
		switch (orientation) {
			case VERTICAL -> {
				switch (major) {
					case REVERSE -> {
						switch (minor) {
							case REVERSE -> {
								applyVerticalReverseReverse(element);
							}
							default -> {
								applyVerticalReverseRegular(element);
							}
						}
					}
					default -> {
						switch (minor) {
							case REVERSE -> {
								applyVerticalRegularReverse(element);
							}
							default -> {
								applyVerticalRegularRegular(element);
							}
						}
					}
				}
			}
			default -> {
				switch (major) {
					case REVERSE -> {
						switch (minor) {
							case REVERSE -> {
								applyHorizontalReverseReverse(element);
							}
							default -> {
								applyHorizontalReverseRegular(element);
							}
						}
					}
					default -> {
						switch (minor) {
							case REVERSE -> {
								applyHorizontalRegularReverse(element);
							}
							default -> {
								applyHorizontalRegularRegular(element);
							}
						}
					}
				}
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void applyHorizontalRegularRegular(Element element) {

		float x = 0f;
		float y = 0f;
		
		for (int i=0; i<element.elements(); i++) {
			
			int idx = i;
			var child = element.element(idx);

			float w = child.width();
			float h = span;
			
			if ((x + w) > element.width()) {
				x = 0f;
				y += span + verticalGap;
			}
			
			child.position(x, y);
			child.size(w, h);
			
			x += w + horizontalGap;
			
		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyHorizontalRegularReverse(Element element) {

		float x = 0f;
		float y = 0f;
		float offset = element.height();
		
		for (int i=0; i<element.elements(); i++) {
			
			int idx = i;
			var child = element.element(idx);

			float w = child.width();
			float h = span;
			
			if ((x + w) > element.width()) {
				x = 0f;
				y += span + verticalGap;
				offset = element.height() - (y + span);
			}
			
			child.position(x, y);
			child.size(w, h);
			
			x += w + horizontalGap;
			
		}

		for (int i=0; i<element.elements(); i++) {
			int idx = i;
			var child = element.element(idx);
			float oldx = child.x();
			float oldy = child.y();
			child.position(oldx, oldy + offset);
		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyHorizontalReverseRegular(Element element) {
		
		float x = 0f;
		float y = 0f;
		
		int start = 0;
		for (int i=0; i<element.elements(); i++) {
			
			int idx = i;
			var child = element.element(idx);

			float w = child.width();
			float h = span;
			
			if ((x + w) > element.width()) {
				var prev = element.element(i-1);
				float offset = element.width() - (prev.x() + prev.width());
				for (int j=start; j<i; j++) {
					var oldchild = element.element(j);
					float oldx = oldchild.x();
					float oldy = oldchild.y();
					oldchild.position(oldx + offset, oldy);
				}
				
				x = 0f;
				y += span + verticalGap;
			
				start = i;
				
			}
			
			child.position(x, y);
			child.size(w, h);
			
			x += w + horizontalGap;
			
		}
		
		if (start < element.elements()-1) {
			var prev = element.element(element.elements()-1);
			float offset = element.width() - (prev.x() + prev.width());
			for (int j=start; j<element.elements(); j++) {
				var oldchild = element.element(j);
				float oldx = oldchild.x();
				float oldy = oldchild.y();
				oldchild.position(oldx + offset, oldy);
			}
			
			x = 0f;
			y += span + verticalGap;
		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyHorizontalReverseReverse(Element element) {
	
		float x = 0f;
		float y = 0f;
		float offset = element.height();
		
		int start = 0;
		for (int i=0; i<element.elements(); i++) {
			
			int idx = i;
			var child = element.element(idx);

			float w = child.width();
			float h = span;
			
			if ((x + w) > element.width()) {
				var prev = element.element(i-1);
				float offset2 = element.width() - (prev.x() + prev.width());
				for (int j=start; j<i; j++) {
					var oldchild = element.element(j);
					float oldx = oldchild.x();
					float oldy = oldchild.y();
					oldchild.position(oldx + offset2, oldy);
				}
				
				x = 0f;
				y += span + verticalGap;
			
				start = i;
				offset = element.height() - (y + span);

			}
			
			child.position(x, y);
			child.size(w, h);
			
			x += w + horizontalGap;
			
		}
		
		if (start < element.elements()-1) {
			var prev = element.element(element.elements()-1);
			float offset2 = element.width() - (prev.x() + prev.width());
			for (int j=start; j<element.elements(); j++) {
				var oldchild = element.element(j);
				float oldx = oldchild.x();
				float oldy = oldchild.y();
				oldchild.position(oldx + offset2, oldy);
			}
			
			x = 0f;
			y += span + verticalGap;
		}

		for (int i=0; i<element.elements(); i++) {
			int idx = i;
			var child = element.element(idx);
			float oldx = child.x();
			float oldy = child.y();
			child.position(oldx, oldy + offset);
		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyVerticalRegularRegular(Element element) {
		
	
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyVerticalRegularReverse(Element element) {
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyVerticalReverseRegular(Element element) {
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyVerticalReverseReverse(Element element) {
		
	}
	//=============================================================================================
	
}
//*************************************************************************************************
