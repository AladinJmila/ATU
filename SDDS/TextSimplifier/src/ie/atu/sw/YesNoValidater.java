package ie.atu.sw;

import java.util.Scanner;

/**
 * A validator class that handles yes/no input validation from users.
 * This class extends TextValidator and specifically validates user input
 * against yes/no responses (including variations like 'y' and 'n').
 */
public class YesNoValidater extends TextValidater {

    /**
     * Constructs a new YesNoValidator with the specified Scanner.
     *
     * @param scanner The Scanner object used to read user input
     */
    YesNoValidater(Scanner scanner) {
        super(scanner);
    }

    /**
     * Validates user input against the provided valid options.
     *
     * @param showPrompt   The Prompter object used to display prompts to the user
     * @param validOptions An array of String containing valid input options
     * @return Boolean Returns true for "yes"/"y" responses, false for other valid
     *         inputs
     */
    @Override
    public Boolean validate(Prompter showPrompt, String[] validOptions) {
        return validate(showPrompt, validOptions, "");
    }

    /**
     * Validates user input against the provided valid options with custom
     * indentation.
     * Continuously prompts the user until valid input is received.
     *
     * @param showPrompt   The Prompter object used to display prompts to the user
     * @param validOptions An array of String containing valid input options
     * @param tab          The string used for indentation in error messages
     * @return Boolean Returns true for "yes"/"y" responses, false for other valid
     *         inputs
     */
    public Boolean validate(Prompter showPrompt, String[] validOptions, String tab) {
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
                ConsoleLogger.error(tab, "Invalid input, please enter a valid option.");
                showPrompt.prompt();
            }
        }

        if (input.equals("yes") || input.equals("y")) {
            return true;
        }

        return false;
    }

}
