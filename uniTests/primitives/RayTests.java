package primitives;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 */
class RayTests {

    /**
     * Test method for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    public void testGetPoint() {
        // Arrange
        Point p0 = new Point(1, 2, 3);
        Vector dir = new Vector(1, 0, 0);
        Ray ray = new Ray(p0, dir);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test getPoint() with positive distance
        Point resultPositive = ray.getPoint(5);
        Point expected = ray.getP0().add(ray.getDirection().scale(5));
        assertEquals(expected, resultPositive, "getPoint() for positive distance is incorrect");

        // TC02: Test getPoint() with negative distance
        Point resultNegative = ray.getPoint(-5);
        expected = ray.getP0().add(ray.getDirection().scale(-5));
        assertEquals(expected, resultNegative, "getPoint() for negative distance is incorrect");

        // =============== Boundary Values Tests ==================
        // TC03: Test getPoint() with zero distance
        Point resultZero = ray.getPoint(0);
        assertEquals(p0, resultZero, "getPoint() for zero distance is incorrect");
    }

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(java.util.List)}.
     */
    @Test
    public void testFindClosestPoint() {
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        // The middle point in the list is the closest
        Point p1 = new Point(1, 1, 1);
        Point p2 = new Point(0.5, 0.5, 0.5);
        Point p3 = new Point(2, 2, 2);
        List<Point> points = Arrays.asList(p1, p2, p3);
        assertEquals(p2, ray.findClosestPoint(points), "Middle point should be the closest");

        // =============== Boundary Values Tests ==================
        // TC01: Empty list
        points = Arrays.asList();
        assertNull(ray.findClosestPoint(points), "Empty list should return null");

        // TC02: The first point is the closest
        points = Arrays.asList(p1, p3);
        assertEquals(p1, ray.findClosestPoint(points), "First point should be the closest");

        // TC03: The last point is the closest
        points = Arrays.asList(p3, p1);
        assertEquals(p1, ray.findClosestPoint(points), "Last point should be the closest");
    }
}