package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTests {

    //For a plain, you must add a builder's check that gets three points and include two extreme cases:
    //o The first and second points converge
    //o The points are on the same line
    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
     */
    @Test
void testPlane() {
        // =============== Boundary Values Tests =================
        // TC01: The first and second points converge
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(0, 0, 0), new Point(0, 0, 0), new Point(0, 1, 0)));
        // TC02: The points are on the same line
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(0, 0, 0), new Point(1, 0, 0), new Point(2, 0, 0)));
    }


    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
// ============ Equivalence Partitions Tests ==============
        // TC01: A point on the plane
        Plane p = new Plane(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        Vector expectedNormal1 = new Vector(0, 0, 1);
        Vector expectedNormal2 = new Vector(0, 0, -1);
        Vector actualNormal = p.getNormal(new Point(0, 0, 0));
        // Check if the actual normal matches either of the expected normals
        boolean matchesExpected1 = actualNormal.equals(expectedNormal1);
        boolean matchesExpected2 = actualNormal.equals(expectedNormal2);
        assertTrue(matchesExpected1 || matchesExpected2, "Bad normal to plane");
    }

    @Test
    void testFindIntersections() {
        Plane plane = new Plane(new Point(0, 0, 1), new Vector(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane
        Ray ray1 = new Ray(new Point(0.5, 0.5, 0), new Vector(-0.5, -0.5, 1));
        List<Point> result1 = plane.findIntersections(ray1);
        assertNotNull(result1, "Ray intersects the plane");
        assertEquals(1, result1.size(), "Wrong number of points");
        assertEquals(new Point(0, 0, 1), result1.getFirst(), "Ray intersects the plane at (0,0,1)");

        // TC02: Ray does not intersect the plane
        Ray ray2 = new Ray(new Point(0.5, 0.5, 0), new Vector(-0.5, -0.5, -1));
        List<Point> result2 = plane.findIntersections(ray2);
        assertNull(result2, "Ray does not intersect the plane");

        // =============== Boundary Values Tests ==================
        // TC11: Ray is parallel to the plane
        Ray ray3 = new Ray(new Point(0, 0, 1), new Vector(1, 0, 0));
        List<Point> result3 = plane.findIntersections(ray3);
        assertNull(result3, "Ray is parallel to the plane");

        // TC12: Ray is orthogonal to the plane and starts at the plane
        Ray ray4 = new Ray(new Point(0, 0, 1), new Vector(0, 0, 1));
        List<Point> result4 = plane.findIntersections(ray4);
        assertNull(result4, "Ray is orthogonal to the plane and starts at the plane");

        // TC13: Ray is orthogonal to the plane and starts below the plane
        Ray ray5 = new Ray(new Point(0, 1, 0), new Vector(0, 0, 1));
        List<Point> result5 = plane.findIntersections(ray5);
        assertNotNull(result5, "Ray is orthogonal to the plane and starts below the plane");
        assertEquals(1, result5.size(), "Wrong number of points");
        assertEquals(new Point(0, 1, 1), result5.getFirst(), "Ray intersects the plane at (0,0,1)");
    }


}