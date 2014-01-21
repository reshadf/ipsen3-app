package helpers.combobox;

/**
 * User: Chris
 * Class: for itemrenderer
 */
public class Item
{
    private int id;
    private String description;

    /**
     * create item
     * @param id  shows id
     * @param description shows description
     */
    public Item(int id, String description)
    {
        this.id = id;
        this.description = description;
    }

    /**
     * Getter for id
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Getter for description
     * @return description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Getter for string
     * @return description
     */

    public String toString()
    {
        return description;
    }
}
