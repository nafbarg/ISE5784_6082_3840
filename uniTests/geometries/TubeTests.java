package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;
import java.util.List;

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

    /**
     * Test method for {@link geometries.Tube#findGeoIntersectionsHelper(primitives.Ray)}.
     */
    @Test
    void testFindGeoIntersectionsHelper() {
        // Setup: Create a tube
        Ray axisRay = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        double radius = 1.0;
        Tube tube = new Tube(radius, axisRay);

        // ============ Equivalence Partitions Tests ==============

        // EP1: Ray outside and parallel to the tube - no intersections
        Ray ray1 = new Ray(new Point(2, 0, 1), new Vector(0, 0, 1));
        List<GeoPoint> result1 = tube.findGeoIntersectionsHelper(ray1);
        assertNull(result1, "EP1: Ray outside and parallel to the tube should not intersect");

        // EP2: Ray is inside the tube but doesn't intersect (parallel) - no intersections
        Ray ray2 = new Ray(new Point(0.5, 0, 1), new Vector(0, 0, 1));
        List<GeoPoint> result2 = tube.findGeoIntersectionsHelper(ray2);
        assertNull(result2, "EP2: Ray inside the tube but parallel to the axis should not intersect");

        // EP3: Ray starts outside the tube and intersects the tube twice
        Ray ray3 = new Ray(new Point(2, 0, 1), new Vector(-1, 0, 0));
        List<GeoPoint> result3 = tube.findGeoIntersectionsHelper(ray3);
        assertEquals(2, result3.size(), "EP3: Ray should intersect the tube twice");

        // EP4: Ray starts inside the tube and intersects the tube once
        Ray ray4 = new Ray(new Point(0.5, 0, 1), new Vector(1, 0, 0));
        List<GeoPoint> result4 = tube.findGeoIntersectionsHelper(ray4);
        assertEquals(1, result4.size(), "EP4: Ray should intersect the tube once");

        // ============ Boundary Tests ==============

        // BT1: Ray is tangent to the tube - no intersections
        Ray ray5 = new Ray(new Point(1, 0, 1), new Vector(0, 1, 0));
        List<GeoPoint> result5 = tube.findGeoIntersectionsHelper(ray5);
        assertNull(result5, "BT2: Ray tangent to the tube should not intersect");

        // BT2: Ray originates on the surface of the tube and goes outward - no intersections
        Ray ray6 = new Ray(new Point(1, 0, 1), new Vector(1, 0, 0));
        List<GeoPoint> result6 = tube.findGeoIntersectionsHelper(ray6);
        assertNull(result6, "BT2: Ray originating on the tube and pointing outward should not intersect");

        // BT3: Ray originates on the surface of the tube and goes inward - one intersection
        Ray ray7 = new Ray(new Point(1, 0, 1), new Vector(-1, 0, 0));
        List<GeoPoint> result7 = tube.findGeoIntersectionsHelper(ray7);
        assertEquals(1, result7.size(), "BT3: Ray originating on the tube and pointing inward should intersect once");
    }


}