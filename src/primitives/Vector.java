package primitives;

import static primitives.Util.alignZero;

/**
 * This class represents a vector in 3D space.
 * It extends the Point class and provides additional operations specific to vectors.
 */
public class Vector extends Point{

    /**
     * Constructs a new vector with the specified coordinates.
     *
     * @param x the x-coordinate of the vector
     * @param y the y-coordinate of the vector
     * @param z the z-coordinate of the vector
     * @throws IllegalArgumentException if the vector is (0,0,0)
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector(0,0,0) is not a valid vector");
    }

    /**
     * Constructs a new vector with the specified coordinates.
     *
     * @param xyz the coordinates of the vector
     * @throws IllegalArgumentException if the vector is (0,0,0)
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector(0,0,0) is not a valid vector");
    }

    @Override
    public String toString() {
        return "Vector{" + xyz + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;

        return xyz.equals(vector.xyz);
    }

    /**
     * Adds another vector to this vector, resulting in a new vector.
     *
     * @param vector the other vector
     * @return the new vector
     */
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * Scales this vector by a scalar, resulting in a new vector.
     *
     * @param scalar the scalar to scale by
     * @return the new vector
     */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     * Returns the squared length of this vector.
     *
     * @return the squared length of this vector
     */
    public double lengthSquared() {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }

    /**
     * Returns the length of this vector.
     *
     * @return the length of this vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes this vector, resulting in a new vector.
     *
     * @return the normalized vector
     * @throws ArithmeticException if the vector is (0,0,0)
     */
    public Vector normalize() {
        double length = alignZero(length());
        if (length == 0)
            throw new ArithmeticException("Cannot normalize Vector(0,0,0)");

        return new Vector(xyz.scale(1 / length));
    }

    /**
     * Calculates the dot product of this vector and another vector.
     *
     * @param vector the other vector
     * @return the dot product
     */
    public double dotProduct(Vector vector) {
        return alignZero(xyz.d1 * vector.xyz.d1 + xyz.d2 * vector.xyz.d2 + xyz.d3 * vector.xyz.d3);
    }

    /**
     * Calculates the cross product of this vector and another vector.
     *
     * @param vector the other vector
     * @return the cross product
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(
                xyz.d2 * vector.xyz.d3 - xyz.d3 * vector.xyz.d2,
                xyz.d3 * vector.xyz.d1 - xyz.d1 * vector.xyz.d3,
                xyz.d1 * vector.xyz.d2 - xyz.d2 * vector.xyz.d1);
    }
}
