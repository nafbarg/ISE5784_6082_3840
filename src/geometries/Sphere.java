package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Sphere represents a sphere in 3D space.
 */
public class Sphere extends RadialGeometry{
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
    public Vector getNormal(Point point) {
        return null;
    }


}
