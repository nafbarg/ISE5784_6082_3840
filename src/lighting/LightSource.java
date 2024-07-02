package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * interface LightSource is an interface that represents the light source in the scene
 * it has a color and a direction
 */
public interface LightSource {
    public Color getIntensity(Point p);
    public Vector getL(Point p);
}
