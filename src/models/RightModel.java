package models;
import views.AccessRightView;
/**
 * User: Reinier
 * Class: Model, inserting and getting information to create a model for the factory
 */
public class RightModel extends IpsenModel
{
    private AccessRightView view;
    private int id = 0;
    private String description = "";
    private String value = "";

    /**
     * Getter and setter for id
     * @return the id
     */
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Getter and setter for description
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Getter and setter for value
     * @return the value
     */
    public String getValue()
    {
        return value;
    }
    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * Superclass which appoints the view for this class
     */
    public RightModel(AccessRightView view)
    {
        super();
        this.view = view;
    }

    /**
     * validates
     */

    public Object validate(Object key, Object value)
    {
        return true;
    }


}
