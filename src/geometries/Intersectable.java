package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;

import java.util.List;

public interface Intersectable {
    public default List<Point> findIntersections(Ray ray) {
        return null;
    }
}
