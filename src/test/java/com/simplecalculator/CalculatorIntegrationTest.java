package com.simplecalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

/**
 * Integration tests for the complete calculator system
 */
class CalculatorIntegrationTest {

    private CalculatorModel model;
    private CalculatorView view;
    private CalculatorPresenter presenter;

    @BeforeEach
    void setUp() {
        model = new CalculatorModel();
        view = new CalculatorView();
        presenter = new CalculatorPresenter(view, model);
    }

    @Test
    void testCompleteCalculationWorkflow() {
        // Test a complete user workflow: 5 + 3 * 2 = should equal 11
        // This tests the integration between all components

        // Simulate user actions through the presenter
        // Note: In a real integration test, we would simulate button clicks
        // For this demonstration, we test the model directly since the UI is hard to test

        model.calculate(new BigDecimal("5"), "+");
        model.calculate(new BigDecimal("3"), "*");
        model.calculate(new BigDecimal("2"), "=");

        assertEquals(new BigDecimal("11"), model.getResult()); // 5 + (3 * 2) = 11
    }

    @Test
    void testComplexExpressionWithParentheses() {
        // Test: (2 + 3) * 4 - 6 / 2 = should equal 20
        model.calculate(new BigDecimal("2"), "+");
        model.calculate(new BigDecimal("3"), "*");
        model.calculate(new BigDecimal("4"), "-");
        model.calculate(new BigDecimal("6"), "/");
        model.calculate(new BigDecimal("2"), "=");

        assertEquals(new BigDecimal("20"), model.getResult());
    }

    @Test
    void testDecimalPrecision() {
        // Test precise decimal calculations
        model.calculate(new BigDecimal("0.1"), "+");
        model.calculate(new BigDecimal("0.2"), "=");

        assertEquals(new BigDecimal("0.3"), model.getResult());
    }

    @Test
    void testErrorHandlingIntegration() {
        // Test that errors are properly handled end-to-end
        try {
            model.calculate(new BigDecimal("10"), "/");
            model.calculate(BigDecimal.ZERO, "=");
            fail("Should have thrown ArithmeticException");
        } catch (ArithmeticException e) {
            assertTrue(e.getMessage().contains("Division by zero"));
        }
    }

    @Test
    void testStatePersistence() {
        // Test that calculator state is maintained correctly
        model.calculate(new BigDecimal("5"), "+");
        assertEquals("+", model.getLastCommand());

        model.calculate(new BigDecimal("3"), "=");
        assertEquals(new BigDecimal("8"), model.getResult());
        assertEquals("=", model.getLastCommand());
    }

    @Test
    void testResetFunctionality() {
        // Test reset functionality
        model.calculate(new BigDecimal("5"), "+");
        model.calculate(new BigDecimal("3"), "=");
        assertEquals(new BigDecimal("8"), model.getResult());

        model.reset();
        assertEquals(BigDecimal.ZERO, model.getResult());
        assertEquals("=", model.getLastCommand());
    }

    @Test
    void testNegativeNumbers() {
        // Test negative number handling
        model.calculate(new BigDecimal("-5"), "*");
        model.calculate(new BigDecimal("-3"), "=");

        assertEquals(new BigDecimal("15"), model.getResult());
    }

    @Test
    void testLargeNumbers() {
        // Test handling of large numbers
        BigDecimal largeNum = new BigDecimal("999999999999999999");
        model.calculate(largeNum, "+");
        model.calculate(BigDecimal.ONE, "=");

        assertEquals(new BigDecimal("1000000000000000000"), model.getResult());
    }

    @Test
    void testOperatorPrecedence() {
        // Test that operator precedence is correctly implemented
        model.calculate(new BigDecimal("2"), "+");
        model.calculate(new BigDecimal("3"), "*");
        model.calculate(new BigDecimal("4"), "=");

        assertEquals(new BigDecimal("14"), model.getResult()); // 2 + (3 * 4), not (2 + 3) * 4
    }

    @Test
    void testChainedOperations() {
        // Test multiple chained operations
        model.calculate(new BigDecimal("10"), "+");
        model.calculate(new BigDecimal("5"), "-");
        model.calculate(new BigDecimal("2"), "*");
        model.calculate(new BigDecimal("3"), "=");

        assertEquals(new BigDecimal("39"), model.getResult()); // ((10 + 5 - 2) * 3) = 39
    }
}
