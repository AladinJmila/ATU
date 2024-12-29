package ie.atu.sw;

import java.util.Scanner;

public class TextValidator implements Validator<String> {
    private Scanner scanner;

    TextValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String validate(Prompter showPrompt, String[] validOptions) {
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

        return input;
    }

}
