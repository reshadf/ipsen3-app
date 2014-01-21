package helpers;

/**
 * User: Jasper
 * Class: Easy way to switch screens, used singleton design pattern
 */

public class MainScreenSwitcher extends AbstractScreenSwitcher{

    private final static MainScreenSwitcher instance = new MainScreenSwitcher();

    /**
     * Call super constructor in constructor
     */
    private MainScreenSwitcher()
    {
        super();
    }

    /**
     * Create getInstance method for singleton
     */
    public static MainScreenSwitcher getInstance() {
        return instance;
    }

}
