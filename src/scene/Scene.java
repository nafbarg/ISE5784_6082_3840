package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * Scene class represents a 3D scene with geometries and ambient light.
 * The scene is defined by a name, a collection of geometries, an ambient light, and a background color.
 */
public class Scene {
    private final String sceneName;
    /**
     * The geometries of the scene.
     */
    public Geometries geometries = new Geometries();
    /**
     * The ambient light of the scene.
     */
    public AmbientLight ambientLight = AmbientLight.NONE;
    /**
     * The background color of the scene.
     */
    public Color background = Color.BLACK;

    /**
     * Constructs a Scene with a specific name.
     *
     * @param sceneName the name of the scene
     */
    public Scene(String sceneName) {
        this.sceneName = sceneName;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight the ambient light to set
     * @return the scene with the set ambient light
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the background color of the scene.
     *
     * @param backgroundColor the background color to set
     * @return the scene with the set background color
     */
    public Scene setBackground(Color backgroundColor) {
        this.background = backgroundColor;
        return this;
    }

    /**
     * Sets the geometries of the scene.
     *
     * @param geometries the geometries to set
     * @return the scene with the set geometries
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
