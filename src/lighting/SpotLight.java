package lighting;

import primitives.*;
import static primitives.Util.*;

/**
 * class SpotLight is a class that represents the spotlight in the scene
 * it has a direction and a position and extends PointLight.
 */
public class SpotLight extends PointLight {
    private final Vector direction;

    /**
     * constructor for spotlight
     *
     * @param intensity color of light
     * @param p         position of light
     * @param d         direction of light
     */
    public SpotLight(Color intensity, Point p, Vector d) {
        super(intensity, p);
        direction = d.normalize();
    }

    // ***************** Functions ********************** //

    /**
     * setter for kC in phong model
     *
     * @param kC constant attenuation factor
     * @return the light itself
     */
    public SpotLight setkC(double kC) {
        super.setkC(kC);
        return this;
    }

    /**
     * setter for kL in phong model
     *
     * @param kL light's attenutation factor
     * @return the light itself
     */
    public SpotLight setKl(double kL) {
         super.setKl(kL);
         return this;
    }

    /**
     * setter for kQ in phong model
     *
     * @param kQ qudratic attenutation factor
     * @return the light itself
     */
    public SpotLight setKq(double kQ) {
        super.setKq(kQ);
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double projection = alignZero(direction.dotProduct(getL(p)));
        // if the point is outside the cone of light
        return (projection <= 0) ? Color.BLACK : super.getIntensity(p).scale(projection);
    }
}