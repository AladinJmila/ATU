package ie.atu.sw;

import java.util.Scanner;

/**
 * A validator class that handles double precision floating point input
 * validation.
 */
public class DoubleValidator implements Validator<Double, Double> {

    /** The Scanner object used to read input from the user */
    private Scanner scanner;

    /**
     * Constructs a new DoubleValidator with the specified Scanner.
     * 
     * @param scanner The Scanner object to read input from
     */
    DoubleValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Validates double input without a tab prefix.
     * 
     * @param showPrompt The prompter object to display prompts to the user
     * @param range      An array containing the minimum (index 0) and maximum
     *                   (index 1) allowed values
     * @return The validated double value within the specified range
     */
    @Override
    public Double validate(Prompter showPrompt, Double[] range) {
        return validate(showPrompt, range, "");
    }

    /**
     * Validates numeric input from the user within a specified range with a custom
     * tab prefix.
     * Continuously prompts the user until valid input is received.
     *
     * @param showPrompt The prompter object to display prompts to the user
     * @param range      An array containing the minimum (index 0) and maximum
     *                   (index 1) allowed values
     * @param tab        The string to use as a tab/indent prefix for error messages
     * @return The validated double value within the specified range
     */
    public Double validate(Prompter showPrompt, Double[] range, String tab) {
        Double input = 0.0;
        boolean validInput = false;

        // Loop until a valid input is provided
        while (!validInput) {
            try {
                input = Double.parseDouble(scanner.next());

                // Check if the input is within the specified range
                if (input < range[0] || input > range[1]) {
                    ConsoleLogger.error(tab, "Input out of range, please enter a valid number.");
                    showPrompt.prompt();
                } else {
                    validInput = true;
                }

            } catch (NumberFormatException e) {
                ConsoleLogger.error(tab, "Invalid input, please enter a valid number.");
                showPrompt.prompt();
            }

        }

        return input;
    }
}
