package com.rightpoint.training.utils;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("While testing meth util ")
class MathUtilsTest {

    MathUtils mathUtils;

    @BeforeEach
    void init() {
        mathUtils = new MathUtils();
    }

    @Test
    @DisplayName("Test Add Method ")
    @Tag("Math")
    void testAdd() {
        int expected = 6;
        int actual = mathUtils.add(3, 3);
        assertEquals(expected, actual, "Value should be same!");
    }

    @Test
    @DisplayName("Test Compute Circle Area Method ")
    @Tag("Circle")
    void testComputeCircleArea() {
        double expected = 314.1592653589793;
        assertEquals(expected, mathUtils.computeCircleArea(10), "Value should be same!");
    }

    @Test
    @DisplayName("Multiply method")
    @Tag("Math")
    void testMultiply() {
        assertAll(
                () -> assertEquals(4, mathUtils.multiply(2, 2)),
                () -> assertEquals(0, mathUtils.multiply(0, 2)),
                () -> assertEquals(-2, mathUtils.multiply(2, -1))
        );
    }

    @Test
    void timeOutNot(){
        assertTimeout(Duration.ofMinutes(10), () -> System.out.println(10));
    }

    @Test
    @DisplayName("Test Divide")
    @Tag("Math")
    void testDivide() {
        assertThrows(ArithmeticException.class, () -> mathUtils.divide(1, 0), "Divide by zero should throw ");
    }

    @Nested
    @DisplayName("Add Method ")
    @Tag("Math")
    class testAdd {

        @Test
        @DisplayName("when adding two positive number")
        void testAddPositiveNumber() {
            assertEquals(2, mathUtils.add(1, 1), "Value should be same!");
        }

        @Test
        @DisplayName("when adding two negative number")
        void testAddNegativeNumber() {
            assertEquals(-2, mathUtils.add(-1, -1), "Value should be same!");
        }
    }

}
