package models;
import views.AccessRightView;

/**
 * User: Reinier Koops
 * Class: This class checks the right group model
 */
public class Right2GroupModel extends IpsenModel
{
    private AccessRightView view;
    private int id = 0;
    private int right_id = 0;
    private int group_id = 0;

    /**
     * Getter for id
     * @return id
     */
    public int getId()
    {
        return id;
    }
    /**
     * Setter for id
     * @return id
     */

    public void setId(int id)
    {
        this.id = id;
    }
    /**
     * Getter for right_id
     * @return right_id
     */
    public int getRightId() {
        return right_id;
    }
    /**
     * Setter for right_id
     * @return right_id
     */
    public void setRightId(int right_id) {
        this.right_id = right_id;
    }
    /**
     * Getter for group_id
     * @return group_id
     */
    public int getGroupId() {
        return group_id;
    }
    /**
     * Setter for group_id
     * @return group_id
     */
    public void setGroupId(int group_id) {
        this.group_id = group_id;
    }

    /**
     * Shows view as a superclass
     * @param view
     */

    public Right2GroupModel(AccessRightView view)
    {
        super();
        this.view = view;
    }

    public Object validate(Object key, Object value)
    {
        return true;
    }


}
