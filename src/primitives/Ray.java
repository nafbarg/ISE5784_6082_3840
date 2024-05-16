package primitives;

/**
 * This class represents a ray in 3D space.
 * It is defined by a starting point (head) and a direction.
 */
public class Ray {
    private final Double3 head;
    private final Vector direction;

    /**
     * Constructs a new ray with the specified head and direction.
     *
     * @param head the starting point of the ray
     * @param direction the direction of the ray
     */
    public Ray(Double3 head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
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
