import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SmartCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private String currentInput = "";
    private String operator = "";
    private double num1 = 0;
    private boolean isOperatorClicked = false;

    public SmartCalculator() {
        setTitle("Smart Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display field
        display = new JTextField();
        display.setFont(new Font("Arial", Font.PLAIN, 30));
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        add(display, BorderLayout.NORTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        // Add buttons
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "⌫" // Clear and Backspace
        };

        for (String buttonText : buttons) {
            JButton button = new JButton(buttonText);
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            button.setFocusPainted(false);
            button.setBackground(Color.LIGHT_GRAY);
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        // Center the window
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Handle number or decimal point input
        if (command.matches("[0-9.]")) {
            if (isOperatorClicked) {
                currentInput = "";
                isOperatorClicked = false;
            }
            currentInput += command;
            display.setText(currentInput);
        }

        // Handle operator input
        else if (command.matches("[/*+-]")) {
            if (!currentInput.isEmpty()) {
                num1 = Double.parseDouble(currentInput);
                operator = command;
                isOperatorClicked = true;
            }
        }

        // Handle equal (=) button
        else if (command.equals("=")) {
            double num2 = !currentInput.isEmpty() ? Double.parseDouble(currentInput) : 0;
            try {
                double result = performOperation(num1, num2, operator);
                display.setText(Double.toString(result));
                currentInput = Double.toString(result);  // Store the result as the new input
                operator = "";
            } catch (ArithmeticException ex) {
                display.setText("Error");
            }
        }

        // Handle clear (C) button
        else if (command.equals("C")) {
            currentInput = "";
            operator = "";
            num1 = 0;
            display.setText("");
        }

        // Handle backspace (⌫) button
        else if (command.equals("⌫")) {
            if (!currentInput.isEmpty()) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
                display.setText(currentInput);
            }
        }
    }

    // Perform arithmetic operations
    private double performOperation(double num1, double num2, String operator) throws ArithmeticException {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return num1 / num2;
            default:
                return num2;  // If no operator, return the second number
        }
    }

    public static void main(String[] args) {
        // Set the look and feel to make the UI more material-like
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start the calculator
        SwingUtilities.invokeLater(SmartCalculator::new);
    }
}
