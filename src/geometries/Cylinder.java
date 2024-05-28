package geometries;

import primitives.*;
import static primitives.Util.isZero;

/**
 * Cylinder represents a cylinder in 3D space.
 */
public class Cylinder extends Tube{
    private final double height;

    /**
     * Constructs a new cylinder with the specified radius, axis, and height.
     *
     * @param radius the radius of the cylinder
     * @param axis the axis of the cylinder
     * @param height the height of the cylinder
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "radius=" + radius +
                ", axis=" + axis +
                ", height=" + height +
                '}';
    }

    @Override
    public Vector getNormal(Point p) {
        // Check that surface point is different from head of axisRay to avoid creating
        // a zero vector
        Vector dir = axis.getDirection();
        Point p0 = axis.getP0();
        if (p.equals(p0))
            return dir.scale(-1);
        // Finding the nearest point to the given point that is on the axis ray
        double t = dir.dotProduct(p.subtract(p0));
        // Finds out if surface point is on a base and returns a normal appropriately
        if (isZero(t))
            return dir.scale(-1);
        if (isZero(t - height))
            return dir;
        // If surface point is on the side of the cylinder, the superclass (Tube) is
        // used to find the normal
        return super.getNormal(p);
    }

}
