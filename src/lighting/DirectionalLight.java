package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * class DirectionalLight is a class that represents the directional light in the scene
 * it has a direction
 */
public class DirectionalLight extends Light implements LightSource{
    private Vector direction;

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
     * constructor for DirectionalLight
     * @param p the position of the light
     */
    @Override
    public Color getIntensity(Point p) {
        return this.getIntensity();
    }

    /**
     * constructor for DirectionalLight
     * @param p the position of the light
     */
    @Override
    public Vector getL(Point p) {
        return this.direction;
    }


}