/**
 * Simple Test Runner for SimpleCalculator
 * Runs basic tests without requiring JUnit
 */
public class SimpleTestRunner {

    private static int testsRun = 0;
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("=== SimpleCalculator Test Runner ===\n");

        // Run all test suites
        runOperatorTests();
        runCalculatorModelTests();
        runIntegrationTests();

        // Print results
        System.out.println("\n=== Test Results ===");
        System.out.println("Tests run: " + testsRun);
        System.out.println("Tests passed: " + testsPassed);
        System.out.println("Tests failed: " + testsFailed);
        System.out.println("Success rate: " + (testsRun > 0 ? (testsPassed * 100 / testsRun) : 0) + "%");

        if (testsFailed == 0) {
            System.out.println("✅ All tests passed!");
        } else {
            System.out.println("❌ Some tests failed. Please check the implementation.");
        }
    }

    private static void runOperatorTests() {
        System.out.println("Running Operator Tests...");

        // Test operator symbols
        test("Operator.ADD.getSymbol() == '+'",
            com.simplecalculator.model.Operator.ADD.getSymbol().equals("+"));

        test("Operator.SUBTRACT.getSymbol() == '-'",
            com.simplecalculator.model.Operator.SUBTRACT.getSymbol().equals("-"));

        test("Operator.MULTIPLY.getSymbol() == '*'",
            com.simplecalculator.model.Operator.MULTIPLY.getSymbol().equals("*"));

        test("Operator.DIVIDE.getSymbol() == '/'",
            com.simplecalculator.model.Operator.DIVIDE.getSymbol().equals("/"));

        // Test precedence
        test("ADD precedence == 1",
            com.simplecalculator.model.Operator.ADD.getPrecedence() == 1);

        test("MULTIPLY precedence == 2",
            com.simplecalculator.model.Operator.MULTIPLY.getPrecedence() == 2);

        // Test fromSymbol
        test("fromSymbol('+') returns ADD",
            com.simplecalculator.model.Operator.fromSymbol("+") == com.simplecalculator.model.Operator.ADD);

        test("fromSymbol('invalid') returns null",
            com.simplecalculator.model.Operator.fromSymbol("invalid") == null);
    }

    private static void runCalculatorModelTests() {
        System.out.println("\nRunning CalculatorModel Tests...");

        com.simplecalculator.model.CalculatorModel model = new com.simplecalculator.model.CalculatorModel();

        // Test basic addition
        model.calculate(java.math.BigDecimal.valueOf(5), "+");
        model.calculate(java.math.BigDecimal.valueOf(3), "=");
        test("5 + 3 = 8",
            model.getResult().equals(java.math.BigDecimal.valueOf(8)));

        // Reset for next test
        model.reset();

        // Test multiplication
        model.calculate(java.math.BigDecimal.valueOf(6), "*");
        model.calculate(java.math.BigDecimal.valueOf(7), "=");
        test("6 * 7 = 42",
            model.getResult().equals(java.math.BigDecimal.valueOf(42)));

        // Reset for next test
        model.reset();

        // Test operator precedence
        model.calculate(java.math.BigDecimal.valueOf(2), "+");
        model.calculate(java.math.BigDecimal.valueOf(3), "*");
        model.calculate(java.math.BigDecimal.valueOf(4), "=");
        test("2 + 3 * 4 = 14 (precedence)",
            model.getResult().equals(java.math.BigDecimal.valueOf(14)));

        // Reset for next test
        model.reset();

        // Test decimal numbers
        model.calculate(java.math.BigDecimal.valueOf(1.5), "+");
        model.calculate(java.math.BigDecimal.valueOf(2.25), "=");
        test("1.5 + 2.25 = 3.75",
            model.getResult().equals(java.math.BigDecimal.valueOf(3.75)));

        // Test negative numbers
        model.reset();
        model.calculate(java.math.BigDecimal.valueOf(-5), "+");
        model.calculate(java.math.BigDecimal.valueOf(3), "=");
        test("-5 + 3 = -2",
            model.getResult().equals(java.math.BigDecimal.valueOf(-2)));
    }

    private static void runIntegrationTests() {
        System.out.println("\nRunning Integration Tests...");

        // Test the complete workflow
        com.simplecalculator.model.CalculatorModel model = new com.simplecalculator.model.CalculatorModel();

        // Complex expression: 2 + 3 * 4 = 14 (precedence: 2 + (3 * 4) = 14)
        model.calculate(java.math.BigDecimal.valueOf(2), "+");
        model.calculate(java.math.BigDecimal.valueOf(3), "*");
        model.calculate(java.math.BigDecimal.valueOf(4), "=");

        test("Complex expression 2 + 3 * 4 = 14 (precedence)",
            model.getResult().equals(java.math.BigDecimal.valueOf(14)));

        // Test error handling - division by zero (throws exception in model)
        model.reset();
        model.calculate(java.math.BigDecimal.valueOf(10), "/");

        boolean divisionByZeroThrows = false;
        try {
            model.calculate(java.math.BigDecimal.ZERO, "=");
            // If no exception thrown, that's unexpected
            divisionByZeroThrows = false;
        } catch (ArithmeticException e) {
            // Exception should be thrown for division by zero
            divisionByZeroThrows = e.getMessage().contains("Division by zero");
        }

        test("Division by zero throws ArithmeticException",
            divisionByZeroThrows);
    }

    private static void test(String description, boolean condition) {
        testsRun++;
        if (condition) {
            testsPassed++;
            System.out.println("✅ PASS: " + description);
        } else {
            testsFailed++;
            System.out.println("❌ FAIL: " + description);
        }
    }
}
