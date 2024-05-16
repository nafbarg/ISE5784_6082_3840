package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
    public Vector getNormal(Point point) {
        return null;
    }

}
