package data;

import models.Right2GroupModel;
import models.RightModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: Reinier
 * Class: The right table makes it able to put the objects into the database
 */
public class RightTable
{     private String tableName;
    private ArrayList<String> config = null;

    public RightTable() {
        this.tableName = "right";
    }

    /**
     *
     * Get all rights from the database
     *
     * @return
     */

    public ArrayList<HashMap<String, Object>> getRights() {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList("SELECT * FROM `" + this.tableName + "`");

        return results;
    }

    /**
     *
     * Get assigned rights for usergroup
     *
     * @param id
     * @return
     */

    public ArrayList<HashMap<String, Object>> getAssignedRights(int id) {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList("SELECT * FROM `right_group` WHERE group_id = ?", id);

        return results;
    }

    /**
     *
     * Insert all rights in database for group, if already exsists just update
     *
     * @param groupId
     * @param checkedItems
     */

    public void updateRights(int groupId, ArrayList<Integer> checkedItems) {
        DatabaseObject dbo = new DatabaseObject();

        for(Integer item : checkedItems)
        {
            dbo.getUpdateOrInsertResultFor("INSERT INTO `right_group` (right_id, group_id) VALUES (?, ?) ON DUPLICATE KEY UPDATE right_id = ?, group_id = ?", item, groupId, item, groupId);
        }
    }

    /**
     *
     * Remove rights from usergroup
     *
     * @param groupId
     * @param uncheckedItems
     */

    public void removeRights(int groupId, ArrayList<Integer> uncheckedItems) {
        DatabaseObject dbo = new DatabaseObject();

        for(Integer item : uncheckedItems)
        {
            dbo.getUpdateOrInsertResultFor("DELETE FROM `right_group` WHERE right_id = ? AND group_id = ?", item, groupId);
        }
    }
}
