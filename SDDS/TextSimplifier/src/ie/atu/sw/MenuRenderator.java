package ie.atu.sw;

/**
 * An interface that defines the contract for classes that render menus.
 */
public interface MenuRenderator {
    /**
     * A constant representing a tab character, used for formatting menu output.
     */
    String tab = ConsoleLogger.TAB;

    /**
     * Renders the menu to the user.
     * This method should be implemented to display all available menu options
     * in a user-friendly format.
     */
    public void renderMenu();
}
