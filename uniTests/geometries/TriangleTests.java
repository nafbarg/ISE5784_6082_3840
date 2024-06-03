package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTests {

    /**
     * Test method for {@link geometries.Triangle#Triangle(primitives.Point, primitives.Point, primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A point on the triangle
        Triangle t = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        Vector expectedNormal1 = new Vector(0, 0, 1);
        Vector expectedNormal2 = new Vector(0, 0, -1);
        Vector actualNormal = t.getNormal(new Point(0, 0, 0));
        // Check if the actual normal matches either of the expected normals
        boolean matchesExpected1 = actualNormal.equals(expectedNormal1);
        boolean matchesExpected2 = actualNormal.equals(expectedNormal2);
        assertTrue(matchesExpected1 || matchesExpected2, "Bad normal to triangle");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Triangle triangle = new Triangle(new Point(0, 0, 2),
                new Point(0, 1, 0),
                new Point(0, -1, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: intersection inside the triangle
        Point p0 = new Point(0,0,1);
        List<Point> result = triangle.findIntersections(new Ray(new Point(1,0,0),
                new Vector(-1,0,1)));
        assertEquals(1, result.size(),"it must be one intersection");
        assertEquals(List.of(p0), result, "it's not the right point...");
        //TC02: intersection outside and against edge
        assertNull(triangle.findIntersections(new Ray(new Point(1,0,0),
                new Vector(-1,0,-1))),"the point is outside against edge...");
        //TC03: intersection outside and against vertex
        assertNull(triangle.findIntersections(new Ray(new Point(1,0,0),
                new Vector(-1,0,4))),"the point is outside against vertex...");
        // =============== Boundary Values Tests ==================
        //TC11: intersection on edge
        assertNull(triangle.findIntersections(new Ray(new Point(1,0,0),
                new Vector(-1,0.5,0))),"the point is on the edge...");
        //TC12: intersection on vertex
        assertNull(triangle.findIntersections(new Ray(new Point(1,0,0),
                new Vector(-1,1,0))),"the point is on the vertex...");
        //TC13: intersection on edge's continuation
        assertNull(triangle.findIntersections(new Ray(new Point(1,0,0),
                new Vector(-1,4,0))),"the point is on the edge's continuation...");
    }

}