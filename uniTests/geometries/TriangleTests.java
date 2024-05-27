package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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

}