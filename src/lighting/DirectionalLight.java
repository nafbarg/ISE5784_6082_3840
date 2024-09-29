package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * class DirectionalLight is a class that represents the directional light in the scene
 * it has a direction
 */
public class DirectionalLight extends Light implements LightSource{
    private final Vector direction;

    /**
     * constructor for DirectionalLight
     * @param intensity the color of the light
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Returns the intensity of the light at the specified point.
     * @param p the point at which to calculate the intensity
     * @return the intensity of the light at the point
     */
    @Override
    public Color getIntensity(Point p) {
        return this.getIntensity();
    }

    /**
     * Returns the direction of the light at the specified point.
     * @param p the point at which to calculate the direction
     * @return the direction of the light at the point
     */
    @Override
    public Vector getL(Point p) {
        return this.direction;
    }

    /**
     * Returns the distance between the light source and the specified point.
     * @param point the point at which to calculate the distance
     * @return the distance between the light source and the point
     */
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }


}