package primitives;

import java.util.List;

import static primitives.Util.isZero;

/**
 * This class represents a ray in 3D space.
 * It is defined by a starting point (head) and a direction.
 */
public class Ray {
    final Point head;
    final Vector direction;

    /**
     * Constructs a new ray with the specified head and direction.
     *
     * @param head the starting point of the ray
     * @param direction the direction of the ray
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }
    /**
     * Returns the starting point of the ray.
     *
     * @return starting point of ray.
     */
    public Point getP0() {
        return head;
    }

    /**
     * Returns the directional vector of the ray.
     *
     * @return directional vector of ray.
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * Calculates a point on the ray at a given distance from the origin.
     *
     * @param t the distance from the origin point, can be any real number
     * @return the point on the ray at distance t from the origin
     */
    public Point getPoint(double t) {
        if (isZero(t)) {
            return head;
        }
        return head.add(direction.scale(t));
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        return (o instanceof Ray ray
                && head.equals(ray.head)
                && direction.equals(ray.direction));
    }

    @Override
    public int hashCode() {
        int result = head.hashCode();
        result = 31 * result + direction.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }

    /**
     * find the closest point to head from a list of points
     *
     * @param points list of points
     * @return closest point
     */
    public Point findClosestPoint(List<Point> points) {
        Point closestPoint = null;
        double closestDistance = Double.POSITIVE_INFINITY;
        for (Point point : points) {
            double distance = point.distance(head);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPoint = point;
            }
        }
        return closestPoint;
    }



}