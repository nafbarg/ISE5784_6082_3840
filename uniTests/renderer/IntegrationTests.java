package renderer;
import geometries .*;
import org.junit.jupiter.api.Test;
import primitives .*;
import static org.junit.jupiter.api.Assertions .*;

/**
 * Integration tests between Camera and geometries
 */
public class IntegrationTests {

    /**
     * Helper method to count intersections
     *
     * @param camera the camera
     * @param geometry the geometry to intersect with
     * @return the total number of intersections
     */
    private int countIntersections(Camera camera, Intersectable geometry) {
        int count = 0;
        int nX = 3;
        int nY = 3;

        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                Ray ray = camera.constructRay(nX, nY, j, i);
                var intersections = geometry.findIntersections(ray);
                if (intersections != null) {
                    count += intersections.size();
                }
            }
        }
        return count;
    }

    Camera camera = Camera.getBuilder()
            .setLocation(new Point(0, 0, 0))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpSize(3, 3)
            .setVpDistance(1)
            .build();

    /**
     * Test integration between Camera and Sphere
     */
    @Test
    public void testSphereIntersections() {
        // TC01: Camera is looking at the sphere
        Sphere sphere = new Sphere(1, new Point(0, 0, -3));

        int intersections = countIntersections(camera, sphere);
        assertEquals(2, intersections, "Wrong number of intersections with the sphere");

        // TC02: Camera is looking at the sphere from the side
        sphere = new Sphere(2.5, new Point(0, 0, -2.5));

        intersections = countIntersections(camera, sphere);
        assertEquals(18, intersections, "Wrong number of intersections with the sphere");


        // TC03: Camera is looking at the sphere from the side
        camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, 0.5))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .build();

        sphere = new Sphere(2, new Point(0, 0, -2));

        intersections = countIntersections(camera, sphere);
        assertEquals(10, intersections, "Wrong number of intersections with the sphere");

        // TC04: Camera is inside the sphere
        camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, -0.5))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .build();

        sphere = new Sphere(5, new Point(0, 0, -1));

        intersections = countIntersections(camera, sphere);
        assertEquals(9, intersections, "Wrong number of intersections with the sphere");

        // TC05: Camera isn't looking at the sphere
        sphere = new Sphere(0.5, new Point(0, 0, 1));
        intersections = countIntersections(camera, sphere);
        assertEquals(0, intersections, "Wrong number of intersections with the sphere");
    }

    /**
     * Test integration between Camera and Plane
     */
    @Test
    public void testPlaneIntersections() {
        // TC01: The plane is parallel to the view plane
        Plane plane = new Plane(new Point(0, 0, -4), new Vector(0, 0, 1));

        int intersections = countIntersections(camera, plane);
        assertEquals(9, intersections, "Wrong number of intersections with the plane");

        // TC02: The plane with a slight slope to the view plane
        plane = new Plane(new Point(0, 0, -4), new Vector(1, 1, -1));

        intersections = countIntersections(camera, plane);
        assertEquals(9, intersections, "Wrong number of intersections with the plane");

        // TC03:only part of the plane is visible
        plane = new Plane(new Point(0, 0, -4), new Vector(0, 1, 1));

        intersections = countIntersections(camera, plane);
        assertEquals(6, intersections, "Wrong number of intersections with the plane");
    }

    /**
     * Test integration between Camera and Triangle
     */
    @Test
    public void testTriangleIntersections() {
        // TC01: The triangle is parallel to the center of the view plane

        Triangle triangle = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));

        int intersections = countIntersections(camera, triangle);
        assertEquals(1, intersections, "Wrong number of intersections with the triangle");

        // TC02: The triangle is parallel to whole view plane
        triangle = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));

        intersections = countIntersections(camera, triangle);
        assertEquals(2, intersections, "Wrong number of intersections with the triangle");
    }

}






