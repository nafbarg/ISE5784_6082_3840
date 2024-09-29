package test;

import static java.awt.Color.*;

import geometries.*;
import org.junit.jupiter.api.Test;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;


/**
 * Test class for rendering a scene with multiple geometries such as spheres,
 * triangles, tube, and a cube, with different levels of transparency and reflection.
 * Also adds multiple spotlights to illuminate the scene from different angles.
 *
 * @author Naftali
 */
public class MyImageTest {

    /** The scene where all geometries and lighting are placed */
    private final Scene scene = new Scene("Test scene");

    /** Camera builder of the tests */
    private final Camera.Builder camera     = Camera.getBuilder()
            .setDirection(new Vector(0,0,-1), Vector.Y)
            .setLocation(new Point(0, 0, 1000)).setVpDistance(700)
            .setVpSize(200, 200)
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * Test case for rendering a scene with spheres, a pyramid (made of triangles), a tube,
     * and a cube.
     */
    @Test
    public void myImage() {

        scene.geometries.add(
                new Plane(new Point(80, 120, 0), new Point(-80, 120, 0), new Point(-80, 120, 10))
                        .setMaterial(new Material().setShininess(60).setKt(1))
                        .setEmission(new Color(20, 20, 20)),

                // Triangles
                new Triangle(new Point(-180, -180, -115), new Point(200, -200, -135),
                        new Point(160, 160, -150))
                        .setMaterial(new Material().setKs(0.8).setShininess(60)),

                new Triangle(new Point(-180, -180, -115), new Point(-180, 180, -140), new Point(160, 160, -150))
                        .setMaterial(new Material().setKs(0.8).setShininess(60)),

                // Spheres with varying transparency and reflection
                new Sphere(20d, new Point(-40, -40, -100))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.15).setKs(0.3).setShininess(90).setKt(0.55)),

                new Sphere(20d, new Point(50, 50, -11))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.15).setKs(0.15).setShininess(90).setKt(0.5)),

                new Sphere(30d, new Point(0, 0, -150))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.4).setShininess(30).setKt(0.4)),

                new Sphere(20d, new Point(-90, -90, -100))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.25).setKs(0.15).setShininess(90).setKr(0.6)),

                new Sphere(15d, new Point(-75, 25, -75))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.1).setKs(0.4).setShininess(30).setKr(0.3)));


        // Add a pyramid
        Point apex = new Point(35.0,-51.6,-11.1);
        Point p1 = new Point(-60, -106.6, -93.6);
        Point p2 = new Point(90.0,-106.7,-103.6);
        Point p3 = new Point(52.5,5.8,-111.1);

        Triangle pyramidSide1 = (Triangle) new Triangle(p1, p2, apex)
                .setMaterial(new Material().setKd(0.1).setKs(0.4).setShininess(30).setKr(0.15));
        Triangle pyramidSide2 = (Triangle) new Triangle(p2, p3 , apex)
                .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30).setKr(0.7));
        Triangle pyramidSide3 = (Triangle) new Triangle(p1, p3, apex)
                .setMaterial(new Material().setKd(0.0).setKs(0.0).setShininess(30).setKr(1)).setEmission(new Color(20, 30, 40));

        scene.geometries.add(pyramidSide1, pyramidSide2, pyramidSide3);


        // Add a Tube
        Tube tube = new Tube(5, new Ray(new Point(110, 50, -130), new Vector(0, 1, 0)));
        tube.setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(50).setKt(0.3));
        tube.setEmission(new Color(140, 20, 20));
        scene.geometries.add(tube);



        // Add a cube
        p1 = new Point(-67.67766952966369,-5.177669529663689,-44.82233047033631);
        p2 = new Point(-32.32233047033632,19.822330470336308,-69.82233047033631);
        p3 = new Point(-57.32233047033631,62.5,-62.5);
        Point p4 = new Point(-92.67766952966369,37.5,-37.5);
        Point p5 = new Point(-92.67766952966369,-12.5,-87.5);
        Point p6 = new Point(-57.32233047033631,12.499999999999996,-112.5);
        Point p7 = new Point(-82.32233047033631,55.17766952966369,-105.17766952966369);
        Point p8 = new Point(-117.67766952966369,30.177669529663692,-80.17766952966369);

        Polygon front = new Polygon(p1, p2, p3, p4);
        Polygon back = new Polygon(p5, p6, p7, p8);
        Polygon top = new Polygon(p4, p3, p7, p8);
        Polygon bottom = new Polygon(p1, p2, p6, p5);
        Polygon left = new Polygon(p1, p4, p8, p5);
        Polygon right = new Polygon(p2, p3, p7, p6);


        Material cubeMaterial = new Material().setKd(0.1).setKs(0.1).setShininess(60).setKt(0.5);
        Color cubeColor = new Color(0, 30, 10);

        front.setMaterial(cubeMaterial).setEmission(cubeColor);
        back.setMaterial(cubeMaterial).setEmission(cubeColor);
        top.setMaterial(cubeMaterial).setEmission(cubeColor);
        bottom.setMaterial(cubeMaterial).setEmission(cubeColor);
        left.setMaterial(cubeMaterial).setEmission(cubeColor);
        right.setMaterial(cubeMaterial).setEmission(cubeColor);

        scene.geometries.add(front, back, top, bottom, left, right);

        // Adding lights
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //red
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new SpotLight(new Color(400, 400, 700), new Point(-100, -100, 200), new Vector(1, 1, -4)) //purple
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new SpotLight(new Color(400, 700, 400), new Point(150, -100, 200), new Vector(-1, 1, -4)) //green
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new SpotLight(new Color(700, 400, 700), new Point(-70, 130, 200), new Vector(1, -1, -4)) //pink
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new SpotLight(new Color(700, 700, 400), new Point(100, 100, 100), new Vector(-1, -1, -4)) //yellow
                        .setKl(4E-3).setKq(2E-5));

        // Render the scene
        camera.setImageWriter(new ImageWriter("myImage", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }
}


