//**************************************************************************************************
package mct;
//**************************************************************************************************

import java.awt.geom.Point2D;

//**************************************************************************************************
public class LineIntersection {

    //==============================================================================================
	public static Point2D.Double calculateIntersection(
			Point2D.Double point1, double direction1Degrees,
			Point2D.Double point2, double direction2Degrees) {

		double direction1Radians = Math.toRadians(direction1Degrees-90);
		double direction2Radians = Math.toRadians(direction2Degrees-90);

		double slope1 = Math.tan(direction1Radians);
		double slope2 = Math.tan(direction2Radians);

		double yIntercept1 = point1.getY() - slope1 * point1.getX();
		double yIntercept2 = point2.getY() - slope2 * point2.getX();

		double xIntersection = (yIntercept2 - yIntercept1) / (slope1 - slope2);
		double yIntersection = slope1 * xIntersection + yIntercept1;

		return new Point2D.Double(xIntersection, yIntersection);
		
    }
    //==============================================================================================

    //==============================================================================================
    public static void main(String[] args) {

        Point2D.Double point1 = new Point2D.Double(-4183, 1882);
        double direction1Degrees = 76.1;
        
        Point2D.Double point2 = new Point2D.Double(-4465, 1473);
        double direction2Degrees = 49.7;

        Point2D.Double intersectionPoint = calculateIntersection(point1, direction1Degrees, point2, direction2Degrees);
        System.out.println("Intersection Point: (" + intersectionPoint.getX() + ", " + intersectionPoint.getY() + ")");

        System.out.println("Nether Point: " + (-5233/8) + " " + (2154/8));
        
    }
    //==============================================================================================

}
//**************************************************************************************************
