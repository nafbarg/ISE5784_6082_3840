package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * The Geometries class represents a collection of geometric objects.
 * It implements the Intersectable interface and allows for adding multiple geometric objects.
 */
public class Geometries implements Intersectable {
    final private List<Intersectable> geometries = new LinkedList<>();

    /**
     * Default constructor that initializes an empty collection of geometric objects.
     */
    public Geometries() {
    }

    /**
     * Constructor that initializes the collection with the given geometric objects.
     *
     * @param geometries The geometric objects to add to the collection.
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Adds the given geometric objects to the collection.
     *
     * @param geometries The geometric objects to add to the collection.
     */
    public void add(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = null;

        // Find intersections with each geometry in the collection
        for (Intersectable geometry : geometries) {
            List<Point> tempIntersections = geometry.findIntersections(ray);
            if (tempIntersections != null) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                intersections.addAll(tempIntersections);
            }
        }

        return intersections;
    }
}
