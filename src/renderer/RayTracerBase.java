package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract base class for ray tracers.
 * Provides the basic structure for tracing rays in a scene.
 */
public abstract class RayTracerBase {
    /**
     * The scene to render.
     */
    protected final Scene scene;

    /**
     * Constructs a RayTracerBase with a specific scene.
     *
     * @param scene the scene to render
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Abstract method to trace a ray and return the color.
     *
     * @param ray the ray to trace
     * @return the color at the intersection of the ray with the scene
     */
    public abstract Color traceRay(Ray ray);
}
