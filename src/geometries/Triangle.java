package geometries;

import primitives.*;
import java.util.List;

import static primitives.Util.alignZero;

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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        // First, check if the ray intersects the plane of the triangle
        if (plane.findIntersections(ray) == null)
            return null;

        // If the ray intersects the plane, get the intersection point
        Point p = plane.findIntersections(ray).getFirst();

        Point p0 = ray.getP0();
        Vector v = ray.getDirection();

        // Calculate the vectors from the head of the ray to each of the vertices
        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector v3 = vertices.get(2).subtract(p0);

        // Calculate the normal vectors of the triangles of the pyramid
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        // Calculate the dot product of the ray direction vector with the normal vectors
        double s1 = alignZero(v.dotProduct(n1));
        double s2 = alignZero(v.dotProduct(n2));
        double s3 = alignZero(v.dotProduct(n3));

        // Check if the intersection point is inside the triangle
        if (s1 > 0 && s2 > 0 && s3 > 0 || s1 < 0 && s2 < 0 && s3 < 0)
            return List.of(new GeoPoint(this, p));

        // If the intersection point is not inside the triangle
        return null;
    }
}
