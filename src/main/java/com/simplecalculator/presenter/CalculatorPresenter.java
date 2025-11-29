package com.simplecalculator.presenter;

import com.simplecalculator.model.CalculatorModelInterface;
import com.simplecalculator.view.CalculatorViewInterface;
import com.simplecalculator.view.CalculatorView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.logging.Logger;

public class CalculatorPresenter {
    private static final Logger logger = Logger.getLogger(CalculatorPresenter.class.getName());

    private CalculatorViewInterface view;
    private CalculatorModelInterface model;
    private boolean start = true;

    public CalculatorPresenter(CalculatorViewInterface view, CalculatorModelInterface model) {
        this.view = view;
        this.model = model;
        setupEventHandlers();
        setupKeyboardBindings();
    }

    private void setupEventHandlers() {
        // Get buttons from view (assuming view provides access)
        // Since view is interface, we need to cast or assume it's CalculatorView
        if (view instanceof CalculatorView) {
            CalculatorView concreteView = (CalculatorView) view;

            // Number buttons
            for (JButton button : concreteView.getNumberButtons()) {
                button.addActionListener(new NumberListener());
            }

            // Operation buttons
            for (JButton button : concreteView.getOperationButtons()) {
                button.addActionListener(new OperatorListener());
            }

            // Clear button (C)
            concreteView.getClearButton().addActionListener(e -> {
                logger.info("Clear button pressed");
                handleClear();
            });

            // Backspace button (←) - access via reflection or direct cast
            try {
                java.lang.reflect.Field backspaceField = concreteView.getClass().getDeclaredField("backspaceButton");
                backspaceField.setAccessible(true);
                JButton backspaceButton = (JButton) backspaceField.get(concreteView);
                if (backspaceButton != null) {
                    backspaceButton.addActionListener(e -> {
                        logger.info("Backspace button pressed");
                        handleBackspace();
                    });
                }
            } catch (Exception e) {
                // Backspace button not accessible, keyboard shortcut will work
            }
        }
    }

    private void setupKeyboardBindings() {
        // Similar to before, but using view
        // For simplicity, assume view is CalculatorView
        if (view instanceof CalculatorView) {
            CalculatorView concreteView = (CalculatorView) view;
            JComponent contentPane = (JComponent) concreteView.getContentPane();

            // Number keys
            String[] numberKeys = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "(", ")"};
            for (String key : numberKeys) {
                contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(key.charAt(0)),
                    "number" + key
                );
                contentPane.getActionMap().put("number" + key, new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleNumberInput(key);
                    }
                });
            }

            // Operation keys
            String[] operationKeys = {"+", "-", "*", "/"};
            for (String key : operationKeys) {
                contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(key.charAt(0)),
                    "operation" + key
                );
                contentPane.getActionMap().put("operation" + key, new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleOperation(key);
                    }
                });
            }

            // Equals
            contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("ENTER"),
                "equals"
            );
            contentPane.getActionMap().put("equals", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleEquals();
                }
            });

            // Clear
            contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke('C'),
                "clear"
            );
            contentPane.getActionMap().put("clear", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    start = true;
                    model.reset();
                    view.clearDisplay();
                }
            });

            // Backspace
            contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("BACK_SPACE"),
                "backspace"
            );
            contentPane.getActionMap().put("backspace", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleBackspace();
                }
            });
        }
    }

    private void handleNumberInput(String input) {
        logger.info("Number input: " + input);
        if (start) {
            view.updateDisplay(input);
            start = false;
        } else {
            view.updateDisplay(view.getDisplayText() + input);
        }
    }

    private void handleOperation(String command) {
        logger.info("Operator input: " + command);
        if (start) {
            if (command.equals("-")) {
                view.updateDisplay(command);
                start = false;
            } else {
                model.calculate(BigDecimal.ZERO, command);
            }
        } else {
            try {
                BigDecimal value = new BigDecimal(view.getDisplayText());
                model.calculate(value, command);
                start = true;
            } catch (NumberFormatException ex) {
                view.showError("Error: '" + view.getDisplayText() + "' is not a valid number. Please enter a valid number.");
                start = true;
            } catch (ArithmeticException ex) {
                view.showError("Expression Error: " + ex.getMessage());
                start = true;
            }
        }
    }

    private void handleEquals() {
        if (!start) {
            try {
                BigDecimal value = new BigDecimal(view.getDisplayText());
                model.calculate(value, "=");
                view.updateDisplay(model.getResult().toString());
                start = true;
            } catch (NumberFormatException ex) {
                view.showError("Error: '" + view.getDisplayText() + "' is not a valid number. Please enter a valid number before pressing equals.");
                start = true;
            } catch (ArithmeticException ex) {
                String message = ex.getMessage();
                if (message.contains("Division by zero")) {
                    view.showError("Math Error: Cannot divide by zero. Please check your expression.");
                } else if (message.contains("Invalid expression")) {
                    view.showError("Syntax Error: " + message + ". Please check your mathematical expression.");
                } else if (message.contains("Invalid mathematical expression")) {
                    view.showError("Expression Error: " + message + ". Common issues: mismatched parentheses, consecutive operators.");
                } else {
                    view.showError("Math Error: " + message + ". Please check your expression.");
                }
                start = true;
            }
        }
    }

    private void handleBackspace() {
        String current = view.getDisplayText();
        if (current.length() > 1) {
            view.updateDisplay(current.substring(0, current.length() - 1));
        } else {
            view.updateDisplay("0");
            start = true;
        }
    }

    private void handleClear() {
        start = true;
        model.reset();
        view.clearDisplay();
    }

    private class NumberListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String input = event.getActionCommand();
            handleNumberInput(input);
        }
    }

    private class OperatorListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();
            handleOperation(command);
        }
    }
}
