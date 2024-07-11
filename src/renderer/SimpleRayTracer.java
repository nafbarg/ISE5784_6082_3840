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
            if (alignZero(geoPoint.point.distance(gp.point) - lightDistance) <= 0)
                return false;
        }
        return true;
    }

    /**
     * Traces a ray and returns the color at the intersection point.
     * If there are no intersections, the background color of the scene is returned.
     *
     * @param ray the ray to be traced
     * @return the color at the intersection point or the background color if no intersections are found
     */
    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null
                ? scene.background
                : calcColor(ray.findClosestGeoPoint(intersections), ray);
    }

    /**
     * Calculates the color of the intersection point by considering the ambient light and local effects.
     *
     * @param intersection the intersection point
     * @param ray the ray that intersects the geometry
     * @return the color at the intersection point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity().add(calcLocalEffects(intersection, ray));
    }


    private Color calcColor(GeoPoint intersection, Ray ray,
                            int level, double k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return 1 == level ? Color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    private Color calcGlobalEffect(Ray ray, int level, Double k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
        return (gp == null ? scene.background : calcColor(gp, ray, level-1, kkx)).scale(kx);
    }

    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        return calcGLobalEffect(constructRefractedRay(gp, ray), material.kr, level, k)
                .add(calcColorGLobalEffect(constructReflectedRay(gp, ray), material.kt, level, k));
    }

    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level-1, kkx)).scale(kx);
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
