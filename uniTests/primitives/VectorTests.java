package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTests {

    private static final double DELTA = 0.0000001;

    /**
     * Test method for {@link primitives.Vector#Vector(double, double, double)}.
     */
    @Test
    void testConstructor() {
        // =============== Boundary Values Tests ==================
        // TC11: Test a vector with a zero value
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0), "Constructed a vector with zero values");
    }

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        Vector v = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Adding a vector with positive values to a vector
        Vector vr1 = v.add(new Vector(2, 3, 4));
        assertEquals(new Vector(3, 5, 7), vr1, "Wrong result of add()");
        // TC02: Adding a vector with negative values to a vector
        Vector vr2 = v.add(new Vector(-1, -1, -1));
        assertEquals(new Vector(0, 1, 2), vr2, "Wrong result of add()");

        // =============== Boundary Values Tests ==================
        // TC11: Adding a vector to minus itself
        Vector vr3 = new Vector(-1, -2, -3);
        assertThrows(IllegalArgumentException.class, () -> v.add(vr3), "Adding a vector to itself should throw an exception");


    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}
     */
    @Test
    void testScale() {
        Vector v = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Scaling a vector by a positive value
        Vector vr1 = v.scale(2);
        assertEquals(new Vector(2, 4, 6), vr1, "Wrong result of scale()");
        // TC02: Scaling a vector by a negative value
        Vector vr2 = v.scale(-1);
        assertEquals(new Vector(-1, -2, -3), vr2, "Wrong result of scale()");

        // =============== Boundary Values Tests ==================
        // TC11: Scaling a vector by zero
        assertThrows(IllegalArgumentException.class, () -> v.scale(0), "Scaling a vector by zero should throw an exception");

    }

    /**
     * Test method for {@link Vector#lengthSquared()}
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the length squared of a vector is proper
        Vector v = new Vector(1, 2, 3);
        assertEquals(14, v.lengthSquared(), DELTA, "Wrong result of lengthSquared()");
    }

    /**
     * Test method for {@link Vector#length()}
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the length of a vector is proper
        Vector v = new Vector(0, 3, 4);
        assertEquals(5, v.length(), DELTA, "Wrong result of length()");
    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the normalization of a vector is proper
        Vector v = new Vector(0, 3, 4);
        Vector vr = v.normalize();
        assertEquals(new Vector(0, 0.6, 0.8), vr, "Wrong result of normalize()");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the dot product of two vectors is proper with a sharp angle
        assertEquals(-28, v1.dotProduct(v2), DELTA, "Wrong result of dotProduct()");
        // TC02: Test that the dot product of two vectors is proper with an obtuse angle
        Vector v3 = new Vector(-1, -2, -4);
        assertEquals(-17, v1.dotProduct(v3), DELTA, "Wrong result of dotProduct()");

        // =============== Boundary Values Tests ==================
        // TC11: Test that the dot product of two orthogonal vectors is zero
        Vector v4 = new Vector(0, 3, -2);
        assertEquals(0, v1.dotProduct(v4), DELTA, "dotProduct() for orthogonal vectors is not zero");
        // TC12: Test that the dot product of a vector with itself is proper
        assertEquals(v1.lengthSquared(), v1.dotProduct(v1), DELTA, "dotProduct() for a vector with itself is wrong");
        // TC13: Test that the dot product of a vector with unit vector is proper
        assertEquals(v1.length(), v1.dotProduct(v1.normalize()), DELTA, "dotProduct() for a vector with unit vector is wrong");

    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {
        Vector v123 = new Vector(0, 0, 1);
        Vector v03M2 = new Vector(1, 0, 0);
        // ============ Equivalence Partitions Tests ==============
        Vector vr = v123.crossProduct(v03M2);
        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v123.length() * v03M2.length(), vr.length(), DELTA, "crossProduct() wrong result length");
        // TC02: Test cross-product result orthogonality to its operands
        assertEquals(0, vr.dotProduct(v123), "crossProduct() result is not orthogonal to 1st operand");
        assertEquals(0, vr.dotProduct(v03M2), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of parallel vectors
        Vector vM2M4M6 = new Vector(0, 0, 5);
        assertThrows(IllegalArgumentException.class, () -> v123.crossProduct(vM2M4M6),
                "crossProduct() for parallel vectors does not throw an exception");
        // TC12: Cross product of two anti parallel vectors
        Vector v3 = new Vector(0, 0, -1); // anti-parallel to v1
        assertThrows(IllegalArgumentException.class, () -> v123.crossProduct(v3),
                "Cross product of anti-parallel vectors should throw an exception");
        // TC13: Cross product of a vector with itself
        assertThrows(IllegalArgumentException.class, () -> v123.crossProduct(v123),
                "Cross product of a vector with itself should throw an exception");

    }
}