package models;

import data.UserTable;

/**
 * User: Jasper
 * Class: Model, inserting and getting information to create a model for the factory
 */
public class LoginModel extends IpsenModel {

    private UserTable userTable;

    public LoginModel()
    {
        userTable = new UserTable();
    }
    /**
     * Get user
     * @param username, password
     * @return check
     */

    public int getUserIdByCredentials(String username, String password)
    {
        return this.userTable.getUserIdByCredentials(username, password);
    }
    /**
     * Validate login
     * @param username, password
     * @return check - return true or false
     */
    public Boolean login(String username, String password)
    {
        return userTable.userExists(username, password);
    }
    /**
     * Validate login
     * @param username
     */

    public int getUserId(String username, String password) {
        return userTable.getUserIdByCredentials(username, password);
    }
    /**
     * Validate group
     * @param id
     */

    public int getGroupId(int id) {
        return userTable.getGroupId(id);
    }
}
