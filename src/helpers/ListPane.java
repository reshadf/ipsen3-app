package helpers;

import javax.swing.*;
import java.awt.*;

/**
 * User: Jasper
 * Class: Scrolling and such
 */

public class ListPane extends JPanel implements Scrollable {

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return new Dimension(200, 200);
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 128;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 128;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

}