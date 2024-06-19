package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

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
    private RayTracerBase rayTracer;
    private ImageWriter imagerWriter;

    private Camera() {
    }

    public Camera renderImage() {
        if (rayTracer == null) {
            throw new MissingResourceException("Missing rendering resource", "Camera", "rayTracer");
        }
        if (imagerWriter == null) {
            throw new MissingResourceException("Missing rendering resource", "Camera", "imageWriter");
        }

        // todo implement
        return this;
    }

    public Camera printGrid(int intervall, Color color) {
        int nX = imagerWriter.getNx();
        int nY = imagerWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % intervall == 0 || j % intervall == 0) {
                    imagerWriter.writePixel(j, i, color);
                }
            }
        }
        return  this;
    }

    public Camera writeToImage() {
        imagerWriter.writeToImage();
        return this;
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
            if (camera.vTo == null || camera.vUp == null) {
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
            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException ignore) {
                return null;
            }

        }

        public Builder setRayTracer(SimpleRayTracer rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

         public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imagerWriter = imageWriter;
            return this;
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
    public Ray constructRay(int nX, int nY, int j, int i) {
        // pixel measurements
        double rY = height / nY;
        double rX = width / nX;

        // place pixel[i,j] in view grid center
        Point pIJ = p0.add(vTo.scale(distance));

        // calculate pixel[i,j] center
        double yI = -(i - ((nY - 1) / 2d)) * rY;
        double xJ = (j - ((nX - 1) / 2d)) * rX;

        // shift to center of pixel[i,j]
        if (!isZero(xJ))
            pIJ = pIJ.add(vRight.scale(xJ));
        if (!isZero(yI))
            pIJ = pIJ.add(vUp.scale(yI));

        // return ray from camera to viewPlane coordinate (i, j)
        return new Ray(p0, pIJ.subtract(p0));
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


}
