package ie.atu.sw;

import static java.lang.System.out;

public final class OptionsMenuRenderer implements MenuRenderator {

    @Override
    public void renderMenu() {
        ConsoleLogger.cyanBoldTitle(tab + " Configure Options Menu: ", true);
        out.println(tab + "-----------------------------------------------------------------------------------------");
        out.println(tab + "| 1 | Specify the tolerance level");
        out.println(tab + "| 2 | Return to the Main Menu");
        out.println(tab + "-----------------------------------------------------------------------------------------");
        ConsoleLogger.cyanBoldTitle(tab + "Select Option [1-5]> ");
    }

}
