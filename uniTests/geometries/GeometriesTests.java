package geometries;


import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {

    @Test
    void testFindIntersections() {
        // prepare geometries
        Geometries geometries = new Geometries(
                new Sphere(1, new Point(5, 5, 5)),
                new Plane(new Point(2, 3, 4), new Vector(0, 1, 0)),
                new Triangle(new Point(3, 1, 1), new Point(4, 2, 1), new Point(4, 1, 2))
        );

        // ============ Equivalence Partitions Tests ==============
        // TC01: Some (but not all) geometries are intersected
        assertEquals(3, geometries.findIntersections(new Ray(new Point(6, 6, 6), new Vector(-1, -3, -1))).size(),
                "Wrong number of points");

        // =============== Boundary Values Tests ==================
        // TC11: Empty collection of geometries
        Geometries emptyGeometries = new Geometries();
        assertNull(emptyGeometries.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0))),
                "Empty collection should return null");

        // TC12: No geometries are intersected
        assertNull(geometries.findIntersections(new Ray(new Point(2, 0, 0), new Vector(0, 0, 3))),
                "No intersections but result isn't null");

        // TC13: Single geometry is intersected
        assertEquals(1, geometries.findIntersections(new Ray(new Point(5.5, 5, 5), new Vector(-1, 0, 0))).size(),
                "Wrong number of points");

        // TC14: All geometries are intersected
        geometries = new Geometries(
                new Sphere(1, new Point(5, 5, 5)),
                new Plane(new Point(2, 3, 4), new Vector(0, 1, 0)),
                new Triangle(new Point(4, 4, 4), new Point(6.66, 4, 4), new Point(5.33, 4, 6.66))
        );
        assertEquals(4, geometries.findIntersections(new Ray(new Point(6, 6, 6), new Vector(-1, -3, -1))).size(),
                "Wrong number of points");
    }

}