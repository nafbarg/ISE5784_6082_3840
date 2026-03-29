package renderer;

import primitives.*;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.*;
import java.util.concurrent.ThreadLocalRandom;

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
    private int numSamples = 1;
    private int threadsCount = 0;
    private PixelManager pixelManager;
    private boolean adaptiveSamplingEnabled = false;
    // Precomputed center point of the view plane (p0 + vTo * distance). Compute once in build().
    private Point pC;
    // Threshold for color similarity; default value kept to previous behavior (0.1).
    private double colorSimilarityThreshold = 0.1;

    private Camera() {
    }

    @Override
    protected Camera clone() throws CloneNotSupportedException {
        return (Camera) super.clone();
    }

    /**
     * Renders the image.
     */
    public Camera renderImage(){
        final int nX = imagerWriter.getNx();
        final int nY = imagerWriter.getNy();
        pixelManager = new PixelManager(nY, nX, 100);
        if (threadsCount == 0) {
            for (int i = 0; i < nY; i++)
                for (int j = 0; j < nX; j++)
                    castRay(nX, nY, j, i);
        }
        else
            IntStream.range(0, threadsCount).parallel()
                    .forEach(i -> {
                        PixelManager.Pixel pixel;
                        while ((pixel = pixelManager.nextPixel()) != null) {
                            int row = pixel.row();
                            int col = pixel.col();
                            castRay(nX, nY, col, row);
                        }
                    });


        return this;
    }

    /**
     * Prints a grid on the image.
     *
     * @param interval the interval between the grid lines
     * @param color the color of the grid lines
     * @return the Camera object
     */
    public Camera printGrid(int interval, Color color) {
        int nX = imagerWriter.getNx();
        int nY = imagerWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imagerWriter.writePixel(j, i, color);
                }
            }
        }
        return  this;
    }

    /**
     * Writes the image to a file.
     *
     * @return the Camera object
     */
    public Camera writeToImage() {
        imagerWriter.writeToImage();
        return this;
    }

    /**
     * Casts a ray through the specified pixel, computes the color of the ray, and writes the color to the pixel.
     *
     * @param nX number of pixels in the x direction
     * @param nY number of pixels in the y direction
     * @param j  x coordinate of the pixel
     * @param i  y coordinate of the pixel
     */
    private void castRay(int nX, int nY, int j, int i) {
        if (numSamples == 1) {
            // Anti-aliasing is disabled
            Ray ray = constructRay(nX, nY, j, i);
            Color color = rayTracer.traceRay(ray);
            imagerWriter.writePixel(j, i, color);

        } else {
            // Anti-aliasing is enabled
            if (adaptiveSamplingEnabled) {
                Color color = adaptiveSuperSampling(nX, nY, j, i, numSamples);
                imagerWriter.writePixel(j, i, color);
            } else {
                List<Ray> rays = constructRays(nX, nY, j, i, numSamples);
                Color color = rayTracer.traceRays(rays);
                imagerWriter.writePixel(j, i, color);
            }

        }
        pixelManager.pixelDone();
    }

    /**
     * Wrapper function for the adaptive super-sampling.
     * Calculates the initial 4 corners and calls the recursive helper.
     */
    private Color adaptiveSuperSampling(int nX, int nY, int j, int i, int maxSamples) {
        List<Ray> cornerRays = constructCornerRays(nX, nY, j, i);

        // Calculate the initial 4 corners once
        Color cTopLeft = rayTracer.traceRay(cornerRays.get(0));
        Color cTopRight = rayTracer.traceRay(cornerRays.get(1));
        Color cBottomLeft = rayTracer.traceRay(cornerRays.get(2));
        Color cBottomRight = rayTracer.traceRay(cornerRays.get(3));

        return adaptiveSuperSamplingHelper(nX, nY, j, i, maxSamples, cTopLeft, cTopRight, cBottomLeft, cBottomRight);
    }

    /**
     * Recursive helper function that avoids recalculating known corners.
     */
    private Color adaptiveSuperSamplingHelper(int nX, int nY, int j, int i, int maxSamples,
                                              Color cTopLeft, Color cTopRight, Color cBottomLeft, Color cBottomRight) {

        // 1. Calculate the center point color (Solves the "Thin Line" problem)
        Ray centerRay = constructRay(nX, nY, j, i);
        Color cCenter = rayTracer.traceRay(centerRay);

        // 2. Check if colors are similar across all 5 points (4 corners + center)
        if (maxSamples <= 8 || areColorsSimilar(List.of(cTopLeft, cTopRight, cBottomLeft, cBottomRight, cCenter))) {
            return averageColors(List.of(cTopLeft, cTopRight, cBottomLeft, cBottomRight, cCenter));
        }

        // 3. Clean Code: Calculate next resolution using integers
        int nextNX = nX * 2;
        int nextNY = nY * 2;

        // 4. Calculate the midpoints of the 4 edges of the current pixel/quadrant
        // These are required to form the new sub-quadrants without recalculating corners
        double rY = height / nY;
        double rX = width / nX;

        Color cTopMiddle    = rayTracer.traceRay(constructRayFromOffset(nX, nY, j, i, 0, -0.5 * rY));
        Color cBottomMiddle = rayTracer.traceRay(constructRayFromOffset(nX, nY, j, i, 0, 0.5 * rY));
        Color cLeftMiddle   = rayTracer.traceRay(constructRayFromOffset(nX, nY, j, i, -0.5 * rX, 0));
        Color cRightMiddle  = rayTracer.traceRay(constructRayFromOffset(nX, nY, j, i, 0.5 * rX, 0));

        // 5. Recursive calls - Passing the known colors down the tree!
        Color topLeftColor = adaptiveSuperSamplingHelper(nextNX, nextNY, j * 2, i * 2, maxSamples / 4,
                cTopLeft, cTopMiddle, cLeftMiddle, cCenter);

        Color topRightColor = adaptiveSuperSamplingHelper(nextNX, nextNY, j * 2 + 1, i * 2, maxSamples / 4,
                cTopMiddle, cTopRight, cCenter, cRightMiddle);

        Color bottomLeftColor = adaptiveSuperSamplingHelper(nextNX, nextNY, j * 2, i * 2 + 1, maxSamples / 4,
                cLeftMiddle, cCenter, cBottomLeft, cBottomMiddle);

        Color bottomRightColor = adaptiveSuperSamplingHelper(nextNX, nextNY, j * 2 + 1, i * 2 + 1, maxSamples / 4,
                cCenter, cRightMiddle, cBottomMiddle, cBottomRight);

        // Return the average of the 4 sub-quadrants
        return averageColors(List.of(topLeftColor, topRightColor, bottomLeftColor, bottomRightColor));
    }

    /**
     * Checks if all colors in the list are similar (within a small threshold).
     *
     * @param colors list of colors to check
     * @return true if colors are similar, false otherwise
     */
    private boolean areColorsSimilar(List<Color> colors) {
        // Defensive checks
        if (colors == null || colors.isEmpty()) {
            return true; // nothing to compare, treat as similar
        }

        // Use class-level threshold (configurable via Builder)
        final double THRESHOLD = colorSimilarityThreshold;
        Color baseColor = colors.get(0); // use get(0) which works for any List implementation

        // Use an indexed loop to avoid stream overhead and reduce allocations
        for (int idx = 1, size = colors.size(); idx < size; idx++) {
            Color color = colors.get(idx);
            if (!color.isSimilar(baseColor, THRESHOLD)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Constructs four rays for the corners of a pixel.
     *
     * @param nX the number of pixels in the x direction
     * @param nY the number of pixels in the y direction
     * @param j the x coordinate of the pixel
     * @param i the y coordinate of the pixel
     * @return a list of four corner rays
     */
    private List<Ray> constructCornerRays(int nX, int nY, int j, int i) {
        List<Ray> cornerRays = new ArrayList<>();
        double rY = height / nY;
        double rX = width / nX;

        Point center = calculatePixelCenter(nX, nY, j, i);

        // Calculating each corner of the pixel
        cornerRays.add(new Ray(p0, center.add(vRight.scale(-0.5 * rX)).add(vUp.scale(-0.5 * rY)).subtract(p0))); // top-left
        cornerRays.add(new Ray(p0, center.add(vRight.scale(0.5 * rX)).add(vUp.scale(-0.5 * rY)).subtract(p0)));  // top-right
        cornerRays.add(new Ray(p0, center.add(vRight.scale(-0.5 * rX)).add(vUp.scale(0.5 * rY)).subtract(p0)));  // bottom-left
        cornerRays.add(new Ray(p0, center.add(vRight.scale(0.5 * rX)).add(vUp.scale(0.5 * rY)).subtract(p0)));   // bottom-right

        return cornerRays;
    }

    /**
     * Constructs a ray from the camera through an offset within the specified pixel.
     *
     * @param nX the number of pixels in the x direction
     * @param nY the number of pixels in the y direction
     * @param j  the x coordinate of the pixel
     * @param i  the y coordinate of the pixel
     * @param offsetX the x offset within the pixel
     * @param offsetY the y offset within the pixel
     * @return the constructed ray
     */
    private Ray constructRayFromOffset(int nX, int nY, int j, int i, double offsetX, double offsetY) {
        Point pIJ = calculatePixelCenter(nX, nY, j, i);

        // Add offsets only if they are not zero to avoid scaling a vector by 0
        if (!isZero(offsetX)) {
            pIJ = pIJ.add(vRight.scale(offsetX));
        }
        if (!isZero(offsetY)) {
            pIJ = pIJ.add(vUp.scale(offsetY));
        }

        return new Ray(p0, pIJ.subtract(p0));
    }

    /**
     * Averages a list of colors.
     *
     * @param colors list of colors
     * @return the average color
     */
    private Color averageColors(List<Color> colors) {
        Color sum = Color.BLACK;
        for (Color color : colors) {
            sum = sum.add(color);
        }
        return sum.scale(1.0 / colors.size());
    }

    /**
     * Constructs a list of rays for a pixel.
     *
     * @param nX the number of pixels in the x direction
     * @param nY the number of pixels in the y direction
     * @param j the x coordinate of the pixel
     * @param i the y coordinate of the pixel
     * @param numSamples the number of samples
     * @return the list of rays
     */
    private List<Ray> constructRays(int nX, int nY, int j, int i, int numSamples) {
        // Pre-allocate list capacity to avoid resizing
        List<Ray> rays = new ArrayList<>(Math.max(1, numSamples));

        // Compute central/sample base once
        Point center = calculatePixelCenter(nX, nY, j, i);
        rays.add(new Ray(p0, center.subtract(p0))); // add the central ray


        /*
        if (numSamples <= 1) {
            return rays;
        }*/

        double rY = height / nY;
        double rX = width / nX;

        ThreadLocalRandom rand = ThreadLocalRandom.current();

        // Generate jittered samples inside the pixel
        for (int s = 1; s < numSamples; s++) {
            double offsetX = (rand.nextDouble() - 0.5) * rX;
            double offsetY = (rand.nextDouble() - 0.5) * rY;

            Point sampleP = center;
            if (!isZero(offsetX)) sampleP = sampleP.add(vRight.scale(offsetX));
            if (!isZero(offsetY)) sampleP = sampleP.add(vUp.scale(offsetY));

            rays.add(new Ray(p0, sampleP.subtract(p0)));
        }

        return rays;
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
        // calculate pixel center
        Point pIJ = calculatePixelCenter(nX, nY, j, i);

        // return ray from camera to viewPlane coordinate (i, j)
        return new Ray(p0, pIJ.subtract(p0));
    }

    /**
     * Calculates the center of a pixel in the view grid.
     *
     * @param nX the number of pixels in the x direction
     * @param nY the number of pixels in the y direction
     * @param j  the x coordinate of the pixel
     * @param i  the y coordinate of the pixel
     * @return the center of the pixel
     */
    private Point calculatePixelCenter(int nX, int nY, int j, int i) {
        // pixel measurements
        double rY = height / nY;
        double rX = width / nX;

        // place pixel[i,j] in view grid center
        // Use precomputed view-plane center (pC) to avoid recomputing p0 + vTo * distance for every pixel
        Point pIJ = pC;

        // calculate pixel[i,j] center
        double yI = -(i - ((nY - 1) / 2d)) * rY;
        double xJ = (j - ((nX - 1) / 2d)) * rX;

        // shift to a center of pixel[i, j]
        if (!isZero(xJ))
            pIJ = pIJ.add(vRight.scale(xJ));
        if (!isZero(yI))
            pIJ = pIJ.add(vUp.scale(yI));

        return pIJ;
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
         * Sets the number of samples for anti-aliasing.
         *
         * @param numSamples the number of samples
         * @return the Builder
         */
        public Builder setNumSamples(int numSamples) {
            if (numSamples < 1) {
                throw new IllegalArgumentException("Number of samples must be at least 1");
            }
            camera.numSamples = numSamples;
            return this;
        }

        /**
         * Sets the number of threads for rendering.
         *
         * @param threadsCount the number of threads
         * @return the Builder
         */
        public Builder setThreadsCount(int threadsCount) {
            if (threadsCount < 0) {
                throw new IllegalArgumentException("Number of threads must be at least 0");
            }
            camera.threadsCount = threadsCount;
            return this;
        }

        public Builder setAdaptiveSamplingEnabled(boolean adaptiveSamplingEnabled) {
            camera.adaptiveSamplingEnabled = adaptiveSamplingEnabled;
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
         * Sets the camera's ray tracer.
         *
         * @param rayTracer the ray tracer of the camera
         * @return the Builder
         */
        public Builder setRayTracer(SimpleRayTracer rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Sets the camera's image writer.
         *
         * @param imageWriter the image writer of the camera
         * @return the Builder
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imagerWriter = imageWriter;
            return this;
        }

        /**
         * Sets the color similarity threshold used by adaptive sampling.
         * A non-negative value is expected (typically between 0 and 1).
         */
        public Builder setColorSimilarityThreshold(double threshold) {
            if (threshold < 0) {
                throw new IllegalArgumentException("Threshold must be non-negative");
            }
            camera.colorSimilarityThreshold = threshold;
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
            if (camera.rayTracer == null) {
                throw new MissingResourceException(MISSING_RESOURCE_ERROR, CAMERA_CLASS, "rayTracer");
            }
            if (camera.imagerWriter == null) {
                throw new MissingResourceException(MISSING_RESOURCE_ERROR, CAMERA_CLASS, "imageWriter");
            }

            // Calculate the missing data
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            // Precompute the view-plane center (p0 + vTo * distance) once to avoid repeated work
            camera.pC = camera.p0.add(camera.vTo.scale(camera.distance));

            // Return a clone of the camera
            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException ignore) {
                return null;
            }

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
