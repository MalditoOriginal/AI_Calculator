package com.simplecalculator.main;

import com.simplecalculator.model.CalculatorModel;
import com.simplecalculator.model.CalculatorModelInterface;
import com.simplecalculator.view.CalculatorView;
import com.simplecalculator.view.CalculatorViewInterface;
import com.simplecalculator.presenter.CalculatorPresenter;
import javax.swing.*;

public class Calculator {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorModelInterface model = new CalculatorModel();
            CalculatorViewInterface view = new CalculatorView();
            @SuppressWarnings("unused")
            CalculatorPresenter presenter = new CalculatorPresenter(view, model);
            ((CalculatorView) view).setVisible(true);
        });
    }
}
