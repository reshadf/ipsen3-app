package helpers.combobox;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

/**
 * User: Chris
 * Class: makes it able for box to render properly ofcourse
 */
public class ItemRenderer extends BasicComboBoxRenderer
{
    /**
     * Will use components to make the said cells
     * @param list
     * @param value
     * @param index
     * @param isSelected
     * @param cellHasFocus
     * @return
     */
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value != null)
        {
            Item item = (Item)value;
            setText( item.getDescription() );
        }

        if (index == -1)
        {
            Item item = (Item)value;
            setText( "" + item.getDescription() );
        }

        return this;
    }
}
