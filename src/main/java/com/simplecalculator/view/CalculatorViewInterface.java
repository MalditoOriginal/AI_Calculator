package com.simplecalculator.view;

public interface CalculatorViewInterface {
    void updateDisplay(String text);
    void showError(String message);
    String getDisplayText();
    void clearDisplay();
}
