package geometries;

import primitives.Point;

/**
 * Triangle class represents a triangle in 3D Cartesian coordinate
 * system
 * @see Polygon
 */
public class Triangle extends Polygon {
    /**
     * Triangle constructor based on three vertices. The vertices must be in the same plane
     * and the order of vertices is by edge path.
     * @param p1 first vertex
     * @param p2 second vertex
     * @param p3 third vertex
     * @throws IllegalArgumentException if the vertices are null or on the same line
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

}
