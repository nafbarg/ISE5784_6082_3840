package renderer;

import lighting.LightSource;
import primitives.*;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;

/**
 * A simple implementation of a ray tracer.
 * This class extends RayTracerBase and provides a basic ray tracing mechanism.
 */
public class SimpleRayTracer extends RayTracerBase {

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * The delta value used to prevent self-shadowing.
     */
    private static final double DELTA = 0.1;

    /**
     * Constructs a SimpleRayTracer with the given scene.
     *
     * @param scene the scene to be rendered by this ray tracer
     */
    public SimpleRayTracer(scene.Scene scene) {
        super(scene);
    }

    /**
     * Checks if the point is shaded by another geometry.
     *
     * @param gp the intersection point
     * @param l the light direction vector
     * @param n the normal vector at the intersection point
     * @return true if the point is not shaded by another geometry, false otherwise
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource light) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point point = gp.point.add(delta);
        Ray shadowRay = new Ray(point, lightDirection);
        var intersections = scene.geometries.findGeoIntersections(shadowRay);
        if (intersections == null) return true;
        double lightDistance = light.getDistance(gp.point);
        for (GeoPoint geoPoint : intersections) {
            if (alignZero(geoPoint.point.distance(gp.point) - lightDistance) <= 0 && (geoPoint.geometry.getMaterial().kT.equals(Double3.ZERO)))
                return false;
        }
        return true;
    }


    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }


    /**
     * Finds the closest intersection point of a ray with the geometries in the scene.
     * @param ray the ray to find intersections with the geometries
     * @return the closest GeoPoint intersection, or null if there are no intersections
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null ? null : ray.findClosestGeoPoint(intersections);
    }

    /**
     * Calculates the color at a given intersection point, including ambient light.
     * @param geopoint the intersection point
     * @param ray the ray that intersects at the geopoint
     * @return the color at the intersection point
     */
    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }

    /**
     * Recursively calculates the color at an intersection point, considering local and global effects.
     * @param intersection the intersection point
     * @param ray the ray that intersects at the intersection point
     * @param level the current recursion level
     * @param k the attenuation factor for the current recursion level
     * @return the calculated color at the intersection point
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * Calculates the global effects (reflection and refraction) at an intersection point.
     * @param gp the intersection point
     * @param ray the ray that intersects at the intersection point
     * @param level the current recursion level
     * @param k the attenuation factor for the current recursion level
     * @return the calculated color considering global effects
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(gp, ray), material.kR, level, k)
                .add(calcGlobalEffect(constructReflectedRay(gp, ray), material.kT, level, k));
    }

    /**
     * Calculates the global effect (either reflection or refraction) for a given ray.
     * @param ray the ray to calculate the global effect for
     * @param k the attenuation factor for the current recursion level
     * @param level the current recursion level
     * @param kx the attenuation factor for the specific global effect
     * @return the calculated color considering the global effect
     */
    private Color calcGlobalEffect(Ray ray, Double3 k, int level, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }


    /**
     * Helper method to construct the refracted ray.
     * @param geoPoint the intersection point
     * @param ray the original ray
     * @return the refracted ray
     */
    private Ray constructRefractedRay(GeoPoint geoPoint, Ray ray) {
        Vector direction = ray.getDirection();
        Point point = geoPoint.point;

        // Move the starting point slightly in the direction of the ray to avoid self-intersection
        Vector deltaVector = direction.scale(DELTA);
        Point movedPoint = point.add(deltaVector);

        return new Ray(movedPoint, direction);
    }

    /**
     * Helper method to construct the reflected ray.
     * @param geoPoint the intersection point
     * @param ray the original ray
     * @return the reflected ray
     */
    private Ray constructReflectedRay(GeoPoint geoPoint, Ray ray) {
        Point point = geoPoint.point;
        Vector direction = ray.getDirection();
        Vector normal = geoPoint.geometry.getNormal(point);

        // Calculate the reflected direction
        Vector reflectedDirection = direction.subtract(normal.scale(2 * direction.dotProduct(normal)));

        // Move the starting point slightly in the direction of the reflected ray to avoid self-intersection
        Vector deltaVector = normal.scale(DELTA);
        Point movedPoint = point.add(deltaVector);

        return new Ray(movedPoint, reflectedDirection);
    }



    /**
     * Calculates the color of the intersection point by considering the local effects only.
     *
     * @param gp the intersection point
     * @param ray the ray that intersects the geometry
     * @return the color at the intersection point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return null;
        Material material = gp.geometry.getMaterial();
        Color color = gp.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if ((nl * nv > 0) && unshaded(gp, l, n, lightSource)){
                // sign(nl) == sign(nv);
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(material, nl).add(calcSpecular(material, n, l, nl, v))));
            }
        }
        return color;
    }

    /**
     * Calculates the diffusive component of the lighting model.
     *
     * @param material the material of the intersected geometry
     * @param nl the dot product of the normal and the light direction
     * @return the diffusive component as a Double3
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * Calculates the specular component of the lighting model.
     *
     * @param material the material of the intersected geometry
     * @param n the normal vector at the intersection point
     * @param l the light direction vector
     * @param nl the dot product of the normal and the light direction
     * @param v the view direction vector
     * @return the specular component as a Double3
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl));
        double vr = -alignZero(v.dotProduct(r));
        double max = Math.max(0, vr);
        return material.kS.scale(Math.pow(max, material.nShininess));
    }
}
