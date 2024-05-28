package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
        Point o = p0.add(v.scale(t));
        // The normal vector at point p is the vector from point o to point p, normalized
        return p.subtract(o).normalize();
    }
}
