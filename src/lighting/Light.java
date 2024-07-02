package lighting;

import primitives.Color;

/**
 * class Light is an abstract class that represents the light in the scene
 * it has an intensity
 */
abstract class Light {
    private Color intensity;

    /**
     * constructor for Light
     * @param intensity the color of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * getter for intensity
     * @return the color of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}