package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * class PointLight is a class that represents the point light in the scene
 * it has a position
 */
public class PointLight extends Light implements LightSource{
    private final Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    /**
     * constructor for PointLight
     * @param intensity the color of the light
     * @param position the position of the light
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * setter for kC in phong model
     *
     * @param kC constant attenuation factor
     * @return the light itself
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * setter for kL in phong model
     *
     * @param kL light's attenutation factor
     * @return the light itself
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * setter for kQ in phong model
     *
     * @param kQ qudratic attenutation factor
     * @return the light itself
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    // ***************** Functions ********************** //

    @Override
    public Color getIntensity(Point p) {
        double distanceSquared = p.distanceSquared(position);
        double distance = Math.sqrt(distanceSquared);
        double attenuation = 1/(kC + kL*distance + kQ*distanceSquared);
        return this.getIntensity().scale(attenuation);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(this.position).normalize();
    }


    public double getDistance(Point point) {
        return this.position.distance(point);
    }
}