package data;

import helpers.Utils;
import models.UserModel;
import org.joda.time.DateTime;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  User: Reinier
 *  Class: The user table is the direct link to the database for users made with the application
 */
public class UserTable
{
    /**
     * class constructor, which seems empty my friend.
     */
    public UserTable() {

    }

    /**
     * Method to acquire UserId
     * @param username - fills in username
     * @param password - fills in password
     * this will aquire the userid through filling credentials
     * @return
     */
    public int getUserIdByCredentials(String username, String password)
    {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> results;

        String id;
        try {
            results = dbo.getObjectList("SELECT id FROM user WHERE email = ? AND password = ? LIMIT 1", username, Utils.sha1(password));
            id = "" + results.get(0).get("id");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return 0;
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }

        return Integer.parseInt(id);
    }

    /**
     * Method to acquire GroupId
     * @param id = id of the group in the database
     * @return
     */
    public int getGroupId(int id) {
        DatabaseObject dbo = new DatabaseObject();

        ArrayList<HashMap<String, Object>> results;
        results = dbo.getObjectList("SELECT group_id FROM user_view WHERE id=?", id);

        String group_id = "" + results.get(0).get("id");

        return Integer.valueOf(group_id);
    }

    /**
     * Method checks if user exists or not
     * @param username
     * @param password
     * @return
     */

    public Boolean userExists(String username, String password)
    {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> results;

        try {
            results = dbo.getObjectList("SELECT COUNT(*) as counted FROM user WHERE email = ? AND password = ?", username, Utils.sha1(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }

        String counted = "" + results.get(0).get("counted");

        if(Integer.parseInt(counted) > 0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    /**
     * Method bazinga
     * @param id
     * @return
     */

    public ArrayList<HashMap<String, Object>> getUserById(int id) {
        DatabaseObject dbo = new DatabaseObject();
        String sql;

        sql = "SELECT * FROM `user` WHERE id = ?";
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList(sql, id);

        return results;
    }

    /**
     * Method buhu
     * @param id
     * @return
     */
    public ArrayList<HashMap<String, Object>> getUserRightsByUserId(int id) {
        DatabaseObject dbo = new DatabaseObject();
        String sql;

        sql = "SELECT * FROM `new_user_right_view` WHERE user_id = ?";
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList(sql, id);

        return results;
    }

}