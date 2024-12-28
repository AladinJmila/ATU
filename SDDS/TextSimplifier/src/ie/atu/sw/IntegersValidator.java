package ie.atu.sw;

import java.util.Scanner;

public class IntegersValidator implements Validator<Integer> {
    private Scanner scanner;

    IntegersValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Integer validate(Prompter showPrompt, Integer[] range) {
        return validate(showPrompt, range, "");
    }

    // Validates numeric input from the user within a specified range with a tab.
    public Integer validate(Prompter showPrompt, Integer[] range, String tab) {
        int input = 0;
        boolean validInput = false;

        // Loop until a valid input is provided
        while (!validInput) {
            try {
                input = Integer.parseInt(scanner.next());

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
