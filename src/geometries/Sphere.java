package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Sphere represents a sphere in 3D space.
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * Constructs a new sphere with the specified radius and center.
     *
     * @param radius the radius of the sphere
     * @param center the center of the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point p0) {
        return p0.subtract(center).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector u = center.subtract(ray.getP0());
        double tm = alignZero(ray.getDir().dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));

        // If the ray starts outside the sphere and there is no intersection
        if (d >= radius) {
            return null;
        }

        double th = Math.sqrt(radius * radius - d * d);
        double t1 = tm - th;
        double t2 = tm + th;

        if (t1 > 0 && t2 > 0) {
            return List.of(ray.getPoint(t1), ray.getPoint(t2));
        }

        if (t1 > 0) {
            return List.of(ray.getPoint(t1));
        }

        if (t2 > 0) {
            return List.of(ray.getPoint(t2));
        }
        return null;

    }

}
