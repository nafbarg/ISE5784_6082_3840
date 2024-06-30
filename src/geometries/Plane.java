package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Plane class represents a plane in 3D Cartesian coordinate system
 * by a point and a normal vector to the plane
 */
public class Plane extends Geometry {
    /**
     * The reference point of the plane.
     */
    private final Point q0;
    /**
     * The normal vector of the plane.
     */
    private final Vector normal;

    /**
     * Constructs a new plane with the specified point and normal vector.
     *
     * @param p      the point on the plane
     * @param normal the normal vector to the plane
     */
    public Plane(Point p, Vector normal) {
        this.q0 = p;
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
        q0 = p1;

        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        normal = v1.crossProduct(v2).normalize();
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
                "p=" + q0 +
                ", normal=" + normal +
                '}';
    }

    @Override

    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDirection();
        // If the ray starts on the plane, return null
        if (q0.equals(p0)) {
            return null;
        }

        double nv = normal.dotProduct(v);
        // If the ray is parallel to the plane, return null
        if (isZero(nv)) {
            return null;
        }

        double t = (q0.subtract(p0)).dotProduct(normal) / nv;
        // If the intersection point is behind the ray, return null
        if (alignZero(t) <= 0) {
            return null;
        }

        return List.of(new GeoPoint(this, p0.add(v.scale(t))));
    }
}