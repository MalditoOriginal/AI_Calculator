# Simple Calculator

A professional Java Swing calculator application built with MVP (Model-View-Presenter) architecture, featuring expression evaluation with parentheses support and precise BigDecimal calculations.

## Features

### Core Functionality
- ✅ Basic arithmetic operations (+, -, *, /)
- ✅ Parentheses support for complex expressions
- ✅ BigDecimal precision (no floating-point errors)
- ✅ Expression evaluation using Shunting Yard algorithm
- ✅ Backspace editing during input

### User Interface
- 🎨 Clean Swing GUI with standard calculator layout
- ⌨️ Full keyboard support (numbers, operators, Enter, Backspace)
- 🖱️ Mouse click support for all buttons
- 📱 Error dialogs for invalid operations

### Advanced Features
- 🔢 Support for negative numbers
- 📝 Decimal number input
- 🎯 Proper operator precedence
- 🚫 Division by zero protection
- 📊 Comprehensive logging for debugging
- 🚨 User-friendly error messages
- 🔍 Expression validation and syntax checking

## Project Structure

```
SimpleCalculator/
├── README.md                              # This documentation
├── src/main/java/com/simplecalculator/
│   ├── main/
│   │   └── Calculator.java                # Application entry point
│   ├── model/
│   │   ├── CalculatorModel.java           # Business logic & calculations
│   │   ├── CalculatorModelInterface.java  # Model contract
│   │   └── Operator.java                  # Operator enum with precedence
│   ├── view/
│   │   ├── CalculatorView.java            # GUI implementation
│   │   └── CalculatorViewInterface.java   # View contract
│   └── presenter/
│       └── CalculatorPresenter.java       # User interaction handling
└── *.class files                          # Compiled bytecode (generated)
```

## Architecture

### MVP Pattern
- **Model**: Handles all calculation logic and data
- **View**: Passive GUI components, displays data only
- **Presenter**: Processes user input, coordinates Model and View

### Design Principles
- 🔌 Interface-based design for loose coupling
- 🎯 Single Responsibility Principle
- 🧪 Testable architecture with separated concerns
- 📦 Proper package organization

## Usage Examples

### Basic Calculations
```
2 + 3 =     → 5
10 - 4 =    → 6
6 * 7 =     → 42
8 / 2 =     → 4
```

### Complex Expressions
```
(2 + 3) * 4 =     → 20
10 / (2 + 3) =    → 2
2 * (3 + 4) - 1 = → 13
```

### Decimal Calculations
```
1.5 + 2.25 =   → 3.75
10.0 / 3.0 =   → 3.3333333333 (BigDecimal precision)
```

## Building and Running

### Prerequisites
- Java 8 or higher
- Java Development Kit (JDK)

### Compile
```bash
cd SimpleCalculator/src/main/java
javac com/simplecalculator/main/Calculator.java
```

### Run
```bash
java com.simplecalculator.main.Calculator
```

## Technical Details

### Expression Evaluation
Uses the **Shunting Yard algorithm** by Edsger Dijkstra for parsing mathematical expressions:
- Handles operator precedence (* / before + -)
- Supports parentheses for grouping
- Converts infix notation to postfix for evaluation

### Precision
- Uses `java.math.BigDecimal` instead of `double`
- Eliminates floating-point precision errors
- Configurable decimal precision (currently 10 digits)

### Error Handling
- Division by zero detection
- Invalid number format validation
- Arithmetic exception handling
- User-friendly error dialogs

## Development

### Code Quality
- Comprehensive logging with `java.util.logging`
- Input validation and sanitization
- Exception handling with meaningful messages
- Clean separation of concerns

### Testing
The MVP architecture enables easy unit testing:
- Mock View interface for Presenter testing
- Mock Model interface for View testing
- Isolated business logic testing

### Extensibility
Easy to add new features:
- Additional operators (%, ^, √)
- Scientific notation
- Memory functions (M+, M-, MR, MC)
- History of calculations
- Different UI themes

## Dependencies

- **Java Standard Library**: Swing (GUI), BigDecimal (precision), Logging
- **No external dependencies** - pure Java implementation

## License

This project is open source and available under the MIT License.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## Future Enhancements

- [ ] Scientific calculator functions (sin, cos, tan, log)
- [ ] Unit conversion capabilities
- [ ] Calculation history with undo/redo
- [ ] Configurable themes and layouts
- [ ] Export calculation results
- [ ] Keyboard shortcuts customization
