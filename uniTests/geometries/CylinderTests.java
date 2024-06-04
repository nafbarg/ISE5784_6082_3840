package geometries;

import geometries.Cylinder;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link geometries.Cylinder}.
 */
public class CylinderTests {

    @Test
    void testGetNormal() {
    }

    /**
     * Test method for {@link geometries.Cylinder#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Cylinder cylinder = new Cylinder(1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the cylinder (0 points)
        assertNull(cylinder.findIntersections(new Ray(new Point(3, 3, 0), new Vector(0, 0, 1))),
                "Ray's line is outside the cylinder");

        // TC02: Ray starts before and crosses the cylinder (2 points)
        Point p1 = new Point(1, 1, 0);
        Point p2 = new Point(1, 1, 2);
        List<Point> result = cylinder.findIntersections(new Ray(new Point(1, 1, -1), new Vector(0, 0, 1)));
        assertEquals(2, result.size(), "Wrong number of points");
        assertTrue(result.contains(p1) && result.contains(p2), "Ray crosses cylinder");

        // TC03: Ray starts inside the cylinder (1 point)
        Point p3 = new Point(0, 0, 1);
        result = cylinder.findIntersections(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertTrue(result.contains(p3), "Ray inside cylinder");

        // TC04: Ray starts after the cylinder (0 points)
        assertNull(cylinder.findIntersections(new Ray(new Point(0, 0, 3), new Vector(0, 0, 1))),
                "Ray's line is outside the cylinder");

        // =============== Boundary Values Tests ==================
        // TC11: Ray's line is tangent to the cylinder (1 point)
        Point p4 = new Point(1, 1, 2);
        result = cylinder.findIntersections(new Ray(new Point(1, 1, 0), new Vector(1, 0, 2)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertTrue(result.contains(p4), "Ray tangent to cylinder");

        // TC12: Ray's line is parallel to the cylinder (0 points)
        assertNull(cylinder.findIntersections(new Ray(new Point(1, 1, 0), new Vector(1, 0, 0))),
                "Ray's line is parallel to the cylinder");
    }
}
