package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Plane class represents a plane in 3D Cartesian coordinate system
 * by a point and a normal vector to the plane
 */
public class Plane implements Geometry {
    private final Point p;
    private final Vector normal;

    /**
     * Constructs a new plane with the specified point and normal vector.
     *
     * @param p the point on the plane
     * @param normal the normal vector to the plane
     */
    public Plane(Point p, Vector normal) {
        this.p = p;
        this.normal = normal.normalize();
    }

    /**
     * Constructs a new plane with the specified three points.
     *
     * @param p1 the first point on the plane
     * @param p2 the second point on the plane
     * @param p3 the third point on the plane
     */
    public Plane(Point p1, Point p2, Point p3) {
        p = p1;
        normal = null;
    }

    /**
     * Returns the normal vector of the plane.
     *
     * @return the normal vector
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }


    @Override
    public String toString() {
        return "Plane{" +
                "p=" + p +
                ", normal=" + normal +
                '}';
    }
}
