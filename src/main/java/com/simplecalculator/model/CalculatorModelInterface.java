package com.simplecalculator.model;

import java.math.BigDecimal;

public interface CalculatorModelInterface {
    void calculate(BigDecimal x, String command);
    BigDecimal getResult();
    String getLastCommand();
    void reset();
}
