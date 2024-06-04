package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * The Intersectable interface represents an object that can be intersected by a ray.
 */
public interface Intersectable {

    /**
     * Finds the intersections of the given ray with the object.
     *
     * @param ray The ray to find intersections with.
     * @return A list of intersection points, or null if there are no intersections.
     */
    List<Point> findIntersections(Ray ray);
}
