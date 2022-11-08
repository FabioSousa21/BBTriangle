package bbtriangle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TriangleTest {
    
    private Triangle triangle;

    @BeforeEach
    public void setUp() {
        triangle = new Triangle(1, 1, 1);
    }

    @Test
    public void testSetSideLengths() {
        int s1 = 2, s2 = 2, s3 = 2;
        Triangle expected = new Triangle(s1, s2, s3);
        Assertions.assertEquals(expected, triangle.setSideLengths(s1, s2, s3), "Triangle doesn't have the expected side lengths");
    }

}
