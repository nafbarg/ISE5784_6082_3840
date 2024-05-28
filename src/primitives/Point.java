package primitives;

/**
 * This class represents a point in 3D space.
 * It provides methods to perform operations on these points.
 */
public class Point {
    /**
     * A 3D point represented by double precision coordinates.
     */
    final protected Double3 xyz;

    /**
     * A constant representing a point at the origin (0,0,0).
     */
    public static final Point ZERO = new Point(0, 0, 0);

    /**
     * Constructs a new point with the specified coordinates.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * Constructs a new point with the specified coordinates.
     *
     * @param xyz the coordinates of the point
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return (o instanceof Point point
                && xyz.equals(point.xyz));
    }

    @Override
    public int hashCode() {
        return xyz.hashCode();
    }

    @Override
    public String toString() {
        return "Point{" + xyz + '}';
    }

    /**
     * Calculates the squared distance between this point and another point.
     *
     * @param point the other point
     * @return the squared distance
     */
    public double distanceSquared(Point point) {
        double dx = xyz.d1 - point.xyz.d1;
        double dy = xyz.d2 - point.xyz.d2;
        double dz = xyz.d3 - point.xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Calculates the squared distance between this point and another point.
     *
     * @param point the other point
     * @return the squared distance
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }

    /**
     * Adds a vector to this point, resulting in a new point.
     *
     * @param vector the vector to add
     * @return the new point
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }


    /**
     * Subtracts another point from this point, resulting in a vector.
     *
     * @param point the other point
     * @return the resulting vector
     */
    public Vector subtract(Point point) {
        return new Vector(xyz.subtract(point.xyz));
    }
}
