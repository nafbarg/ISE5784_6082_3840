package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * AmbientLight class represents the ambient light in a scene.
 * The ambient light is a constant light that is present everywhere in the scene.
 */
public class AmbientLight {
    private final Color intensity;

    /**
     * A constant for an AmbientLight that has no effect (black color).
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0.0 );

    /**
     * Constructs an AmbientLight with a specific color and a scale factor.
     *
     * @param iA the color of the ambient light
     * @param kA the scale factor of the ambient light
     */
    public AmbientLight(Color iA, double kA) {
        this.intensity = iA.scale(kA);
    }

    /**
     * Constructs an AmbientLight with a specific color and a scale factor.
     *
     * @param iA the color of the ambient light
     * @param kA the scale factor of the ambient light
     */
    public AmbientLight(Color iA, Double3 kA) {
        this.intensity = iA.scale(kA);
    }

    /**
     * Constructs an AmbientLight that has no effect (black color).
     */
    public AmbientLight() {
        this.intensity = Color.BLACK;
    }

    /**
     * Returns the intensity of the ambient light.
     *
     * @return the intensity of the ambient light
     */
    public Color getIntensity() {
        return intensity;
    }
}