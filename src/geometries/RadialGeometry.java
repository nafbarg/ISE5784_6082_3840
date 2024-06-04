package geometries;

import primitives.Ray;
import primitives.Vector;

/**
 * RadialGeometry is an abstract class that represents a radial geometry in 3D space.
 * A radial geometry is a geometry that is defined by its radius.
 */
abstract public class RadialGeometry implements Geometry{
    /**
     * The radius of the radial geometry.
     */
    protected final double radius;

    /**
     * Constructs a new radial geometry with the specified radius.
     *
     * @param radius the radius of the radial geometry
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "RadialGeometry{" +
                "radius=" + radius +
                '}';
    }


}
