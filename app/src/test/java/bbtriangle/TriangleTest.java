package bbtriangle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.ThrowingConsumer;

import com.google.common.base.Function;

public class TriangleTest {
    
    private Triangle triangle;

    @BeforeEach
    public void setUp() {
        triangle = new Triangle(0, 0, 0);
    }

    @Test
    public void testSetSideLengths() {
        int[] sides = new int[]{1, 2, 3};
        
        System.out.println(triangle.setSideLengths(sides[0], sides[1], sides[2]).getSideLengths().split(",")[1]);

        for (int i = 0; i < 3; i++) {
            Assertions.assertEquals(sides[i], Integer.valueOf(triangle.setSideLengths(
                sides[0], sides[1], sides[2]).getSideLengths().split(",")[i]), 
                "Triangle doesn't have the expected side 1 length");
        }
    }

    @Test
    public void testGetPerimeter() {
        int[] sides = new int[]{3, 2, 4};
        int expected = sides[0] + sides[1] + sides[2];
        triangle.setSideLengths(sides[0], sides[1], sides[2]);

        Assertions.assertEquals(expected, triangle.getPerimeter(), "Triangle doesn't have the right perimeter");
    }

    @Test
    public void testGetArea() {
        int[] sides = new int[]{7, 2, 6};
        double expected = 0.25 * Math.sqrt((sides[0] + sides[1] + sides[2]) * (-sides[0] + sides[1] + sides[2]) * 
            (sides[0] - sides[1] + sides[2]) * (sides[0] + sides[1] - sides[2]));
        triangle.setSideLengths(sides[0], sides[1], sides[2]);

        Assertions.assertEquals(expected, triangle.getArea(), "Triangle failed at calculating it's area");
    }

    @Test
    public void testClassify() {
        triangle.setSideLengths(-1, 2, 3);
        Assertions.assertEquals("impossible", triangle.classify());
        
        triangle.setSideLengths(3, 3, 3);
        Assertions.assertEquals("equilateral", triangle.classify());
        
        triangle.setSideLengths(3, 2, 3);
        Assertions.assertEquals("isossceles", triangle.classify());
        
        triangle.setSideLengths(3, 4, 5);
        Assertions.assertEquals("right-angled", triangle.classify());
        
        triangle.setSideLengths(3, 2, 4);
        Assertions.assertEquals("scalene", triangle.classify());
    }

    @Test
    public void testIsIsosceles() {
        triangle.setSideLengths(5, 5, 3);
        Assertions.assertEquals(true, triangle.isIsosceles());
        
        triangle.setSideLengths(3, 5, 5);
        Assertions.assertEquals(true, triangle.isIsosceles());
        
        triangle.setSideLengths(5, 3, 5);
        Assertions.assertEquals(true, triangle.isIsosceles());
    }

    @Test
    public void testIsEquilateral() {
        triangle.setSideLengths(5, 5, 5);
        Assertions.assertEquals(true, triangle.isEquilateral());

        triangle.setSideLengths(5, 3, 5);
        Assertions.assertEquals(false, triangle.isEquilateral());
    }

    @Test
    public void testIsRightAngled() {
        triangle.setSideLengths(4, 3, 5);
        Assertions.assertEquals(true, triangle.isRightAngled());

        triangle.setSideLengths(4, 3, 7);
        Assertions.assertEquals(false, triangle.isRightAngled());
    }

    @Test
    public void testIsScalene() {
        triangle.setSideLengths(1, 2, 3);
        Assertions.assertEquals(true, triangle.isScalene());
        
        triangle.setSideLengths(1, 2, 2);
        Assertions.assertEquals(false, triangle.isScalene());
    }

    @Test
    public void testIsImpossible() {
        triangle.setSideLengths(-1, 3, 4);
        Assertions.assertEquals(true, triangle.isImpossible());
        
        triangle.setSideLengths(1, 2, 7);
        Assertions.assertEquals(true, triangle.isImpossible());
        
        triangle.setSideLengths(1, 2, 3);
        Assertions.assertEquals(true, triangle.isImpossible());
        
        triangle.setSideLengths(2, 3, 2);
        Assertions.assertEquals(false, triangle.isImpossible());
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestFromStream() {
        List<Triangle> inputTriangles = Arrays.asList(
            new Triangle(2, 2, 2), new Triangle(5, 6, 7), new Triangle (1000,10,10)
        );
        // expected classification results
        List<String> outputClassification = Arrays.asList(
            "equilateral", "scalene", "impossible"
        );
        
        //input generator ()
        Iterator<Triangle> inputGenerator = inputTriangles.iterator(); // returns de Triangle one by one

 

        // diplay name generator. Build the display name for each test in the form "Classification of Triangle: s1,s2,s3" , "Testing isosceles Classification", ...
        Function<Triangle, String> displayNameGenerator 
         = (input) -> "Classification of Triangle: " + input.getSideLengths();

 

        // test executor
        ThrowingConsumer<Triangle> testExecutor = input -> {
            int pos = inputTriangles.indexOf(input);
            
            assertEquals(outputClassification.get(pos),input.classify());
        };
        // return stream

 

        return DynamicTest.stream(inputGenerator, displayNameGenerator, testExecutor);
    }

}
