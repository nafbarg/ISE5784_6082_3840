package primitives;

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
    public Vector getDir() {
        return direction;
    }

    public Point getPoint(double t) {
        if (isZero(t)) {
            throw new IllegalArgumentException("scale zero is illegal");
            return head.add(direction.scale(t));

        } else {
            return head;
        }
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


}
