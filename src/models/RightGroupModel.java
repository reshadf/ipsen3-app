package models;

import views.AccessRightGroupView;

/**
 * User: Reinier
 * Class: Model, inserting and getting information to create a model for the factory
 */
public class RightGroupModel extends IpsenModel
{
    private AccessRightGroupView view;
    private int id = 0;
    private String name = "";
    private String description = "";

    /**
     *
     * Get database id
     *
     * @return id
     */
    public int getId()
    {
        return this.id;
    }

    /**
     *
     * Set id for the item
     *
     * @param id
     */

    public void setId(int id)
    {
        this.id = id;
    }

    /**
     *
     * Return the groupname
     *
     * @return name
     */

    public String getName()
    {
        return this.name;
    }

    /**
     * Set groupname
     *
     * @param name
     */

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     *
     * Return group description
     *
     * @return description
     */

    public String getDescription()
    {
        if(!this.description.equals("null"))
        {
            return this.description;
        }
        else
        {
            return "";
        }
    }

    /**
     *
     * Set groupdescription
     *
     * @param value
     */

    public void setDescription(String value)
    {
        this.description = value;
    }

    /**
     *
     * Constructor
     *
     * @param view
     */
    public RightGroupModel(AccessRightGroupView view)
    {
        super();
        this.view = view;
    }

    /**
     *
     * Validate if object is valid
     *
     * @param key
     * @param value
     * @return
     */

    public Object validate(Object key, Object value)
    {
        return true;
    }
}