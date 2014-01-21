package helpers;

import data.UserTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: Jasper
 * Class: ACL OMG Alex this is to much awesome...
 */
public class ACL
{
    private final static ACL instance = new ACL();

    private List<String> userRights = new ArrayList<String>();
    private int userId = 0;

    /**
     * Call super constructor in constructor
     */
    private ACL() { }

    /**
     * Set right list
     */
    public void setRights(int userId)
    {
        this.userId = userId;

        UserTable userTable = new UserTable();

        ArrayList<HashMap<String, Object>> tempRights = userTable.getUserRightsByUserId(userId);

        userRights.clear();

        for(int i = 0; i < tempRights.size(); i++)
        {
            userRights.add("" + tempRights.get(i).get("value"));
        }
    }

    /**
     * Check if user has permission for application part
     */
    public boolean hasPermissionFor(String value)
    {
        if(userRights.contains(value))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Create getInstance method for singleton
     */
    public static ACL getInstance() {
        return instance;
    }

    /**
     *
     * Return the user id of the logged in user
     *
     * @return
     */

    public int getUserId()
    {
        return this.userId;
    }
}
