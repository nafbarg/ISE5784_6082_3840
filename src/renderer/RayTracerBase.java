package renderer;

import scene.Scene;

public class RayTracerBase {
    private final Scene scene;

    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    public void renderImage(ImageWriter imagerWriter) {

    }
}
