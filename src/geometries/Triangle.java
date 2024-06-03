package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Triangle class represents a triangle in 3D Cartesian coordinate
 * system
 * @see Polygon
 */
public class Triangle extends Polygon {
    /**
     * Triangle constructor based on three vertices. The vertices must be in the same plane
     * and the order of vertices is by edge path.
     * @param p1 first vertex
     * @param p2 second vertex
     * @param p3 third vertex
     * @throws IllegalArgumentException if the vertices are null or on the same line
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        if (plane.findIntersections(ray) == null)
            return null;
        Point q = plane.findIntersections(ray).get(0);
        // Then, use barycentric coordinates technique to check if the intersection
        // point is inside the triangle
        // calculate the area of the respective triangle for each of the edges
        Point a = vertices.get(0);
        Point b = vertices.get(1);
        Point c = vertices.get(2);

        Vector normal = plane.getNormal();

        try {
            if (isZero(a.subtract(b).crossProduct(q.subtract(b)).dotProduct(normal))
                    || isZero(c.subtract(b).crossProduct(q.subtract(b)).dotProduct(normal))
                    || isZero(a.subtract(c).crossProduct(q.subtract(c)).dotProduct(normal)))
                return null;
        } catch (IllegalArgumentException e) {
            return null;
        }

        double area = a.subtract(b).crossProduct(a.subtract(c)).dotProduct(normal);

        double aArea = c.subtract(b).crossProduct(q.subtract(b)).dotProduct(normal);
        double bArea = a.subtract(c).crossProduct(q.subtract(c)).dotProduct(normal);
        double cArea = b.subtract(a).crossProduct(q.subtract(a)).dotProduct(normal);

        double aplha = aArea / area;
        double betha = bArea / area;
        double gamma = cArea / area;

        if (isZero(aplha + betha + gamma - 1) && alignZero(aplha) > 0 && alignZero(betha) > 0 && alignZero(gamma) > 0)
            return List.of(q);

        return null;

    }
}
