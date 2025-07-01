//*****************************************************************************
package gameengine.widget.layout;
//*****************************************************************************

import gameengine.client.Surface;
import gameengine.widget.Widget;

import javax.vecmath.Vector2f;
import java.util.List;

//*****************************************************************************
public class ListLayout extends Layout {

    //=========================================================================
    public enum Direction { HORIZONTAL, VERTICAL }
    private final Direction direction;
    private final float spacing;
    //=========================================================================

    //=========================================================================
    public ListLayout(Direction direction) {
        this(direction, 0f);
    }
    //=========================================================================

    //=========================================================================
    public ListLayout(Direction direction, float spacing) {
        this.direction = direction;
        this.spacing = spacing;
    }
    //=========================================================================

    //=========================================================================
    public Direction getDirection() {
    	return direction;
    }
    //=========================================================================

    //=========================================================================
    public float getSpacing() {
    	return spacing;
    }
    //=========================================================================
    
    //=========================================================================
    @Override
    public void validate(Widget widget, Surface surface) {}
    //=========================================================================
    
	//=========================================================================
    @Override
    public void calcMinExtent(Widget widget, Surface surface) {
    
    	if (!isDirty()) return;

        float totalWidth = 0f;
        float totalHeight = 0f;
        int childCount = 0;

        for (Widget child : widget.getChildren()) {
        	child.getLayout().calcMinExtent(child, surface);
            Vector2f min = child.getMinExtent();
            if (direction == Direction.HORIZONTAL) {
                totalWidth += min.x;
                totalHeight = Math.max(totalHeight, min.y);
            } else {
                totalHeight += min.y;
                totalWidth = Math.max(totalWidth, min.x);
            }
            childCount++;
        }

        // Spacing zwischen den Kindern
        if (childCount > 1) {
            if (direction == Direction.HORIZONTAL) totalWidth += spacing * (childCount - 1);
            else totalHeight += spacing * (childCount - 1);
        }

        Vector2f currentMin = widget.getMinExtent();
        currentMin.x = Math.max(currentMin.x, totalWidth);
        currentMin.y = Math.max(currentMin.y, totalHeight);
        widget.setMinExtent(currentMin);
        
    }
    //=========================================================================
    
    //=========================================================================
    @Override
    public void layout(Widget widget, Surface surface) {
        Vector2f extent    = widget.getExtent();
        Vector2f minextent = widget.getMinExtent();
        extent.x = Math.max(extent.x, minextent.x);
        extent.y = Math.max(extent.y, minextent.y);
        List<Widget> children = widget.getChildren();
        Vector2f cursor = new Vector2f();
        if (direction.equals(Direction.VERTICAL)) {
        	cursor.y = extent.y;
        }
        for (Widget child : children) {
            Vector2f size = child.getMinExtent();
            switch (direction) {
                case HORIZONTAL -> {
                    child.setLocation(new Vector2f(cursor));
                    child.setExtent(new Vector2f(size));
                    cursor.x += size.x + spacing;
                }
                case VERTICAL -> {
                    cursor.y -= size.y; // zuerst runterziehen
                    child.setLocation(new Vector2f(cursor));
                    child.setExtent(new Vector2f(extent.x, size.y)); // volle Breite
                    cursor.y -= spacing; // dann spacing abziehen
                }
            }
        }
    }
    //=========================================================================

}
//*****************************************************************************
