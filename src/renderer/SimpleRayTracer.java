package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * SimpleRayTracer class represents a simple ray tracer.
 * The simple ray tracer traces rays in a scene and returns the color at the intersection.
 */
public class SimpleRayTracer extends RayTracerBase{

    /**
     * Constructs a SimpleRayTracer with a specific scene.
     *
     * @param scene the scene to render
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }

        Point closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint, ray);
    }

    /**
     * Calculate the color of a point
     *
     * @param point the point to calculate color for
     * @param ray the ray that hit the point
     * @return the color of the point
     */
    private Color calcColor(Point point, Ray ray) {
        return scene.ambientLight.getIntensity();
    }

}
