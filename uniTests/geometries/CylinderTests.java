//package geometries;
//
//import org.junit.jupiter.api.Test;
//import primitives.Point;
//import primitives.Ray;
//import primitives.Vector;
//import geometries.Intersectable.GeoPoint;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Test class for {@link geometries.Cylinder}.
// */
//public class CylinderTests {
//
//    @Test
//    public void testGetNormal() {
//        // ============ Equivalence Partitions Tests ==============
//        Cylinder cylinder = new Cylinder(1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 5);
//
//        // TC01: point on the side of the cylinder
//        Point pointOnSide = new Point(1, 0, 2);
//        Vector expectedNormalSide = new Vector(1, 0, 0).normalize();
//        assertEquals(expectedNormalSide, cylinder.getNormal(pointOnSide), "TC01: Bad normal for point on the side of the cylinder");
//
//        // TC02: point on the bottom base of the cylinder
//        Point pointOnBottomBase = new Point(0, 0, 0);
//        Vector expectedNormalBottomBase = new Vector(0, 0, -1).normalize();
//        assertEquals(expectedNormalBottomBase, cylinder.getNormal(pointOnBottomBase), "TC02: Bad normal for point on the bottom base of the cylinder");
//
//        // TC03: point on the edge of the bottom base
//        Point pointOnBottomEdge = new Point(0.5, 0.5, 0);
//        assertEquals(expectedNormalBottomBase, cylinder.getNormal(pointOnBottomEdge), "TC03: Bad normal for point on the edge of the bottom base");
//
//        // =============== Boundary Values Tests ==================
//        // TC04: point on the top base of the cylinder
//        Point pointOnTopBase = new Point(0, 0, 5);
//        Vector expectedNormalTopBase = new Vector(0, 0, 1).normalize();
//        assertEquals(expectedNormalTopBase, cylinder.getNormal(pointOnTopBase), "TC04: Bad normal for point on the top base of the cylinder");
//
//        // TC05: point on the edge of the top base
//        Point pointOnTopEdge = new Point(0.5, 0.5, 5);
//        assertEquals(expectedNormalTopBase, cylinder.getNormal(pointOnTopEdge), "TC05: Bad normal for point on the edge of the top base");
//    }
//
//    /**
//     * Test method for {@link geometries.Cylinder#findIntersections(primitives.Ray)}.
//     */
//    @Test
//    public void testFindIntersections() {
//        Cylinder cylinder = new Cylinder(1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 2);
//
//        // ============ Equivalence Partitions Tests ==============
//        // TC01: Ray's line is outside the cylinder (0 points)
//        assertNull(cylinder.findIntersections(new Ray(new Point(3, 3, 0), new Vector(0, 0, 1))),
//                "Ray's line is outside the cylinder");
//
//        // TC02: Ray starts before and crosses the cylinder (2 points)
//        Point p1 = new Point(1, 1, 0);
//        Point p2 = new Point(1, 1, 2);
//        List<Point> result = cylinder.findIntersections(new Ray(new Point(1, 1, -1), new Vector(0, 0, 1)));
////        assertEquals(2, result.size(), "Wrong number of points");
////        assertTrue(result.contains(p1) && result.contains(p2), "Ray crosses cylinder");
//
//        // TC03: Ray starts inside the cylinder (1 point)
//        Point p3 = new Point(0, 0, 1);
//        result = cylinder.findIntersections(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)));
//        assertEquals(1, result.size(), "Wrong number of points");
//        assertTrue(result.contains(p3), "Ray inside cylinder");
//
//        // TC04: Ray starts after the cylinder (0 points)
//        assertNull(cylinder.findIntersections(new Ray(new Point(0, 0, 3), new Vector(0, 0, 1))),
//                "Ray's line is outside the cylinder");
//
//        // =============== Boundary Values Tests ==================
//        // TC11: Ray's line is tangent to the cylinder (1 point)
//        Point p4 = new Point(1, 1, 2);
//        result = cylinder.findIntersections(new Ray(new Point(1, 1, 0), new Vector(1, 0, 2)));
//        assertEquals(1, result.size(), "Wrong number of points");
//        assertTrue(result.contains(p4), "Ray tangent to cylinder");
//
//        // TC12: Ray's line is parallel to the cylinder (0 points)
//        assertNull(cylinder.findIntersections(new Ray(new Point(1, 1, 0), new Vector(1, 0, 0))),
//                "Ray's line is parallel to the cylinder");
//    }
//
//    @Test
//    public void testFindGeoIntersectionsHelper() {
//        Cylinder cylinder = new Cylinder(1, new Ray(Point.ZERO, new Vector(0, 0, 1)), 2);
//
//        // Test case 1: Ray from above, not intersecting the cylinder
//        Ray ray1 = new Ray(new Point(0, 3, 3), new Vector(0, 0, -1));
//        assertNull(cylinder.findGeoIntersectionsHelper(ray1), "Ray should not intersect cylinder");
//
//        // Test case 2: Ray intersecting the side surface at two points (general case of double intersection)
//        Ray ray2 = new Ray(new Point(-2, 0, 1), new Vector(1, 0, 0));
//        List<GeoPoint> result2 = cylinder.findGeoIntersectionsHelper(ray2);
//        assertNotNull(result2, "Ray should intersect cylinder");
//        assertEquals(result2.size(), 2,"Should have two intersection points" );
//
//        // Test case 3: Ray entering and exiting through bases
//        Ray ray3 = new Ray(new Point(0, 0, -1), new Vector(0, 0, 1));
//        List<GeoPoint> result3 = cylinder.findGeoIntersectionsHelper(ray3);
//        assertNotNull(result3,"Ray should intersect cylinder");
//        assertEquals(2, result3.size(),"Should have two intersection points");
//
//        // Test case 4: Ray touching the edge of the base (tangent)
//        Ray ray4 = new Ray(new Point(1, 0, -1), new Vector(0, 1, 1));
//        List<GeoPoint> result4 = cylinder.findGeoIntersectionsHelper(ray4);
//        assertNotNull(result4, "Ray should intersect cylinder");
//        assertEquals(1, result4.size(),"Should have one intersection point");
//
//        // Test case 5: Ray inside the cylinder along the axis
//        Ray ray5 = new Ray(new Point(0, 0, 0.5), new Vector(0, 0, 1));
//        List<GeoPoint> result5 = cylinder.findGeoIntersectionsHelper(ray5);
//        assertNotNull( result5, "Ray should intersect cylinder");
//        assertEquals(1, result5.size(),"Should have one intersection point");
//
//        // Test case 6: Ray passing through the center of bases
//        Ray ray6 = new Ray(new Point(0, 0, -1), new Vector(0, 0, 1));
//        List<GeoPoint> result6 = cylinder.findGeoIntersectionsHelper(ray6);
//        assertNotNull(result6, "Ray should intersect cylinder");
//        assertEquals(2, result6.size(), "Should have two intersection points");
//
//        // Test case 7: Ray parallel to cylinder axis and outside
//        Ray ray7 = new Ray(new Point(2, 0, 0), new Vector(0, 0, 1));
//        assertNull(cylinder.findGeoIntersectionsHelper(ray7), "Ray should not intersect cylinder");
//
//        // Test case 8: Ray entering through one side and exiting through the other side surface
//        Ray ray8 = new Ray(new Point(-2, 0, 1), new Vector(1, 0, 0.1));
//        List<GeoPoint> result8 = cylinder.findGeoIntersectionsHelper(ray8);
//        assertNotNull(result8, "Ray should intersect cylinder");
//        assertEquals(2, result8.size(),"Should have two intersection points");
//    }
//}
