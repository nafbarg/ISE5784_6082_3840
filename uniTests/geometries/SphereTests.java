package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 */
class SphereTests {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A point on the sphere
        Sphere s = new Sphere(5, new Point(3, 2, 1));
        assertEquals(s.getNormal(new Point(3, 6, 4)), new Vector(0, 4, 3).normalize(), "Bad normal to sphere");
        //
    }

}