package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import java.util.List;


/**
 * Geometry is an interface that represents a geometric object in 3D space.
 */
public interface  Geometry extends Intersectable {
    /**
     * Returns the normal vector of the geometry at the specified point.
     *
     * @param point the point on the geometry
     * @return the normal vector
     */
    Vector getNormal(Point point);

}
