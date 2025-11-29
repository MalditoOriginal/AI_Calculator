package com.simplecalculator.view;

import javax.swing.*;
import java.awt.*;

public class CalculatorView extends JFrame implements CalculatorViewInterface {
    private JTextField display;
    private JPanel buttonPanel;
    private JButton clearButton;
    private JButton backspaceButton;
    private JButton[] numberButtons;
    private JButton[] operationButtons;

    public CalculatorView() {
        // Set up the frame first
        setTitle("Advanced Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        // Set up the display with enhanced styling
        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Segoe UI", Font.BOLD, 32));
        display.setBackground(new Color(255, 255, 255));
        display.setForeground(new Color(50, 50, 50));
        display.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        display.setPreferredSize(new Dimension(400, 60));

        // Create main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add display to main panel
        mainPanel.add(display, BorderLayout.NORTH);

        // Create button panel with better layout
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 8, 8));
        buttonPanel.setBackground(new Color(240, 240, 240));

        // Initialize button arrays
        numberButtons = new JButton[13]; // 0-9, decimal point, parentheses (2)
        operationButtons = new JButton[5]; // +, -, *, /, =

        // Enhanced button styling
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 18);
        Color numberButtonColor = new Color(255, 255, 255);
        Color operatorButtonColor = new Color(255, 165, 0);
        Color equalsButtonColor = new Color(50, 150, 255);
        Color clearButtonColor = new Color(255, 100, 100);
        Color backspaceButtonColor = new Color(150, 150, 150);
        Color buttonTextColor = new Color(50, 50, 50);

        // Define all button labels in order
        String[][] buttonGrid = {
            {"7", "8", "9", "/"},
            {"4", "5", "6", "*"},
            {"1", "2", "3", "-"},
            {"0", ".", "C", "+"},
            {"(", ")", "=", "←"}
        };

        int numberIndex = 0;
        int operationIndex = 0;

        // Create buttons for each grid position
        for (String[] row : buttonGrid) {
            for (String label : row) {
                JButton button = new JButton(label);

                // Enhanced button styling
                button.setFont(buttonFont);
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createRaisedBevelBorder());
                button.setPreferredSize(new Dimension(80, 60));
                button.setMargin(new Insets(5, 5, 5, 5));

                // Color coding and array assignment based on button type
                if (Character.isDigit(label.charAt(0)) || label.equals(".") || label.equals("(") || label.equals(")")) {
                    button.setBackground(numberButtonColor);
                    button.setForeground(buttonTextColor);
                    numberButtons[numberIndex++] = button;
                } else if (label.equals("+") || label.equals("-") || label.equals("*") || label.equals("/")) {
                    button.setBackground(operatorButtonColor);
                    button.setForeground(Color.WHITE);
                    operationButtons[operationIndex++] = button;
                } else if (label.equals("=")) {
                    button.setBackground(equalsButtonColor);
                    button.setForeground(Color.WHITE);
                    operationButtons[operationIndex++] = button;
                } else if (label.equals("C")) {
                    button.setBackground(clearButtonColor);
                    button.setForeground(Color.WHITE);
                    clearButton = button; // C is the clear button
                } else if (label.equals("←")) {
                    button.setBackground(backspaceButtonColor);
                    button.setForeground(Color.WHITE);
                    backspaceButton = button; // ← is the backspace button
                }

                buttonPanel.add(button);
            }
        }

        // Add button panel to main panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add main panel to frame
        add(mainPanel, BorderLayout.CENTER);

        // Enhanced frame properties
        setSize(450, 650);
        setMinimumSize(new Dimension(400, 550));
        setLocationRelativeTo(null);
        setResizable(true);

        // Add window shadow effect (visual enhancement)
        getRootPane().setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        // Request focus for keyboard input
        requestFocusInWindow();
    }

    public JTextField getDisplay() {
        return display;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton[] getNumberButtons() {
        return numberButtons;
    }

    public JButton[] getOperationButtons() {
        return operationButtons;
    }

    @Override
    public void updateDisplay(String text) {
        display.setText(text);
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public String getDisplayText() {
        return display.getText();
    }

    @Override
    public void clearDisplay() {
        display.setText("0");
    }
}
