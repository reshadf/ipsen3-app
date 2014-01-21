package helpers;

import javax.swing.*;
import java.awt.*;

/**
 * User: Jasper
 * Class: Easy way to switch screens, abstract class for inheritence
 */

abstract class AbstractScreenSwitcher extends JPanel{

    private CardLayout cardLayout = new CardLayout();
    private JPanel currentScreen;

    /**
     * Private constructor, sets layout to cardlayout
     */
    public AbstractScreenSwitcher()
    {
        this.setLayout(cardLayout);
    }

    /**
     * Method that can be used to switch the screen
     */
    public void switchScreen(JPanel panel)
    {
        currentScreen = panel;
        String name = panel.getClass().toString();
        String[] name2 = name.split("class ");

        this.add(panel, name2[1]);
        this.cardLayout.show(this, name2[1]);
    }
    /**
     * Get current screen
     */
    public JPanel getCurrentScreen() {
        return currentScreen;
    }

}
