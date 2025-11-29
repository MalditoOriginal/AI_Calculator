# Architecture Guide for SimpleCalculator

This repository is a professional Java Swing calculator application built with MVP (Model-View-Presenter) architecture. This guide helps developers understand the project's structure, patterns, and development workflows.

## Quick Start (Build & Run)

**Prerequisites:**
- Java 8 or higher
- Java Development Kit (JDK)

**Compile:**
```bash
cd SimpleCalculator/src/main/java
javac com/simplecalculator/main/Calculator.java
```

**Run:**
```bash
java com.simplecalculator.main.Calculator
```

**Notes:** The project uses proper package structure (`com.simplecalculator.*`). Always compile from the `src/main/java` directory.

## Architecture Overview

### MVP Pattern
- **Model**: Business logic and data management
- **View**: Passive GUI components (interface-based)
- **Presenter**: User interaction handling and coordination

### Package Structure
```
com.simplecalculator/
├── main/
│   └── Calculator.java                 # Application entry point
├── model/
│   ├── CalculatorModel.java            # Calculation logic & BigDecimal math
│   ├── CalculatorModelInterface.java   # Model contract
│   └── Operator.java                   # Operator enum with precedence
├── view/
│   ├── CalculatorView.java             # Swing GUI implementation
│   └── CalculatorViewInterface.java    # View contract
└── presenter/
    └── CalculatorPresenter.java        # Event handling & coordination
```

## Key Components

### Model Layer (`model/`)
- **`CalculatorModel`**: Implements expression evaluation using Shunting Yard algorithm
- **`CalculatorModelInterface`**: Defines contract for calculation operations
- **`Operator`**: Enum with operator symbols, precedence, and associativity

**Key Methods:**
- `calculate(BigDecimal, String)` - Builds/appends to expression and evaluates on "="
- `evaluateExpression(String)` - Parses and evaluates mathematical expressions
- `getResult()` - Returns current calculation result

### View Layer (`view/`)
- **`CalculatorView`**: JFrame-based GUI with display and button grid
- **`CalculatorViewInterface`**: Contract for UI operations

**Key Methods:**
- `updateDisplay(String)` - Updates the calculator display
- `showError(String)` - Shows error dialog
- `getDisplayText()` - Gets current display content

### Presenter Layer (`presenter/`)
- **`CalculatorPresenter`**: Handles all user interactions and coordinates M-V

**Key Responsibilities:**
- Button click handling
- Keyboard input processing
- Input validation and error handling
- State management (start flag for input flow)

## Important Code Patterns & Conventions

### Input Handling
- **Start Flag**: Boolean `start` determines if input replaces or appends to display
- **Number Input**: Digits, decimal point, and parentheses are handled as number input
- **Operator Input**: `+ - * /` trigger calculations or expression building
- **Equals (=)**: Forces expression evaluation and result display

### Button Arrays
- `numberButtons`: 11 elements (0-9, decimal point, parentheses)
- `operationButtons`: 5 elements (+, -, *, /, =)
- Maintain these sizes when modifying the UI

### Expression Evaluation
- Uses **Shunting Yard algorithm** for parsing infix expressions
- **BigDecimal** for precision (eliminates floating-point errors)
- **Operator precedence**: `* /` before `+ -`
- **Parentheses support**: Proper nesting and evaluation

### Error Handling
- Division by zero: Throws `ArithmeticException`
- Invalid input: `NumberFormatException` handling
- User-friendly error dialogs via `View.showError()`

## Development Workflow

### Making Changes

**Model Changes:**
- Modify `CalculatorModel.java` for new calculation logic
- Update `Operator.java` for new operators
- Test expression evaluation thoroughly

**View Changes:**
- Modify `CalculatorView.java` for UI changes
- Update button arrays if adding/removing buttons
- Preserve interface contract

**Presenter Changes:**
- Modify `CalculatorPresenter.java` for new interactions
- Add keyboard bindings in `addKeyboardBindings()`
- Update event handling logic

### Adding Features

1. **New Operators**: Add to `Operator.java` with precedence
2. **New Buttons**: Update `CalculatorView` button arrays
3. **New Keyboard Shortcuts**: Add in `CalculatorPresenter.addKeyboardBindings()`
4. **New Calculations**: Extend `CalculatorModel.evaluateExpression()`

### Testing & Debugging

**Manual Testing:**
- Compile and run the application
- Test edge cases: division by zero, complex expressions, invalid input
- Verify keyboard and mouse input work identically

**Debug Logging:**
- All user actions are logged via `java.util.logging`
- Check console output for interaction traces
- Add temporary logging in suspicious areas

**IDE Debugging:**
- Set breakpoints in Presenter event handlers
- Step through Model evaluation logic
- Inspect BigDecimal values for precision issues

## Code Quality Guidelines

### Naming Conventions
- Interfaces: `CalculatorXInterface`
- Implementations: `CalculatorX`
- Methods: camelCase, descriptive names
- Variables: meaningful names, no abbreviations

### Exception Handling
- Catch specific exceptions (NumberFormatException, ArithmeticException)
- Show user-friendly error messages
- Log technical details for debugging

### Threading
- All Swing operations on EDT (Event Dispatch Thread)
- Use `SwingUtilities.invokeLater()` for initialization

## Common Pitfalls

1. **Package Issues**: Always compile from correct directory
2. **BigDecimal Precision**: Use appropriate rounding modes
3. **Expression Building**: Ensure proper spacing in expressions
4. **UI Threading**: All Swing updates must be on EDT
5. **Interface Contracts**: Maintain interface compatibility

## Future Enhancements

- Unit tests with mocking frameworks
- Scientific calculator functions
- Calculation history persistence
- Configurable UI themes
- Localization support
- Memory functions (M+, M-, MR, MC)

---

This architecture provides a solid foundation for extending the calculator with new features while maintaining clean separation of concerns and testability.
