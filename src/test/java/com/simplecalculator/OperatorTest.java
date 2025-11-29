package com.simplecalculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Operator enum
 */
class OperatorTest {

    @Test
    void testGetSymbol() {
        assertEquals("+", Operator.ADD.getSymbol());
        assertEquals("-", Operator.SUBTRACT.getSymbol());
        assertEquals("*", Operator.MULTIPLY.getSymbol());
        assertEquals("/", Operator.DIVIDE.getSymbol());
        assertEquals("(", Operator.LEFT_PARENTHESIS.getSymbol());
        assertEquals(")", Operator.RIGHT_PARENTHESIS.getSymbol());
    }

    @Test
    void testGetPrecedence() {
        assertEquals(1, Operator.ADD.getPrecedence());
        assertEquals(1, Operator.SUBTRACT.getPrecedence());
        assertEquals(2, Operator.MULTIPLY.getPrecedence());
        assertEquals(2, Operator.DIVIDE.getPrecedence());
        assertEquals(0, Operator.LEFT_PARENTHESIS.getPrecedence());
        assertEquals(0, Operator.RIGHT_PARENTHESIS.getPrecedence());
    }

    @Test
    void testFromSymbol() {
        assertEquals(Operator.ADD, Operator.fromSymbol("+"));
        assertEquals(Operator.SUBTRACT, Operator.fromSymbol("-"));
        assertEquals(Operator.MULTIPLY, Operator.fromSymbol("*"));
        assertEquals(Operator.DIVIDE, Operator.fromSymbol("/"));
        assertEquals(Operator.LEFT_PARENTHESIS, Operator.fromSymbol("("));
        assertEquals(Operator.RIGHT_PARENTHESIS, Operator.fromSymbol(")"));
        assertNull(Operator.fromSymbol("invalid"));
        assertNull(Operator.fromSymbol(""));
    }

    @Test
    void testIsLeftAssociative() {
        assertTrue(Operator.ADD.isLeftAssociative());
        assertTrue(Operator.SUBTRACT.isLeftAssociative());
        assertTrue(Operator.MULTIPLY.isLeftAssociative());
        assertTrue(Operator.DIVIDE.isLeftAssociative());
        assertTrue(Operator.LEFT_PARENTHESIS.isLeftAssociative());
        assertFalse(Operator.RIGHT_PARENTHESIS.isLeftAssociative());
    }

    @Test
    void testAllOperatorsHaveUniqueSymbols() {
        Operator[] operators = Operator.values();
        for (int i = 0; i < operators.length; i++) {
            for (int j = i + 1; j < operators.length; j++) {
                assertNotEquals(operators[i].getSymbol(), operators[j].getSymbol(),
                    "Duplicate symbol found: " + operators[i].getSymbol());
            }
        }
    }
}
