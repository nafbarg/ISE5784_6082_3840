package geometries;

import primitives.*;

import java.util.List;


/**
 * Geometry is an interface that represents a geometric object in 3D space.
 */
public abstract class Geometry extends Intersectable {
    /**
     * The color of the geometry.
     */
    protected Color emission = Color.BLACK;

    /**
     * material of the geometry body
     */
    private Material material = new Material();



    /**
     * Returns the normal vector of the geometry at the specified point.
     *
     * @param point the point on the geometry
     * @return the normal vector
     */
    public abstract Vector getNormal(Point point);

    /**
     * Returns the emission color of the geometry.
     *
     * @return the emission color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission the emission color to set
     * @return the geometry with the set emission color
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * get the material of the geometry body
     *
     * @return material of the geometry body
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * set the material of the geometry body
     *
     * @param m material of the geometry body
     * @return the geometry body itself
     */
    public Geometry setMaterial(Material m) {
        material = m;
        return this;
    }
}
