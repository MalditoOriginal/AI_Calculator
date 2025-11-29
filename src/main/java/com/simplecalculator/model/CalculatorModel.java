package com.simplecalculator.model;

import java.math.BigDecimal;
import java.util.Stack;
import java.util.logging.Logger;

public class CalculatorModel implements CalculatorModelInterface {
    private static final Logger logger = Logger.getLogger(CalculatorModel.class.getName());

    private BigDecimal result = BigDecimal.ZERO;
    private String lastCommand = "=";
    private StringBuilder currentExpression = new StringBuilder();

    // Method to evaluate an expression using Dijkstra's Shunting Yard algorithm
    public BigDecimal evaluateExpression(String expression) {
        // Tokenize by spaces because expressions are built as: "<num> <op> <num> <op> ..."
        String[] tokens = expression.trim().split("\\s+");
        Stack<BigDecimal> values = new Stack<>();
        Stack<Operator> operators = new Stack<>();

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            // If token is a number
            if (isNumber(token)) {
                values.push(new BigDecimal(token));
            }
            // If token is an operator
            else {
                Operator op = Operator.fromSymbol(token);
                if (op != null) {
                    if (op == Operator.LEFT_PARENTHESIS) {
                        operators.push(op);
                    } else if (op == Operator.RIGHT_PARENTHESIS) {
                        while (!operators.isEmpty() && operators.peek() != Operator.LEFT_PARENTHESIS) {
                            Operator topOp = operators.pop();
                            BigDecimal b = values.pop();
                            BigDecimal a = values.pop();
                            values.push(applyOperator(topOp, a, b));
                        }
                        if (!operators.isEmpty()) {
                            operators.pop(); // Remove the left parenthesis
                        }
                    } else {
                        while (!operators.isEmpty() && operators.peek() != Operator.LEFT_PARENTHESIS &&
                               op.getPrecedence() <= operators.peek().getPrecedence()) {
                            Operator topOp = operators.pop();
                            BigDecimal b = values.pop();
                            BigDecimal a = values.pop();
                            values.push(applyOperator(topOp, a, b));
                        }
                        operators.push(op);
                    }
                }
                // Unknown tokens are ignored
            }
        }

        // Process remaining operators
        try {
            while (!operators.isEmpty()) {
                Operator op = operators.pop();
                if (values.size() < 2) {
                    throw new ArithmeticException("Invalid expression: not enough operands for operator '" + op.getSymbol() + "'");
                }
                BigDecimal b = values.pop();
                BigDecimal a = values.pop();
                values.push(applyOperator(op, a, b));
            }

            if (values.size() != 1) {
                throw new ArithmeticException("Invalid expression: too many operands or mismatched operators");
            }

            BigDecimal finalResult = values.pop();
            logger.info("Evaluated expression: " + expression + " = " + finalResult);
            return finalResult;
        } catch (Exception ex) {
            if (ex instanceof ArithmeticException) {
                throw ex;
            }
            throw new ArithmeticException("Expression evaluation failed: " + ex.getMessage());
        }
    }

    // Check if token is a number
    private boolean isNumber(String token) {
        try {
            new BigDecimal(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Apply operator to operands
    private BigDecimal applyOperator(Operator op, BigDecimal a, BigDecimal b) {
        switch (op) {
            case ADD:
                return a.add(b);
            case SUBTRACT:
                return a.subtract(b);
            case MULTIPLY:
                return a.multiply(b);
            case DIVIDE:
                if (b.compareTo(BigDecimal.ZERO) == 0) throw new ArithmeticException("Division by zero");
                return a.divide(b, 10, BigDecimal.ROUND_HALF_UP); // Arbitrary precision
        }
        return BigDecimal.ZERO;
    }

    public void calculate(BigDecimal x, String command) {
        logger.info("Calculating with value: " + x + ", command: " + command);
        if (command.equals("=")) {
            if (currentExpression.length() > 0) {
                currentExpression.append(" ").append(x.toPlainString());
                try {
                    result = evaluateExpression(currentExpression.toString().trim());
                    currentExpression.setLength(0);
                } catch (ArithmeticException ex) {
                    // For arithmetic errors like division by zero, re-throw with context
                    throw ex;
                } catch (Exception ex) {
                    // If expression evaluation fails, throw a more user-friendly error
                    throw new ArithmeticException("Invalid mathematical expression. Please check parentheses and operators.");
                }
            } else {
                result = x;
            }
        } else {
            if (currentExpression.length() > 0) {
                currentExpression.append(" ");
            }
            currentExpression.append(x.toPlainString()).append(" ").append(command);
            lastCommand = command;
        }
        logger.info("Result: " + result);
    }

    public BigDecimal getResult() {
        return result;
    }

    public String getLastCommand() {
        return lastCommand;
    }

    public void reset() {
        result = BigDecimal.ZERO;
        lastCommand = "=";
        currentExpression.setLength(0);
    }
}
