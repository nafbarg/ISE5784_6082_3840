package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * AmbientLight class represents the ambient light in a scene.
 * The ambient light is a constant light that is present everywhere in the scene.
 */
public class AmbientLight extends Light{

    /**
     * constructor for AmbientLight
     * @param Ia the color of the light
     * @param Ka the factor of the light
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * constructor for AmbientLight
     * @param Ia the color of the light
     * @param Ka the factor of the light
     */
    public AmbientLight(Color Ia, Double Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * A constant for an AmbientLight that has no effect (black color).
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0.0 );

//    /**
//     * Constructs an AmbientLight with a specific color and a scale factor.
//     *
//     * @param iA the color of the ambient light
//     * @param kA the scale factor of the ambient light
//     */
//    public AmbientLight(Color iA, double kA) {
//        this.intensity = iA.scale(kA);
//    }
//
//    /**
//     * Constructs an AmbientLight with a specific color and a scale factor.
//     *
//     * @param iA the color of the ambient light
//     * @param kA the scale factor of the ambient light
//     */
//    public AmbientLight(Color iA, Double3 kA) {
//        this.intensity = iA.scale(kA);
//    }

//    /**
//     * Constructs an AmbientLight that has no effect (black color).
//     */
//    public AmbientLight() {
//        this.intensity = Color.BLACK;
//    }
}