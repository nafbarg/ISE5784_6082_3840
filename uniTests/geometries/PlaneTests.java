package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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



}