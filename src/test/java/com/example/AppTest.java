package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AppTest {
    private final Calculator calc = new Calculator();

    @Test
    public void testAdd() {
        assertEquals(5.0, calc.add(2, 3), 0.00001);
    }

    @Test
    public void testSubtract() {
        assertEquals(-1.0, calc.subtract(2, 3), 0.00001);
    }

    @Test
    public void testMultiply() {
        assertEquals(6.0, calc.multiply(2, 3), 0.00001);
    }

    @Test
    public void testDivide() {
        assertEquals(2.0, calc.divide(6, 3), 0.00001);
    }

    @Test
    public void testDivideByZero() {
        assertThrows(IllegalArgumentException.class, () -> calc.divide(1, 0));
    }
}
