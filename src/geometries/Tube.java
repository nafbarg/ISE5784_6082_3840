package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * Tube represents a tube in 3D space.
 */
public class Tube extends RadialGeometry {
    /**
     * The axis of the tube.
     */
    protected final Ray axis;

    /**
     * Constructs a new tube with the specified radius and axis.
     *
     * @param radius the radius of the tube
     * @param axis   the axis of the tube
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public String toString() {
        return "Tube{" + radius + ", " + axis + '}';
    }


    @Override
    public Vector getNormal(Point p) {
        // Finding the offset of the nearest point to the given point that is on the axis ray
        Point p0 = axis.getP0(); // Get the origin point of the axis ray
        Vector v = axis.getDirection(); // Get the direction vector of the axis ray
        // vector p0_p: The vector from the origin of the axis ray to the point p
        Vector p0_p = p.subtract(p0);
        // Calculate the projection of p0_p onto the direction vector v
        double t = alignZero(v.dotProduct(p0_p));
        // If the projection is zero, the point p is exactly on the axis line
        if (t == 0) {
            // In this case, the vector from p0 to p is already the normal, so normalize it and return
            return p0_p.normalize();
        }
        // Point o is the point on the v axis that is closest to the point p
        Point o = axis.getPoint(t);
        // The normal vector at point p is the vector from point o to point p, normalized
        return p.subtract(o).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDirection();
        Point p1 = axis.getP0();
        Vector v1 = axis.getDirection();

        Vector deltaP = p0.subtract(p1);

        double a = v.dotProduct(v) - Math.pow(v.dotProduct(v1), 2);
        double b = 2 * (v.dotProduct(deltaP) - v.dotProduct(v1) * deltaP.dotProduct(v1));
        double c = deltaP.dotProduct(deltaP) - Math.pow(deltaP.dotProduct(v1), 2) - radius * radius;

        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            return null; // No intersections
        }

        double sqrtDiscriminant = Math.sqrt(discriminant);
        double t1 = (-b + sqrtDiscriminant) / (2 * a);
        double t2 = (-b - sqrtDiscriminant) / (2 * a);

        List<GeoPoint> intersections = new ArrayList<>();

        if (t1 > 0) {
            Point intersection1 = ray.getPoint(t1);
            intersections.add(new GeoPoint(this, intersection1));
        }
        if (t2 > 0 && t1 != t2) {
            Point intersection2 = ray.getPoint(t2);
            intersections.add(new GeoPoint(this, intersection2));
        }

        return intersections.isEmpty() ? null : intersections;
    }
}
