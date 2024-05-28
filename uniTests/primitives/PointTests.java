package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


class PointTests {

    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(4, 5, 6);
        double result = p1.distanceSquared(p2);
        assertEquals(27, result, 1e-10, "Wrong result of distanceSquared()");
        // TC02: Simple test
        result = p2.distanceSquared(p1);
        assertEquals(27, result, 1e-10, "Wrong result of distanceSquared()");

        // =============== Boundary Values Tests ==================
        // TC11: Distance between a point and itself
        assertEquals(0, p1.distanceSquared(p1), 1e-10, "Wrong result when calculating squared distance between a point and itself");

    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(4, 5, 6);
        double result = p1.distance(p2);
        assertEquals(5.196152422706632, result, 1e-10, "Wrong result of distance()");
        // TC02: Simple test
        result = p2.distance(p1);
        assertEquals(5.196152422706632, result, 1e-10, "Wrong result of distance()");

        // =============== Boundary Values Tests ==================
        // TC11: Distance between a point and itself
        assertEquals(0, p1.distance(p1), 1e-10, "Wrong result when calculating distance between a point and itself");

    }

    /**
     * Test method for {@link primitives.Point#add(primitives.Point)}
     */
    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        Point p1 = new Point(1, 2, 3);
        Vector p2 = new Vector(0, -1, -2);
        Point p3 = p1.add(p2);
        assertEquals(new Point(1, 1, 1), p3, "Wrong result of add()");

        // =============== Boundary Values Tests ==================
        // TC10: Adding a vector with very large values to a point
        Vector largeVector = new Vector(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        Point p4 = p1.add(largeVector);
        assertEquals(new Point(1 + Double.MAX_VALUE, 2 + Double.MAX_VALUE, 3 + Double.MAX_VALUE), p4, "Wrong result when adding large vector");
        // TC11: Adding an opposite vector to a point
        Point p5 = p1.add(new Vector(-1, -2, -3));
        assertEquals(Point.ZERO, p5, "Wrong result when adding opposite vector");
    }

    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(0, -1, -2);
        Vector p3 = p1.subtract(p2);
        assertEquals(new Vector(1, 3, 5), p3, "Wrong result of subtract()");

        // =============== Boundary Values Tests ==================
        // TC10: Subtracting a point with very large values from a point
        Point largePoint = new Point(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        Vector p4 = p1.subtract(largePoint);
        assertEquals(new Vector(1 - Double.MAX_VALUE, 2 - Double.MAX_VALUE, 3 - Double.MAX_VALUE), p4, "Wrong result when subtracting large point");
        // TC11: Subtracting a point from itself
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), "Subtracting a point from itself should throw an exception");
    }
}