package renderer;


import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;

import primitives.Color;

import org.junit.jupiter.api.Test;

/**
 * ImageWriterTests class is responsible for testing the ImageWriter class.
 * It checks the functionality of writing pixels and creating an image.
 */
public class ImageWriterTests {

    /**
     * Test method for creating a simple grid image.
     * The image is yellow with a red interval grid.
     */
    @Test
    public void simpleGridTest() {
        ImageWriter imageWriter = new ImageWriter("imageWriterTest", 800, 600);

        int nX = imageWriter.getNx();   //800
        int nY = imageWriter.getNy();   //600

        int interval = 50;

        Color colorYellow = new Color(YELLOW); // yellow
        Color colorRed = new Color(RED); // red

        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                if (i % interval == 0 || j % interval == 0)// grid lines
                    imageWriter.writePixel(j, i, colorRed);
                else// the rest of the image
                    imageWriter.writePixel(j, i, colorYellow);
            }
        }

        imageWriter.writeToImage();
    }
}
