package ie.atu.sw;

import static java.lang.System.out;

/**
 * A class that renders the options configuration menu for the application.
 * It implements the MenuRenderator interface.
 */
public final class OptionsMenuRenderer implements MenuRenderator {

    /**
     * Renders the options configuration menu to the console.
     */
    @Override
    public void renderMenu() {
        ConsoleLogger.cyanBoldTitle(tab + " Configure Options Menu: ", true);
        out.println(tab + "-----------------------------------------------------------------------------------------");
        out.println(tab + "| 1 | Specify the tolerance level");
        out.println(tab + "| 2 | Launch file automatically when processing completes");
        out.println(tab + "| 3 | Return to the Main Menu");
        out.println(tab + "-----------------------------------------------------------------------------------------");
        ConsoleLogger.cyanBoldTitle(tab + "Select Option [1-3]> ");
    }

}
