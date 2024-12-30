package ie.atu.sw;

import java.util.Scanner;

/**
 * A validator class that handles text input validation against a set of valid
 * options.
 * This class implements the Validator interface for String input and Boolean
 * output.
 */
public class TextValidator implements Validator<String, Boolean> {
    /** The Scanner object used to read input from the user. */
    protected Scanner scanner;

    /**
     * Constructs a TextValidator with the specified Scanner.
     * 
     * @param scanner The Scanner object to use for reading input
     */
    TextValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Validates user input against a set of valid options.
     * Continuously prompts for input until a valid option is entered.
     * The input is converted to lowercase and trimmed before validation.
     *
     * @param showPrompt   The Prompter object used to display the prompt message
     * @param validOptions An array of valid string options to check against
     * @return true if the input matches one of the valid options, false otherwise
     */
    @Override
    public Boolean validate(Prompter showPrompt, String[] validOptions) {
        String input = "";
        boolean validInput = false;

        while (!validInput) {
            input = scanner.next().toLowerCase().trim();

            // Check if the input matches any of the valid options
            for (String option : validOptions) {
                if (input.equals(option)) {
                    validInput = true;
                    break;
                }
            }

            if (!validInput) {
                ConsoleLogger.error("Invalid input, please enter a valid option.");
                showPrompt.prompt();
            }
        }

        return validInput;
    }

}
