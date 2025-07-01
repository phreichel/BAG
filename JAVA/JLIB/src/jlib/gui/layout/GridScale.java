//*************************************************************************************************
package jlib.gui.layout;
//*************************************************************************************************

import jlib.gui.Element;

//*************************************************************************************************
public class GridScale implements Layout {

	//=============================================================================================
	private Orientation orientation = Orientation.HORIZONTAL;
	private Direction major = Direction.REGULAR;
	private Direction minor = Direction.REGULAR;
	private float horizontalGap = 0f;
	private float verticalGap = 0f;
	private int amount = 10;
	private float stretch = 50f;
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
	public int amount() {
		return amount;
	}
	//=============================================================================================

	//=============================================================================================
	public void amount(int amount) {
		this.amount = amount;
	}
	//=============================================================================================

	//=============================================================================================
	public float stretch() {
		return stretch;
	}
	//=============================================================================================

	//=============================================================================================
	public void stretch(float stretch) {
		this.stretch = stretch;
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
		
		int n = element.elements();
		if (n == 0) return;
		
		float span = (element.width() - horizontalGap * (amount-1)) / amount;
		
		float x = 0;
		float y = 0;
		
		for (int i=0; i<n; i++) {

			if ((i > 0) && (i % amount) == 0) {
				x = 0;
				y += stretch + verticalGap;
			}
			
			int idx = i;
			var child = element.element(idx);
			
			child.position(x, y);
			child.size(span, stretch);
			
			x += span + horizontalGap;

		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyHorizontalRegularReverse(Element element) {

		int n = element.elements();
		if (n == 0) return;
		
		float span = (element.width() - horizontalGap * (amount-1)) / amount;
		
		float x = 0;
		float y = element.height() - stretch;
		
		for (int i=0; i<n; i++) {

			if ((i > 0) && (i % amount) == 0) {
				x = 0;
				y -= stretch + verticalGap;
			}
			
			int idx = n - i - 1;
			var child = element.element(idx);
			
			child.position(x, y);
			child.size(span, stretch);
			
			x += span + horizontalGap;

		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyHorizontalReverseRegular(Element element) {

		int n = element.elements();
		if (n == 0) return;
		
		float span = (element.width() - horizontalGap * (amount-1)) / amount;
		
		int remain = n;
		float x = remain >= amount ? 0 : (span + horizontalGap) * (amount - remain);
		float y = 0;
		
		for (int i=0; i<n; i++) {

			if ((i > 0) && (i % amount) == 0) {
				remain = n - i;
				x = remain >= amount ? 0 : (span + horizontalGap) * (amount - remain);
				y += stretch + verticalGap;
			}
			
			int idx = i;
			var child = element.element(idx);
			
			child.position(x, y);
			child.size(span, stretch);
			
			x += span + horizontalGap;

		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyHorizontalReverseReverse(Element element) {

		int n = element.elements();
		if (n == 0) return;
		
		float span = (element.width() - horizontalGap * (amount-1)) / amount;
		
		int remain = n;
		float x = remain >= amount ? 0 : (span + horizontalGap) * (amount - remain);
		float y = element.height() - stretch;
		
		for (int i=0; i<n; i++) {

			if ((i > 0) && (i % amount) == 0) {
				remain = n - i;
				x = remain >= amount ? 0 : (span + horizontalGap) * (amount - remain);
				y -= stretch + verticalGap;
			}
			
			int idx = n - i - 1;
			var child = element.element(idx);
			
			child.position(x, y);
			child.size(span, stretch);
			
			x += span + horizontalGap;

		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyVerticalRegularRegular(Element element) {
		
		int n = element.elements();
		if (n == 0) return;
		
		float span = (element.height() - verticalGap * (amount-1)) / amount;
		
		float x = 0;
		float y = 0;
		
		for (int i=0; i<n; i++) {

			if ((i > 0) && (i % amount) == 0) {
				x += stretch + horizontalGap;
				y = 0;
			}
			
			int idx = i;
			var child = element.element(idx);
			
			child.position(x, y);
			child.size(stretch, span);
			
			y += span + verticalGap;

		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyVerticalRegularReverse(Element element) {
		
		int n = element.elements();
		if (n == 0) return;
		
		float span = (element.height() - verticalGap * (amount-1)) / amount;
		
		float x = element.width() - stretch;
		float y = 0;
		
		for (int i=0; i<n; i++) {

			if ((i > 0) && (i % amount) == 0) {
				x -= stretch + horizontalGap;
				y = 0;
			}
			
			int idx = n - i - 1;
			var child = element.element(idx);
			
			child.position(x, y);
			child.size(stretch, span);
			
			y += span + verticalGap;

		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyVerticalReverseRegular(Element element) {
	
		int n = element.elements();
		if (n == 0) return;
		
		float span = (element.height() - verticalGap * (amount-1)) / amount;
		
		int remain = n;
		float x = 0;
		float y = remain >= amount ? 0 : (span + verticalGap) * (amount - remain);
		
		for (int i=0; i<n; i++) {

			if ((i > 0) && (i % amount) == 0) {
				remain = n - i;
				x += stretch + horizontalGap;
				y = remain >= amount ? 0 : (span + verticalGap) * (amount - remain);
			}
			
			int idx = i;
			var child = element.element(idx);
			
			child.position(x, y);
			child.size(stretch, span);
			
			y += span + verticalGap;

		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyVerticalReverseReverse(Element element) {
		
		int n = element.elements();
		if (n == 0) return;
		
		float span = (element.height() - verticalGap * (amount-1)) / amount;
		
		int remain = n;
		float x = element.width() - stretch;
		float y = remain >= amount ? 0 : (span + verticalGap) * (amount - remain);
		
		for (int i=0; i<n; i++) {

			if ((i > 0) && (i % amount) == 0) {
				remain = n - i;
				x -= stretch + horizontalGap;
				y = remain >= amount ? 0 : (span + verticalGap) * (amount - remain);
			}
			
			int idx = n - i - 1;
			var child = element.element(idx);
			
			child.position(x, y);
			child.size(stretch, span);
			
			y += span + verticalGap;

		}

	}
	//=============================================================================================
	
}
//*************************************************************************************************
