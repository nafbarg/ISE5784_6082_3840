package test;

import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for working with triangles.
 */

public class TriangleUtils {

    /**
     * Returns a scaled down version of the given triangle by a given scale factor.
     *
     * @param triangle the original triangle
     * @param scale    the scaling factor (0 < scale < 1 for shrinking)
     * @return a new Triangle object that is scaled down
     */
    public static Triangle shrinkTriangle(Triangle triangle, double scale) {
        // Ensure scale is between 0 and 1
        if (scale <= 0 || scale >= 1) {
            throw new IllegalArgumentException("Scale must be between 0 and 1");
        }

        // Get the vertices of the triangle
        Point p1 = triangle.getP1();
        Point p2 = triangle.getP2();
        Point p3 = triangle.getP3();

        // Find the centroid (average of the points)
        Point centroid = new Point(
                (p1.getX() + p2.getX() + p3.getX()) / 3,
                (p1.getY() + p2.getY() + p3.getY()) / 3,
                (p1.getZ() + p2.getZ() + p3.getZ()) / 3
        );

        // Scale each point towards the centroid
        Point newP1 = scalePointTowardsCentroid(p1, centroid, scale);
        Point newP2 = scalePointTowardsCentroid(p2, centroid, scale);
        Point newP3 = scalePointTowardsCentroid(p3, centroid, scale);

        // Return the new scaled triangle
        return (Triangle) new Triangle(newP1, newP2, newP3).setMaterial(triangle.getMaterial());
    }

    /**
     * Scales the point towards the centroid by the given scale factor.
     *
     * @param point    the original point
     * @param centroid the centroid of the triangle
     * @param scale    the scaling factor
     * @return a new scaled point
     */
    private static Point scalePointTowardsCentroid(Point point, Point centroid, double scale) {
        // Compute the new coordinates
        double newX = centroid.getX() + (point.getX() - centroid.getX()) * scale;
        double newY = centroid.getY() + (point.getY() - centroid.getY()) * scale;
        double newZ = centroid.getZ() + (point.getZ() - centroid.getZ()) * scale;

        // Return the new scaled point
        return new Point(newX, newY, newZ);
    }

    // Rotating a point around an arbitrary axis (between Y and Z)
    public static Point rotatePointAroundDiagonal(Point p, double angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);
        double cosTheta = Math.cos(angleRadians);
        double sinTheta = Math.sin(angleRadians);
        double oneMinusCosTheta = 1 - cosTheta;

        // Unit vector for the diagonal axis (between Y and Z)
        double vX = 0;
        double vY = 1 / Math.sqrt(2); // 1/sqrt(2) for diagonal between Y and Z
        double vZ = 1 / Math.sqrt(2); // 1/sqrt(2) for diagonal between Y and Z

        double x = p.getX();
        double y = p.getY();
        double z = p.getZ();

        double newX = x * (cosTheta + oneMinusCosTheta * vX * vX) +
                y * (oneMinusCosTheta * vX * vY - sinTheta * vZ) +
                z * (oneMinusCosTheta * vX * vZ + sinTheta * vY);

        double newY = x * (oneMinusCosTheta * vY * vX + sinTheta * vZ) +
                y * (cosTheta + oneMinusCosTheta * vY * vY) +
                z * (oneMinusCosTheta * vY * vZ - sinTheta * vX);

        double newZ = x * (oneMinusCosTheta * vZ * vX - sinTheta * vY) +
                y * (oneMinusCosTheta * vZ * vY + sinTheta * vX) +
                z * (cosTheta + oneMinusCosTheta * vZ * vZ);

        return new Point(newX, newY, newZ);
    }


    public static List<Triangle> shrinkPyramid(List<Triangle> triangles, Point apex, double scale) {
        if (scale <= 0 || scale >= 1) {
            throw new IllegalArgumentException("Scale must be between 0 and 1");
        }

        // Calculate the centroid of the base by averaging the vertices of the base
        Point baseCentroid = calculateBaseCentroid(triangles);

        // Scale the apex towards the base centroid
        Point newApex = scalePointTowardsCentroid(apex, baseCentroid, scale);

        // Create a new list to store the shrunken triangles
        List<Triangle> shrunkenTriangles = new ArrayList<>();

        // Scale each triangle in the base and the sides
        for (Triangle triangle : triangles) {
            // Scale each point of the triangle towards the base centroid
            Point p1 = triangle.getP1();
            Point p2 = triangle.getP2();
            Point p3 = triangle.getP3();

            Point newP1 = scalePointTowardsCentroid(p1, baseCentroid, scale);
            Point newP2 = scalePointTowardsCentroid(p2, baseCentroid, scale);
            Point newP3 = scalePointTowardsCentroid(p3, baseCentroid, scale);

            Triangle newTriangle = (Triangle) new Triangle(newP1, newP2, newP3).setMaterial(triangle.getMaterial());
            //print the new triangle
            System.out.println(newTriangle);

            // Create a new triangle with the scaled points
            shrunkenTriangles.add(newTriangle);
        }

        // Return the list of shrunken triangles representing the pyramid
        return shrunkenTriangles;
    }

    public static void main(String[] args) {
        // הגדרת הנקודות
        Point apex = new Point(35.0,-51.6,-11.1);
        Point p1 = new Point(-60, -106.6, -93.6);
        Point p2 = new Point(90.0,-106.7,-103.6);
        Point p3 = new Point(52.5,5.8,-111.1);

        // הגדרת המשולשים (צדדים של הפירמידה)
        Triangle pyramidSide1 = (Triangle) new Triangle(p1, p2, apex)
                .setMaterial(new Material().setKd(0.1).setKs(0.4).setShininess(30).setKr(0.15));
        Triangle pyramidSide2 = (Triangle) new Triangle(p2, p3 , apex)
                .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30).setKr(0.7));
        Triangle pyramidSide3 = (Triangle) new Triangle(p1, p3, apex)
                .setMaterial(new Material().setKd(0.0).setKs(0.0).setShininess(30).setKr(1)).setEmission(new Color(20, 30, 40));

        // יצירת רשימה והוספת המשולשים
        List<Triangle> shrunkenTriangles = new ArrayList<>();
        shrunkenTriangles.add(pyramidSide1);
        shrunkenTriangles.add(pyramidSide2);
        shrunkenTriangles.add(pyramidSide3);

        // קריאה לפונקציה shrinkPyramid
        List<Triangle> newShrunkenTriangles = shrinkPyramid(shrunkenTriangles, apex, 0.5);

        // הדפסת המשולשים המוקטנים
        for (Triangle triangle : newShrunkenTriangles) {
            System.out.println(triangle.getP1() + " " + triangle.getP2() + " " + triangle.getP3());
        }
    }


    /**
     * Calculate the centroid of the base of the pyramid (assuming the base is defined by a set of triangles).
     * This assumes that all base triangles share the same base points.
     */
    private static Point calculateBaseCentroid(List<Triangle> triangles) {
        double sumX = 0, sumY = 0, sumZ = 0;
        int numBasePoints = 0;

        for (Triangle triangle : triangles) {
            // Sum all the points of the base
            Point p1 = triangle.getP1();
            Point p2 = triangle.getP2();
            Point p3 = triangle.getP3();

            sumX += p1.getX() + p2.getX() + p3.getX();
            sumY += p1.getY() + p2.getY() + p3.getY();
            sumZ += p1.getZ() + p2.getZ() + p3.getZ();
            numBasePoints += 3;
        }

        // Calculate the average (centroid)
        return new Point(sumX / numBasePoints, sumY / numBasePoints, sumZ / numBasePoints);
    }
}

//    ---use shrinkTriangle function in the test Exercise7.java---

//        //shrinkPyramid
//        //
//        List<Triangle> shrunkenTriangles = new ArrayList<>();
//        shrunkenTriangles.add(pyramidSide1);
//        shrunkenTriangles.add(pyramidSide2);
//        shrunkenTriangles.add(pyramidSide3);
//        shrunkenTriangles = shrinkPyramid(shrunkenTriangles, apex, 0.5);
//
//        for (int i = 0; i < 3; i++) {
//            System.out.println(shrunkenTriangles.get(i));
//            System.out.println(shrunkenTriangles.get(i).getP1());
//            System.out.println(shrunkenTriangles.get(i).getP2());
//            System.out.println(shrunkenTriangles.get(i).getP3());
//
//        }
//
//
//        scene.geometries.add(shrunkenTriangles.get(0), shrunkenTriangles.get(1), shrunkenTriangles.get(2));

//    ---use rotatePointAroundDiagonal function in the test Exercise7.java---

//// Step 1: Find the center of the cube (average of all points)
//private Point getCenter(Point... points) {
//    double sumX = 0, sumY = 0, sumZ = 0;
//    for (Point p : points) {
//        sumX += p.getX();
//        sumY += p.getY();
//        sumZ += p.getZ();
//    }
//    int numPoints = points.length;
//    return new Point(sumX / numPoints, sumY / numPoints, sumZ / numPoints);
//}
//
//// Step 2: Translate points so the center is at the origin
//private Point translatePoint(Point p, Vector translation) {
//    return p.add(translation);
//}
//
//// Step 4: Rotate points around the diagonal axis
//// Using the rotatePointAroundDiagonal function from earlier
//
//// Example usage:
//
//Point p1 = new Point(-100, 0, -50);
//Point p2 = new Point(-50, 0, -50);
//Point p3 = new Point(-50, 50, -50);
//Point p4 = new Point(-100, 50, -50);
//Point p5 = new Point(-100, 0, -100);
//Point p6 = new Point(-50, 0, -100);
//Point p7 = new Point(-50, 50, -100);
//Point p8 = new Point(-100, 50, -100);
//
//Point center = getCenter(p1, p2, p3, p4, p5, p6, p7, p8); // Find the center
//
//// Translate points to move the center to the origin
//Vector toOrigin = new Vector(-center.getX(), -center.getY(), -center.getZ());
//p1 = translatePoint(p1, toOrigin);
//p2 = translatePoint(p2, toOrigin);
//p3 = translatePoint(p3, toOrigin);
//p4 = translatePoint(p4, toOrigin);
//p5 = translatePoint(p5, toOrigin);
//p6 = translatePoint(p6, toOrigin);
//p7 = translatePoint(p7, toOrigin);
//p8 = translatePoint(p8, toOrigin);
//
//// Rotate the points around the diagonal axis
//double angle = 45; // Angle for rotation
//p1 = rotatePointAroundDiagonal(p1, angle);
//p2 = rotatePointAroundDiagonal(p2, angle);
//p3 = rotatePointAroundDiagonal(p3, angle);
//p4 = rotatePointAroundDiagonal(p4, angle);
//p5 = rotatePointAroundDiagonal(p5, angle);
//p6 = rotatePointAroundDiagonal(p6, angle);
//p7 = rotatePointAroundDiagonal(p7, angle);
//p8 = rotatePointAroundDiagonal(p8, angle);
//
//// Translate points back to their original position
//Vector backToOriginal = toOrigin.scale(-1); // Reverse the translation
//p1 = translatePoint(p1, backToOriginal);
//p2 = translatePoint(p2, backToOriginal);
//p3 = translatePoint(p3, backToOriginal);
//p4 = translatePoint(p4, backToOriginal);
//p5 = translatePoint(p5, backToOriginal);
//p6 = translatePoint(p6, backToOriginal);
//p7 = translatePoint(p7, backToOriginal);
//p8 = translatePoint(p8, backToOriginal);
//
//// Now you can use the rotated points to define your polygons again
//Polygon front = new Polygon(p1, p2, p3, p4);
//Polygon back = new Polygon(p5, p6, p7, p8);
//Polygon top = new Polygon(p4, p3, p7, p8);
//Polygon bottom = new Polygon(p1, p2, p6, p5);
//Polygon left = new Polygon(p1, p4, p8, p5);
//Polygon right = new Polygon(p2, p3, p7, p6);
