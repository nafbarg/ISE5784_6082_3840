package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTests {
    /**
     * Test method for {@link geometries.Tube#Tube(double, primitives.Ray)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A point on the tube
        Tube t = new Tube(1, new Ray(new Point(0, 0, 1), new primitives.Vector(0, 0, 1)));
        Vector expectedNormal = new Vector(0, 1, 0).normalize();
        Vector actualNormal = t.getNormal(new Point(0, 1, 2));
        assertEquals(expectedNormal, actualNormal, "The normal vector for tube is incorrect");


        // =============== Boundary Values Tests ==================
        // TC11: The point "is in front of the head of the ray"
         t = new Tube(1, new Ray(new Point(0, 0, 1), new primitives.Vector(0, 0, 1)));
        expectedNormal = new Vector(0, 1, 0).normalize();
        actualNormal = t.getNormal(new Point(0, 1, 1));
        assertEquals(expectedNormal, actualNormal, "The normal vector for tube is incorrect");
    }


}