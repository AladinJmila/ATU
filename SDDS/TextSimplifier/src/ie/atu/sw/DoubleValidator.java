package ie.atu.sw;

import java.util.Scanner;

public class DoubleValidator implements Validator<Double, Double> {

    private Scanner scanner;

    DoubleValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Double validate(Prompter showPrompt, Double[] range) {
        return validate(showPrompt, range, "");
    }

    // Validates numeric input from the user within a specified range with a tab.
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
