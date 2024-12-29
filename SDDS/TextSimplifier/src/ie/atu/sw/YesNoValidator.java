package ie.atu.sw;

import java.util.Scanner;

public class YesNoValidator extends TextValidator {

    YesNoValidator(Scanner scanner) {
        super(scanner);
    }

    @Override
    public Boolean validate(Prompter showPrompt, String[] validOptions) {
        return validate(showPrompt, validOptions, "");
    }

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

        return input.equals("yes") || input.equals("no") || input.equals("y") || input.equals("n");
    }

}
