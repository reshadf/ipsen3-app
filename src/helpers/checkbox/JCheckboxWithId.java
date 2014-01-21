package helpers.checkbox;

import javax.swing.*;

/**
 * User: Jasper
 * Class: MOARRR CHECKBOXAS BRO
 */

public class JCheckboxWithId extends JCheckBox
{
    private int id;

    /**
     *
     * Constructor with id for checkbox
     *
     * @param id
     * @param value
     */

    public JCheckboxWithId(int id, String value)
    {
        super(value);
        this.id = id;
    }

    /**
     *
     * Overload, addes option for selected or not
     *
     * @param id
     * @param value
     * @param selected
     */

    public JCheckboxWithId(int id, String value, boolean selected)
    {
        super(value, selected);
        this.id = id;
    }

    /**
     *
     * Get the set ID
     *
     * @return
     */

    public Object getId()
    {
        return this.id;
    }

}
