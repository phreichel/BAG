//*************************************************************************************************
package jlib.gui.layout;
//*************************************************************************************************

import jlib.gui.Element;

//*************************************************************************************************
public class GridFix implements Layout {

	//=============================================================================================
	private Orientation orientation = Orientation.HORIZONTAL;
	private Direction major = Direction.REGULAR;
	private Direction minor = Direction.REGULAR;
	private float horizontalGap = 0f;
	private float verticalGap = 0f;
	private float horizontalSpan = 0f;
	private float verticalSpan = 0f;
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
	public float horizontalSpan() {
		return horizontalSpan;
	}
	//=============================================================================================

	//=============================================================================================
	public float verticalSpan() {
		return verticalSpan;
	}
	//=============================================================================================

	//=============================================================================================
	public void span(float horizontal, float vertical) {
		this.horizontalSpan = horizontal;
		this.verticalSpan = vertical;
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

		float x = 0;
		float y = 0;
		
		int n = 1;
		while ((horizontalSpan * n + horizontalGap * (n-1)) < element.width()) n++;
		n = Math.max(1, n-1);
		
		int c = 0;
		for (int i=0; c < element.elements(); i++) {
			for (int j=0; j<n && c<element.elements(); j++) {
				
				int idx = (i*n) + j;
				var child = element.element(idx);
				
				if ((x + horizontalSpan) > element.width()) {
					x = 0;
					y += verticalSpan + verticalGap;
				}
				
				child.position(x, y);
				child.size(horizontalSpan, verticalSpan);
				
				x += horizontalSpan + horizontalGap;
				
				c++;
				
			}
		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyHorizontalRegularReverse(Element element) {

		float x = 0;
		float y = element.height() - verticalSpan;

		int n = 1;
		while ((horizontalSpan * n + horizontalGap * (n-1)) < element.width()) n++;
		n = Math.max(1, n-1);
		
		int c = 0;
		for (int i=0; c < element.elements(); i++) {
			for (int j=0; j<n && c<element.elements(); j++) {
				int idx = element.elements() - (i*n) - 1 - (n-j-1);
				if (idx >= 0) {
					var child = element.element(idx);
					
					if ((x + horizontalSpan) > element.width()) {
						x = 0;
						y -= verticalSpan + verticalGap;
					}
					
					child.position(x, y);
					child.size(horizontalSpan, verticalSpan);
					
					x += horizontalSpan + horizontalGap;
					
					c++;
				}
			}
		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyHorizontalReverseRegular(Element element) {
		
		float x = element.width() - horizontalSpan;
		float y = 0;
		
		int n = 1;
		while ((horizontalSpan * n + horizontalGap * (n-1)) < element.width()) n++;
		n = Math.max(1, n-1);
		
		int c = 0;
		for (int i=0; c < element.elements(); i++) {
			for (int j=0; j<n && c<element.elements(); j++) {
				
				int idx = (i*n) + (n-j-1);
				if (idx < element.elements()) {
					var child = element.element(idx);
					
					if (x < 0) {
						x = element.width() - horizontalSpan;
						y += verticalSpan + verticalGap;
					}
					
					child.position(x, y);
					child.size(horizontalSpan, verticalSpan);
					
					x -= horizontalSpan + horizontalGap;
					
					c++;
				}
			}
		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyHorizontalReverseReverse(Element element) {
		
		float x = element.width() - horizontalSpan;
		float y = element.height() - verticalSpan;
		
		int n = 1;
		while ((horizontalSpan * n + horizontalGap * (n-1)) < element.width()) n++;
		n = Math.max(1, n-1);
		
		int c = 0;
		for (int i=0; c < element.elements(); i++) {
			for (int j=0; j<n && c<element.elements(); j++) {
				
				int idx = element.elements() - 1 - j - i*n;
				var child = element.element(idx);
				
				if (x < 0) {
					x = element.width() - horizontalSpan;
					y -= verticalSpan + verticalGap;
				}
				
				child.position(x, y);
				child.size(horizontalSpan, verticalSpan);
				
				x -= horizontalSpan + horizontalGap;
				
				c++;
			}
		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyVerticalRegularRegular(Element element) {
		
		float x = 0;
		float y = 0;
		
		int n = 1;
		while ((verticalSpan * n + verticalGap * (n-1)) < element.height()) n++;
		n = Math.max(1, n-1);
		
		int c = 0;
		for (int i=0; c < element.elements(); i++) {
			for (int j=0; j<n && c<element.elements(); j++) {
				
				int idx = (i*n) + j;
				var child = element.element(idx);
				
				if ((y + verticalSpan) > element.height()) {
					x += horizontalSpan + horizontalGap;
					y = 0;
				}
				
				child.position(x, y);
				child.size(horizontalSpan, verticalSpan);
				
				y += verticalSpan + verticalGap;
				
				c++;
				
			}
		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyVerticalRegularReverse(Element element) {
		
		float x = element.width() - horizontalSpan;
		float y = 0;

		int n = 1;
		while ((verticalSpan * n + verticalGap * (n-1)) < element.height()) n++;
		n = Math.max(1, n-1);
		
		int c = 0;
		for (int i=0; c < element.elements(); i++) {
			for (int j=0; j<n && c<element.elements(); j++) {
				
				int idx = element.elements() - (i*n) - 1 - (n-j-1);
				if (idx >= 0) {
					
					var child = element.element(idx);
					
					if ((y + verticalSpan) > element.height()) {
						x -= horizontalSpan + horizontalGap;
						y = 0;
					}
					
					child.position(x, y);
					child.size(horizontalSpan, verticalSpan);
					
					y += verticalSpan + verticalGap;
					
					c++;
					
				}
			}
		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyVerticalReverseRegular(Element element) {
		
		float x = 0;
		float y = element.height() - verticalSpan;
		
		int n = 1;
		while ((verticalSpan * n + verticalGap * (n-1)) < element.height()) n++;
		n = Math.max(1, n-1);
		
		int c = 0;
		for (int i=0; c < element.elements(); i++) {
			for (int j=0; j<n && c<element.elements(); j++) {
				
				int idx = (i*n) + (n-j-1);
				if (idx < element.elements()) {
					var child = element.element(idx);
					
					if (y < 0) {
						x += horizontalSpan + horizontalGap;
						y = element.height() - verticalSpan;
					}
					
					child.position(x, y);
					child.size(horizontalSpan, verticalSpan);
					
					y -= verticalSpan + verticalGap;
					
					c++;
				}
			}
		}
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyVerticalReverseReverse(Element element) {
		
		float x = element.width() - horizontalSpan;
		float y = element.height() - verticalSpan;
		
		int n = 1;
		while ((verticalSpan * n + verticalGap * (n-1)) < element.height()) n++;
		n = Math.max(1, n-1);
		
		int c = 0;
		for (int i=0; c < element.elements(); i++) {
			for (int j=0; j<n && c<element.elements(); j++) {
				
				int idx = element.elements() - 1 - j - i*n;
				var child = element.element(idx);
				
				if (y < 0) {
					x -= horizontalSpan + horizontalGap;
					y = element.height() - verticalSpan;
				}
				
				child.position(x, y);
				child.size(horizontalSpan, verticalSpan);
				
				y -= verticalSpan + verticalGap;
				
				c++;
			}
		}
		
	}
	//=============================================================================================
	
}
//*************************************************************************************************
