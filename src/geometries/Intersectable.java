package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable is an abstract class that represents an object that can be intersected by a ray.
 */
public abstract class Intersectable {

    /**
     * Finds the intersections of the given ray with the object.
     *
     * @param ray The ray to find intersections with.
     * @return A list of intersection points, or null if there are no intersections.
     */
    public final List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * The point of intersection between a ray and a geometry.
     */
    public static class GeoPoint {
        /**
         * The geometry that was intersected.
         */
        public Geometry geometry;
        /**
         * The point of intersection.
         */
        public Point point;

        /**
         * Constructs a new GeoPoint with the specified geometry and point.
         *
         * @param geometry the geometry that was intersected
         * @param point    the point of intersection
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }


        @Override
        public String toString() {
            return "GeoPoint [geometry=" + geometry + ", point=" + point + "]";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            return obj instanceof GeoPoint other && geometry.equals(other.geometry) && point.equals(other.point);
        }
    }

    /**
     * Finds the intersections of the given ray with the object.
     *
     * @param ray The ray to find intersections with.
     * @return A list of intersection points as GeoPoints, or null if there are no intersections.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    /**
     * Finds the intersections of the given ray with the object.
     *
     * @param ray The ray to find intersections with.
     * @return A list of intersection points as GeoPoints, or null if there are no intersections.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

}
