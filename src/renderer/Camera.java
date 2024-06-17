package renderer;

import primitives.*;

import java.util.MissingResourceException;

/**
 * Camera class represents a camera in 3D Cartesian coordinate system
 * by a point and 3 vectors to the camera
 */
public class Camera implements Cloneable{
    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double height = 0.0;
    private double width = 0.0;
    private double distance = 0.0;

    private Camera() {
    }

    public static class Builder{
        final private Camera camera = new Camera();

        /**
         * Sets the camera's position.
         *
         * @param location the position of the camera
         * @return the Builder
         */
        public Builder setLocation(Point location) {
            //check if the point is null
            if (location == null) {
                throw new IllegalArgumentException("The point cannot be null");
            }
            camera.p0 = location;
            return this;
        }

        /**
         * Sets the camera's direction vectors.
         *
         * @param vTo the to vector of the camera
         * @param vUp the up vector of the camera
         * @return the Builder
         */
        public Builder setDirection(Vector vTo, Vector vUp){
            if (vTo == null || vUp == null) {
                throw new IllegalArgumentException("The vectors cannot be null");
            }
            if (vTo.dotProduct(vUp) != 0) {
                throw new IllegalArgumentException("The vectors must be orthogonal");
            }
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            return this;
        }
        
        /**
         * Sets the camera's view plane size.
         *
         * @param width the width of the camera
         * @param height the height of the camera
         * @return the Builder
         */
        public Builder setVpSize(double width, double height){
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("The width and height must be positive");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Sets the camera's distance from the view plane.
         *
         * @param distance the distance of the camera
         * @return the Builder
         */
        public Builder setVpDistance(double distance){
            if (distance <= 0) {
                throw new IllegalArgumentException("The distance must be positive");
            }
            camera.distance = distance;
            return this;
        }

        /**
         * Builds the Camera object.
         *
         * @return the Camera object
         */
        public Camera build() {
            final String MISSING_RESOURCE_ERROR = "Missing rendering resource";
            final String CAMERA_CLASS = "Camera";
            if (camera.p0 == null) {
                throw new MissingResourceException(MISSING_RESOURCE_ERROR, CAMERA_CLASS, "location");
            }
            if (camera.vTo == null || camera.vUp == null || camera.vRight == null) {
                throw new MissingResourceException(MISSING_RESOURCE_ERROR, CAMERA_CLASS, "direction vectors");
            }
            if (camera.width == 0 || camera.height == 0) {
                throw new MissingResourceException(MISSING_RESOURCE_ERROR, CAMERA_CLASS,  "view plane size");
            }
            if (camera.distance == 0) {
                throw new MissingResourceException(MISSING_RESOURCE_ERROR, CAMERA_CLASS, "distance");
            }

            // Calculate the missing data
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            // Return a clone of the camera
            return (Camera) camera.clone();
        }
    }


    /**
     * Returns a new Builder for the Camera class.
     *
     * @return a new Builder
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Constructs a ray from the camera through a pixel.
     *
     * @param nX the number of pixels in the x direction
     * @param nY the number of pixels in the y direction
     * @param j the x coordinate of the pixel
     * @param i the y coordinate of the pixel
     * @return the constructed ray
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        return null;
    }

    /**
     * Gets the camera's position.
     *
     * @return the position of the camera
     */
    public Point getLo() {
        return p0;
    }

    /**
     * Gets the camera's up vector.
     *
     * @return the up vector of the camera
     */
    public Vector getVUp() {
        return vUp;
    }

    /**
     * Gets the camera's to vector.
     *
     * @return the to vector of the camera
     */
    public Vector getVTo() {
        return vTo;
    }

    /**
     * Gets the camera's right vector.
     *
     * @return the right vector of the camera
     */
    public Vector getVRight() {
        return vRight;
    }

    /**
     * Gets the camera's height.
     *
     * @return the height of the camera
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets the camera's width.
     *
     * @return the width of the camera
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the camera's distance.
     *
     * @return the distance of the camera
     */
    public double getDistance() {
        return distance;
    }

    @Override
    public Camera clone() {
        try {
            return (Camera) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
