package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Geometry is an interface that represents a geometric object in 3D space.
 */
public interface Geometry {
    /**
     * Returns the normal vector of the geometry at the specified point.
     *
     * @param point the point on the geometry
     * @return the normal vector
     */
    Vector getNormal(Point point);

}
