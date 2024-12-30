package ie.atu.sw;

import java.util.Scanner;

/**
 * A validator class that handles integer input validation within specified
 * ranges.
 */
public class IntegerValidater implements Validator<Integer, Integer> {
    /** The scanner object used to read user input */
    private Scanner scanner;

    /**
     * Constructs an IntegerValidator with the specified Scanner.
     * 
     * @param scanner The Scanner object to read input from
     */
    IntegerValidater(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Validates integer input without a tab prefix.
     * 
     * @param showPrompt The prompter object to display messages
     * @param range      An array containing the minimum (index 0) and maximum
     *                   (index 1) allowed values
     * @return The validated integer within the specified range
     */
    @Override
    public Integer validate(Prompter showPrompt, Integer[] range) {
        return validate(showPrompt, range, "");
    }

    /**
     * Validates numeric input from the user within a specified range.
     * Continues to prompt for input until a valid integer within the range is
     * provided.
     * 
     * @param showPrompt The prompter object to display messages
     * @param range      An array containing the minimum (index 0) and maximum
     *                   (index 1) allowed values
     * @param tab        A string prefix used for formatting console output
     * @return The validated integer within the specified range
     */
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
