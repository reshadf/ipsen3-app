package helpers;

/**
 * User: Jasper
 * Class: Easy way to switch screens, used singleton design pattern
 */

public class PageScreenSwitcher extends AbstractScreenSwitcher{

    private final static PageScreenSwitcher instance = new PageScreenSwitcher();

    /**
     * Call super constructor in constructor
     */
    private PageScreenSwitcher()
    {
        super();
    }

    /**
     * Create getInstance method for singleton
     */
    public static PageScreenSwitcher getInstance() {
        return instance;
    }

}
