//package geometries;
//
//import primitives.*;
//
//import java.util.List;
//
//import static primitives.Util.isZero;
//
///**
// * Cylinder represents a cylinder in 3D space.
// */
//public class Cylinder extends Tube{
//    private final double height;
//
//    /**
//     * Constructs a new cylinder with the specified radius, axis, and height.
//     *
//     * @param radius the radius of the cylinder
//     * @param axis the axis of the cylinder
//     * @param height the height of the cylinder
//     */
//    public Cylinder(double radius, Ray axis, double height) {
//        super(radius, axis);
//        this.height = height;
//    }
//
//    @Override
//    public String toString() {
//        return "Cylinder{" +
//                "radius=" + radius +
//                ", axis=" + axis +
//                ", height=" + height +
//                '}';
//    }
//
//    @Override
//    public Vector getNormal(Point p) {
//        // Check that surface point is different from head of axisRay to avoid creating
//        // a zero vector
//        Vector dir = axis.getDirection();
//        Point p0 = axis.getP0();
//        if (p.equals(p0))
//            return dir.scale(-1);
//        // Finding the nearest point to the given point that is on the axis ray
//        double t = dir.dotProduct(p.subtract(p0));
//        // Finds out if surface point is on a base and returns a normal appropriately
//        if (isZero(t))
//            return dir.scale(-1);
//        if (isZero(t - height))
//            return dir;
//        // If surface point is on the side of the cylinder, the superclass (Tube) is
//        // used to find the normal
//        return super.getNormal(p);
//    }
//
//    /**
//     * Finds the intersection points of a given ray with the cylinder.
//     *
//     * @param ray the ray to intersect with the cylinder
//     * @return a list of intersection points, or null if there are no intersections
//     */
//    @Override
//    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
//
//
//        // Find geoPoints intersections with the infinite cylinder
//        List<GeoPoint> tubeIntersections = super.findGeoIntersectionsHelper(ray);
//        if (tubeIntersections == null) {
//            return null;
//        }
//
//        // Initialize variables to track the intersection parameters
//        double t1 = Double.POSITIVE_INFINITY;
//        double t2 = Double.NEGATIVE_INFINITY;
//
//        // Check if ray intersects the infinite cylinder
//        for (GeoPoint intersection : tubeIntersections) {
//            Vector v = intersection.point.subtract(axis.getP0());
//            double t = axis.getDirection().dotProduct(v);
//
//            if (t1 > t) {
//                t1 = t;
//            }
//
//            if (t2 < t) {
//                t2 = t;
//            }
//        }
//
//        // Check if ray intersects the finite cylinder
//        double tMin = 0;
//        double tMax = height;
//
//        if (t1 > tMax || t2 < tMin) {
//            return null;
//        }
//
//        // Adjust intersection points to be within the height range
//        Point p1 = ray.getPoint(t1);
//        Point p2 = ray.getPoint(t2);
//
//        // Check if the intersection points are inside the finite cylinder
//        if (p1.getZ() >= tMin && p1.getZ() <= tMax) {
//            if (p2.getZ() >= tMin && p2.getZ() <= tMax) {
//                return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2));
//            } else {
//                return List.of(new GeoPoint(this, p1));
//            }
//        } else if (p2.getZ() >= tMin && p2.getZ() <= tMax) {
//            return List.of(new GeoPoint(this, p2));
//        }
//
//        return null; // No valid intersections found
//    }
//
//}
