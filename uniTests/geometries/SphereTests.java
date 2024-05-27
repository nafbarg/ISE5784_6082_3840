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
        Sphere s = new Sphere(1, new Point(0, 0, 0));
        // The normal to the sphere at the point (0, 0, 0) is the vector (0, 0, 1)
        assertEquals(s.getNormal(new Point(0, 0, 0)), new Vector(0, 0, 1), "Bad normal to sphere");
    }
}