package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Tube represents a tube in 3D space.
 */
public class Tube extends RadialGeometry{
    /**
     * The axis of the tube.
     */
    protected final Ray axis;

    /**
     * Constructs a new tube with the specified radius and axis.
     *
     * @param radius the radius of the tube
     * @param axis the axis of the tube
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "radius=" + radius +
                ", axis=" + axis +
                '}';
    }

    @Override
    public Vector getNormal(Point p) {
        // Finding the offset of the nearest point to the given point that is on the
        // axis ray
        double t = axis.getDir().dotProduct(p.subtract(axis.getP0()));
        // Returning the subtraction of one from the other
        return p.subtract(axis.getPoint(t)).normalize();
    }
}
