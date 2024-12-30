package ie.atu.sw;

import static java.lang.System.out;

/**
 * This class is responsible for rendering the main menu of the application
 * It implements the MenuRenderator interface.
 */
public class MainMenuRenderer implements MenuRenderator {
    /** Flag to determine if it's the first time rendering the menu */
    private boolean isFirstRun = true;

    /**
     * Renders the main menu of the application.
     * On the first run, it displays a welcome banner.
     * Subsequently, it shows the menu options for the user to choose from.
     */
    @Override
    public void renderMenu() {
        out.println();
        out.print(ConsoleColour.RESET);
        if (isFirstRun) {
            out.print(ConsoleColour.YELLOW_BOLD);
            out.println("************************************************************");
            out.print("*     ");
            out.print(ConsoleColour.CYAN_BOLD);
            out.print("ATU - Dept. of Computer Science & Applied Physics");
            out.print(ConsoleColour.YELLOW_BOLD);
            out.println("    *");
            out.println("*                                                          *");
            out.print("*             ");
            out.print(ConsoleColour.CYAN_UNDERLINED);
            out.print("Virtual Threaded Text Simplifier");
            out.print(ConsoleColour.RESET);
            out.print(ConsoleColour.YELLOW_BOLD);
            out.println("             *");
            out.println("*                                                          *");
            out.println("************************************************************");
        }

        ConsoleLogger.cyanBoldTitle(" Main Menu:", true);
        out.println("---------------------------------------------------");
        out.println("| 1 | Specify Embeddings File");
        out.println("| 2 | Specify Google 1000 File");
        out.println("| 3 | Specify an Output Path (default: ./)");
        out.println("| 4 | Simplify my input");
        out.println("| 5 | Configure Options");
        out.println("| 6 | Quit");
        out.println("---------------------------------------------------");
        ConsoleLogger.cyanBoldTitle("Select Option [1-6]> ");

        isFirstRun = false;
    }
}
