package test;

import static java.awt.Color.*;

import geometries.*;
import org.junit.jupiter.api.Test;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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


    private final Camera.Builder camera     = Camera.getBuilder()
            .setDirection(new Vector(0,0,-1), Vector.Y)
            .setLocation(new Point(0, 0, 1000)).setVpDistance(700)
            .setVpSize(200, 200)
            .setRayTracer(new SimpleRayTracer(scene))
            .setNumSamples(1);



    /** Camera builder of the tests */
    private final Camera.Builder camera1     = Camera.getBuilder()
            .setDirection(new Vector(0, 1, 0), new Vector(0, 0, 1))
            .setNumSamples(80)
            .setLocation(new Point(-40, -200, -20)).setVpDistance(100)
            .setVpSize(200, 200)
            .setThreadsCount(3)
            .setAdaptiveSamplingEnabled(true)
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
        Tube tube = new Tube(10, new Ray(new Point(110, 50, -130), new Vector(0, 1, 0)));
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
        camera.setImageWriter(new ImageWriter("myImage1", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Test case for rendering a scene with spheres, a pyramid (made of triangles), a tube,
     * and a cube.
     */
    @Test
    public void acvarium() {

        scene.geometries.add(
                //add the floor and the walls

                new Polygon(new Point(-200, -200, -100), new Point(200, -200, -100), new Point(200, 200, -100), new Point(-200, 200, -100))
                        .setEmission(new Color(20, 20, 50))
                        .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(30).setKt(0.0)),
                new Polygon(new Point(-200, 200, 400), new Point(200, 200, 400), new Point(200, 200, -100), new Point(-200, 200, -100))
                        .setEmission(new Color(40, 41, 42))
                        .setMaterial(new Material().setKd(0.0).setKs(0.2).setShininess(30).setKr(0.0)),
                new Polygon(new Point(200, -200, -100), new Point(200, -200, 400), new Point(200, 200, 400), new Point(200, 200, -100))
                        .setEmission(new Color(40, 41, 42))
                        .setMaterial(new Material().setKd(0.0).setKs(0.2).setShininess(30).setKr(0.0)),
                new Polygon(new Point(-200, -200, -100), new Point(-200, -200, 400), new Point(-200, 200, 400), new Point(-200, 200, -100))
                        .setEmission(new Color(40, 41, 42))
                        .setMaterial(new Material().setKd(0.0).setKs(0.2).setShininess(30).setKr(0.0)),
         //add bubbles
        new Sphere(2.6, new Point(10.9, -30.2, -45.8)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.13).setKt(0.85)),
                new Sphere(3.6, new Point(-54.4, -51.1, -18.7)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.14).setKt(0.89)),
                new Sphere(5.0, new Point(-55.1, -70.6, 11.2)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.07).setKt(0.87)),
                new Sphere(4.1, new Point(-1.3, -17.7, -3.3)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.10).setKt(0.84)),
                new Sphere(4.4, new Point(-31.9, -42.5, -78.2)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.05).setKt(0.92)),
                new Sphere(2.5, new Point(-84.5, -21.3, -9.7)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.08).setKt(0.94)),
                new Sphere(3.5, new Point(-38.7, -11.9, 2.2)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.13).setKt(0.82)),
                new Sphere(2.5, new Point(-33.9, -37.1, -63.7)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.09).setKt(0.96)),
                new Sphere(4.7, new Point(-99.3, -19.7, -15.5)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.09).setKt(0.87)),
                new Sphere(3.0, new Point(-56.5, 19.6, -14.3)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.11).setKt(0.96)),
                new Sphere(4.7, new Point(-14.6, -30.1, -1.7)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.13).setKt(0.84)),
                new Sphere(2.3, new Point(-44.6, -95.0, -1.6)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.14).setKt(0.82)),
                new Sphere(3.0, new Point(-66.6, -30.6, 31.0)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.12).setKt(0.85)),
                new Sphere(3.4, new Point(-44.9, 33.0, -57.5)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.14).setKt(0.91)),
                new Sphere(3.1, new Point(-16.4, -27.5, -29.3)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.12).setKt(0.94)),
                new Sphere(4.0, new Point(-21.3, -55.1, 30.8)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.10).setKt(1.00)),
                new Sphere(2.3, new Point(-42.1, -29.3, -85.6)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.15).setKt(0.87)),
                new Sphere(4.3, new Point(-53.7, -8.4, 38.1)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.06).setKt(0.92)),
                new Sphere(4.4, new Point(-40.0, -78.8, 5.0)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.09).setKt(1.00)),
                new Sphere(3.2, new Point(-52.5, 4.5, -34.8)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.09).setKt(0.83)),
                new Sphere(2.7, new Point(-49.2, -66.2, -64.1)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.10).setKt(0.89)),
                new Sphere(2.2, new Point(-79.5, 11.6, -41.8)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.10).setKt(0.84)),
                new Sphere(4.3, new Point(-50.9, -2.6, -1.8)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.15).setKt(0.82)),
                new Sphere(3.4, new Point(-52.2, -0.9, -42.2)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.05).setKt(0.98)),
                new Sphere(3.6, new Point(-72.2, -56.0, -11.8)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.12).setKt(0.96)),
                new Sphere(3.3, new Point(7.0, -47.5, -47.5)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.05).setKt(0.96)),
                new Sphere(2.2, new Point(-62.0, -84.6, -32.4)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.10).setKt(0.95)),
                new Sphere(2.1, new Point(-31.9, -52.4, -77.7)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.11).setKt(0.84)),
                new Sphere(4.7, new Point(19.6, 29.0, -44.4)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.14).setKt(0.84)),
                new Sphere(3.9, new Point(-50.3, -17.9, -39.9)).setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.1).setKs(0.3).setShininess(50).setKr(0.13).setKt(0.82)))


        ;




        createFish(new Point(-50, 0, -70), scene, 15.0, new Color(0, 0, 255), 20);
        createFish(new Point(0, 30, -80), scene, 10.0, new Color(200, 0, 255));
        createFish(new Point(0, 30, -40), scene, 10.0, new Color(0,110,51), 30);
        createFish(new Point(-70, 30, -30), scene, 15.0, new Color(YELLOW), 15);
        createFish(new Point(-90, -30, -70), scene, 8.0, new Color(255, 100, 0), 15);
        createFish(new Point(40, -70, 0), scene, 8.0, new Color(255, 0, 0), 15);
        createFish(new Point(40, 70, 0), scene, 8.0, new Color(0, 255, 0), 15);
        createFish(new Point(-80, -70, 0), scene, 8.0, new Color(150,70,100), 45);

        //createBubbles(new Point(-25, -25, -20), 75, 30, 2, 5, scene);


        // Add a cube
        Point p1 = new Point(-150, -100, 60);
        Point p2 = new Point(100, -100, 60);
        Point p3 = new Point(100, 50, 60);
        Point p4 = new Point(-150, 50, 60);
        Point p5 = new Point(-150, -100, -100);
        Point p6 = new Point(100, -100, -100);
        Point p7 = new Point(100, 50, -100);
        Point p8 = new Point(-150, 50, -100);

        Polygon front = new Polygon(p1, p2, p3, p4);
        Polygon back = new Polygon(p5, p6, p7, p8);
        Polygon top = new Polygon(p4, p3, p7, p8);
        Polygon bottom = new Polygon(p1, p2, p6, p5);
        Polygon left = new Polygon(p1, p4, p8, p5);
        Polygon right = new Polygon(p2, p3, p7, p6);


        Material cubeMaterial = new Material().setKd(0.1).setKs(0.1).setShininess(60).setKt(0.7);
        Material cubeMaterialReflective =new  Material().setKd(0.1).setKs(0.1).setShininess(60).setKr(0.25);
        Color cubeColor = new Color(0,58,80);

        front.setMaterial(cubeMaterial).setEmission(cubeColor);
        back.setMaterial(cubeMaterial).setEmission(cubeColor);
        top.setMaterial(cubeMaterial).setEmission(cubeColor);
        bottom.setMaterial(cubeMaterial).setEmission(cubeColor);
        left.setMaterial(cubeMaterialReflective).setEmission(cubeColor);
        right.setMaterial(cubeMaterialReflective).setEmission(cubeColor);

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
        camera1.setImageWriter(new ImageWriter("acvarium", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }














    public static void createFish(Point center, Scene scene,double bodyRadius,Color color) {
        // Create the body (a Sphere)
        Sphere body = (Sphere) new Sphere(bodyRadius, center)
                .setEmission(color)
                .setMaterial(new Material().setKd(0.3).setKs(0.3).setShininess(20));

        // Add the body to the scene
        scene.geometries.add(body);

        // Create the tail (a Triangle)
        // The tail will have one vertex on the body and two vertices outside the body
        // The vertex on the body will be directly behind the body along the X-axis
        Point tailBase = center.add(new Vector(bodyRadius, 0, 0));

        // The other two vertices will be outside, forming a triangle
        Point tailTip1 = center.add(new Vector(bodyRadius * 2.5, bodyRadius * 0.5, -bodyRadius * 0.8));
        Point tailTip2 = center.add(new Vector(bodyRadius * 2.5, bodyRadius * 0.5, bodyRadius * 0.8));

        Triangle tail = (Triangle) new Triangle(tailBase, tailTip1, tailTip2)
                .setEmission(new Color(80, 0, 0)) // grayish color for the tail
                .setMaterial(new Material().setKd(0.3).setKs(0.3).setShininess(30));

        // Add the tail to the scene
        scene.geometries.add(tail);

        // Create the eyes (small spheres)
        double eyeRadius = bodyRadius / 5;

        // Eye positions, one above and one below the X-axis
        Point leftEyePosition = center.add(new Vector(-bodyRadius * 0.5, bodyRadius * 0.8, bodyRadius * 0.4));  // Above the X-axis
        Point rightEyePosition = center.add(new Vector(-bodyRadius * 0.5, -bodyRadius * 0.8, bodyRadius * 0.4)); // Below the X-axis

        Sphere leftEye = (Sphere) new Sphere(eyeRadius, leftEyePosition)
                .setEmission(new Color(0, 0, 0)) // black color for the eyes
                .setMaterial(new Material().setKd(0.1).setKs(0.5).setShininess(50));

        Sphere rightEye = (Sphere) new Sphere(eyeRadius, rightEyePosition)
                .setEmission(new Color(0, 0, 0)) // black color for the eyes
                .setMaterial(new Material().setKd(0.1).setKs(0.5).setShininess(50));

        // Add the eyes to the scene
        scene.geometries.add(leftEye, rightEye);
    }

    public static void createFish(Point center, Scene scene, double bodyRadius, Color color, double tailAngleDegrees) {


        Sphere body = (Sphere) new Sphere(bodyRadius, center)
                .setEmission(color)
                .setMaterial(new Material().setKd(0.3).setKs(0.3).setShininess(20));


        scene.geometries.add(body);

        // יצירת הזנב (משולש)
        // הזנב יתחיל מנקודה אחת על הגוף ושתי נקודות נוספות מחוץ לגוף בזווית הרצויה
        Point tailBase = center.add(new Vector(bodyRadius, 0, 0));

        // מיקום קצוות הזנב ביחס לזווית
        Point tailTip1 = rotatePointAroundAxisZ(
                center.add(new Vector(bodyRadius * 2.5, bodyRadius * 0.5, -bodyRadius * 0.8)),
                center,
                tailAngleDegrees
        );
        Point tailTip2 = rotatePointAroundAxisZ(
                center.add(new Vector(bodyRadius * 2.5, bodyRadius * 0.5, bodyRadius * 0.8)),
                center,
                tailAngleDegrees
        );

        Triangle tail = (Triangle) new Triangle(tailBase, tailTip1, tailTip2)
                .setEmission(new Color(80, 0, 0)) // צבע חום כהה לזנב
                .setMaterial(new Material().setKd(0.3).setKs(0.3).setShininess(30));


        scene.geometries.add(tail);

        // יצירת עיניים (כדורים קטנים)
        double eyeRadius = bodyRadius / 5;

        //
        Point leftEyePosition = center.add(new Vector(-bodyRadius * 0.5, bodyRadius * 0.8, bodyRadius * 0.4));  // מעל ציר X
        Point rightEyePosition = center.add(new Vector(-bodyRadius * 0.5, -bodyRadius * 0.8, bodyRadius * 0.4)); // מתחת לציר X

        Sphere leftEye = (Sphere) new Sphere(eyeRadius, leftEyePosition)
                .setEmission(new Color(0, 0, 0)) // צבע שחור לעיניים
                .setMaterial(new Material().setKd(0.1).setKs(0.5).setShininess(50));

        Sphere rightEye = (Sphere) new Sphere(eyeRadius, rightEyePosition)
                .setEmission(new Color(0, 0, 0)) // צבע שחור לעיניים
                .setMaterial(new Material().setKd(0.1).setKs(0.5).setShininess(50));


        scene.geometries.add(leftEye, rightEye);
    }


    // function to rotate a point around the Z-axis
    private static Point rotatePointAroundAxisZ(Point point, Point center, double angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);
        double cosTheta = Math.cos(angleRadians);
        double sinTheta = Math.sin(angleRadians);

        // Translate the point to the origin
        double x = point.getX() - center.getX();
        double y = point.getY() - center.getY();
        double z = point.getZ();

        // Rotate the point around the Z-axis
        double newX = x * cosTheta - y * sinTheta + center.getX();
        double newY = x * sinTheta + y * cosTheta + center.getY();

        return new Point(newX, newY, z);
    }


/*
    public static void createBubbles(Point center, double radius, int numBubbles, double minRadius, double maxRadius, Scene scene) {
        List<Sphere> bubbles = new ArrayList<>();
        Random random = new Random();


        for (int i = 0; i < numBubbles; i++) {
            // Random position within the specified area
            Point position = randomPointInSphere(center, radius);

            // Random size for the bubble between minRadius and maxRadius
            double bubbleRadius = minRadius + (maxRadius - minRadius) * random.nextDouble();

            // Random transparency (Kt) and reflection (Kr) to add realism
            double transparency = 0.8 + 0.2 * random.nextDouble();  // Range: 0.8 to 1.0
            double reflection = 0.05 + 0.1 * random.nextDouble();  // Range: 0.05 to 0.15

            // Create bubble with random transparency, reflection, and position
            Sphere bubble = (Sphere) new Sphere(bubbleRadius, position)
                    .setEmission(new Color(0, 0, 0))
                    .setMaterial(new Material()
                            .setKd(0.1)
                            .setKs(0.3)
                            .setShininess(50)
                            .setKr(reflection)
                            .setKt(transparency)
                    );

            bubbles.add(bubble);
        }
        printBubbles(bubbles, random);
        scene.geometries.add(bubbles.toArray(new Sphere[0]));


    }


    private static Point randomPointInSphere(Point center, double radius) {
        Random random = new Random();
        double theta = 2 * Math.PI * random.nextDouble();  // Random angle
        double phi = Math.acos(2 * random.nextDouble() - 1);  // Random polar angle
        double r = radius * Math.cbrt(random.nextDouble());  // Random radius scaled for uniform distribution

        // Convert spherical coordinates to Cartesian
        double x = r * Math.sin(phi) * Math.cos(theta);
        double y = r * Math.sin(phi) * Math.sin(theta);
        double z = r * Math.cos(phi);

        return center.add(new Vector(x, y, z));
    }

    public static void printBubbles(List<Sphere> bubbles, Random random) {
        for (Sphere bubble : bubbles) {
            Point center = bubble.getCenter();
            double radius = bubble.getRadius();

            // Random transparency (Kt) and reflection (Kr) to add realism
            double transparency = 0.8 + 0.2 * random.nextDouble();  // range: 0.8 to 1.0
            double reflection = 0.05 + 0.1 * random.nextDouble();   // range: 0.05 to 0.15

            System.out.printf(
                    "new Sphere(%.1f, new Point(%.1f, %.1f, %.1f))"
                            + ".setEmission(new Color(0, 0, 0))"
                            + ".setMaterial(new Material().setKd(0.1).setKs(0.3)"
                            + ".setShininess(50).setKr(%.2f).setKt(%.2f)),%n",
                    radius, center.getX(), center.getY(), center.getZ(), reflection, transparency);
        }
    }


*/


}

