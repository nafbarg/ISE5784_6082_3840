package primitives;

import org.junit.jupiter.api.Test;

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
}