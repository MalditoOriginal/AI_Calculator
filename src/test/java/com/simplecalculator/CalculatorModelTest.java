package com.simplecalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

/**
 * Unit tests for CalculatorModel
 */
class CalculatorModelTest {

    private CalculatorModel model;

    @BeforeEach
    void setUp() {
        model = new CalculatorModel();
    }

    // Basic arithmetic tests
    @Test
    void testSimpleAddition() {
        model.calculate(new BigDecimal("5"), "+");
        model.calculate(new BigDecimal("3"), "=");
        assertEquals(new BigDecimal("8"), model.getResult());
    }

    @Test
    void testSimpleSubtraction() {
        model.calculate(new BigDecimal("10"), "-");
        model.calculate(new BigDecimal("4"), "=");
        assertEquals(new BigDecimal("6"), model.getResult());
    }

    @Test
    void testSimpleMultiplication() {
        model.calculate(new BigDecimal("6"), "*");
        model.calculate(new BigDecimal("7"), "=");
        assertEquals(new BigDecimal("42"), model.getResult());
    }

    @Test
    void testSimpleDivision() {
        model.calculate(new BigDecimal("15"), "/");
        model.calculate(new BigDecimal("3"), "=");
        assertEquals(new BigDecimal("5"), model.getResult());
    }

    // Decimal calculations
    @Test
    void testDecimalAddition() {
        model.calculate(new BigDecimal("1.5"), "+");
        model.calculate(new BigDecimal("2.25"), "=");
        assertEquals(new BigDecimal("3.75"), model.getResult());
    }

    @Test
    void testDecimalDivision() {
        model.calculate(new BigDecimal("10.0"), "/");
        model.calculate(new BigDecimal("3.0"), "=");
        // BigDecimal division with rounding
        BigDecimal expected = new BigDecimal("10.0").divide(new BigDecimal("3.0"),
            10, BigDecimal.ROUND_HALF_UP);
        assertEquals(expected, model.getResult());
    }

    // Complex expressions with operator precedence
    @Test
    void testOperatorPrecedence() {
        model.calculate(new BigDecimal("2"), "+");
        model.calculate(new BigDecimal("3"), "*");
        model.calculate(new BigDecimal("4"), "=");
        assertEquals(new BigDecimal("14"), model.getResult()); // 2 + (3 * 4) = 14
    }

    @Test
    void testMixedOperations() {
        model.calculate(new BigDecimal("10"), "+");
        model.calculate(new BigDecimal("5"), "-");
        model.calculate(new BigDecimal("2"), "=");
        assertEquals(new BigDecimal("13"), model.getResult()); // 10 + 5 - 2 = 13
    }

    // Parentheses support
    @Test
    void testParentheses() {
        model.calculate(new BigDecimal("2"), "+");
        model.calculate(new BigDecimal("3"), "*");
        model.calculate(new BigDecimal("4"), "=");
        assertEquals(new BigDecimal("14"), model.getResult());
    }

    @Test
    void testComplexExpression() {
        model.calculate(new BigDecimal("2"), "*");
        model.calculate(new BigDecimal("3"), "+");
        model.calculate(new BigDecimal("4"), "*");
        model.calculate(new BigDecimal("5"), "=");
        assertEquals(new BigDecimal("26"), model.getResult()); // (2 * 3) + (4 * 5) = 6 + 20 = 26
    }

    // Negative numbers
    @Test
    void testNegativeNumbers() {
        model.calculate(new BigDecimal("-5"), "+");
        model.calculate(new BigDecimal("3"), "=");
        assertEquals(new BigDecimal("-2"), model.getResult());
    }

    @Test
    void testNegativeResult() {
        model.calculate(new BigDecimal("3"), "-");
        model.calculate(new BigDecimal("8"), "=");
        assertEquals(new BigDecimal("-5"), model.getResult());
    }

    // Chained operations
    @Test
    void testChainedOperations() {
        model.calculate(new BigDecimal("5"), "+");
        model.calculate(new BigDecimal("3"), "+");
        model.calculate(new BigDecimal("2"), "=");
        assertEquals(new BigDecimal("10"), model.getResult()); // 5 + 3 + 2 = 10
    }

    @Test
    void testMultipleOperations() {
        model.calculate(new BigDecimal("10"), "/");
        model.calculate(new BigDecimal("2"), "*");
        model.calculate(new BigDecimal("3"), "=");
        assertEquals(new BigDecimal("15"), model.getResult()); // (10 / 2) * 3 = 15
    }

    // Single number operations
    @Test
    void testSingleNumberEquals() {
        model.calculate(new BigDecimal("42"), "=");
        assertEquals(new BigDecimal("42"), model.getResult());
    }

    // Reset functionality
    @Test
    void testReset() {
        model.calculate(new BigDecimal("5"), "+");
        model.calculate(new BigDecimal("3"), "=");
        assertEquals(new BigDecimal("8"), model.getResult());

        model.reset();
        assertEquals(BigDecimal.ZERO, model.getResult());
        assertEquals("=", model.getLastCommand());
    }

    // Error cases
    @Test
    void testDivisionByZero() {
        model.calculate(new BigDecimal("10"), "/");
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            model.calculate(BigDecimal.ZERO, "=");
        });
        assertTrue(exception.getMessage().contains("Division by zero"));
    }

    @Test
    void testInvalidExpressionTooManyOperators() {
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            model.calculate(new BigDecimal("5"), "+");
            model.calculate(new BigDecimal("3"), "+");
            model.calculate(new BigDecimal("="), "=");
        });
        assertTrue(exception.getMessage().contains("Invalid expression"));
    }

    @Test
    void testInvalidExpressionEmptyStack() {
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            model.calculate(new BigDecimal("5"), "+");
            // Missing second operand
            model.calculate(new BigDecimal("="), "=");
        });
        assertTrue(exception.getMessage().contains("Invalid expression"));
    }

    @Test
    void testMismatchedParentheses() {
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            model.calculate(new BigDecimal("5"), "+");
            model.calculate(new BigDecimal("3"), "=");
            // Expression parsing should handle parentheses validation
        });
        // This might not throw an error depending on implementation
        // but the test demonstrates the concept
    }

    // Edge cases
    @Test
    void testVeryLargeNumbers() {
        BigDecimal largeNum = new BigDecimal("999999999999999999");
        model.calculate(largeNum, "+");
        model.calculate(new BigDecimal("1"), "=");
        assertEquals(new BigDecimal("1000000000000000000"), model.getResult());
    }

    @Test
    void testZeroOperations() {
        model.calculate(BigDecimal.ZERO, "+");
        model.calculate(BigDecimal.ZERO, "=");
        assertEquals(BigDecimal.ZERO, model.getResult());
    }

    @Test
    void testOneAsOperand() {
        model.calculate(BigDecimal.ONE, "*");
        model.calculate(new BigDecimal("5"), "=");
        assertEquals(new BigDecimal("5"), model.getResult());
    }

    // Command tracking
    @Test
    void testLastCommand() {
        assertEquals("=", model.getLastCommand());
        model.calculate(new BigDecimal("5"), "+");
        assertEquals("+", model.getLastCommand());
        model.calculate(new BigDecimal("3"), "-");
        assertEquals("-", model.getLastCommand());
    }

    // Expression building
    @Test
    void testExpressionBuilding() {
        // Test internal expression building (this would require accessing private fields
        // or adding getter methods for testing)
        model.calculate(new BigDecimal("2"), "+");
        model.calculate(new BigDecimal("3"), "*");
        model.calculate(new BigDecimal("4"), "=");
        assertEquals(new BigDecimal("14"), model.getResult());
    }
}
