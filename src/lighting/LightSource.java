package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * interface LightSource is an interface that represents the light source in the scene
 * it has a color and a direction
 */
public interface LightSource {
    /**
     * Returns the intensity of the light at the specified point.
     *
     * @param p the point at which to calculate the intensity
     * @return the intensity of the light at the point
     */
    public Color getIntensity(Point p);

    /**
     * Returns the direction of the light at the specified point.
     *
     * @param p the point at which to calculate the direction
     * @return the direction of the light at the point
     */
    public Vector getL(Point p);

    /**
     * Returns the distance between the light source and the specified point.
     *
     * @param point the point at which to calculate the distance
     * @return the distance between the light source and the point
     */
    double getDistance(Point point);
}
