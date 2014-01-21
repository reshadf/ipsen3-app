package data;

import models.RightGroupModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: Reinier
 * Class: The rightgrouptable is the connector to the database for the rightgroups and helps inserting information
 */

public class RightGroupTable
{
    private String tableName;
    private ArrayList<String> config = null;

    /**
     *
     * Constructor that defines the table to use
     *
     */

    public RightGroupTable()
    {
        this.tableName = "group";
    }


    /**
     *
     * Select all rightgroups
     *
     * @return results
     */

    public ArrayList<HashMap<String, Object>> getRightGroups()
    {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList("SELECT * FROM `" + this.tableName + "`");

        return results;
    }

    public ArrayList<HashMap<String, Object>> getRightGroupsNames()
    {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList("SELECT id, name FROM `" + this.tableName + "`");

        return results;
    }



    /**
     *
     * Write changes for item
     *
     * @param model
     * @return
     */

    public boolean writeChangesFor( RightGroupModel model )
    {
        DatabaseObject dbo = new DatabaseObject();
        String sql;

        Object result;
        if (model.getId() == 0) //id is 0 this means it is a new item
        {
            sql = "INSERT INTO `" + this.tableName + "` " +
                    "(id, name, description)" +
                    " VALUES " +
                    "(null, ?, ?)";
            result = dbo.getUpdateOrInsertResultFor( sql,
                    model.getName(),
                    model.getDescription() );
        } else { //edit
            sql = "UPDATE `" + this.tableName + "` SET " +
                    "name=?, description=?" +
                    " WHERE " +
                    "id=?";
            result = dbo.getUpdateOrInsertResultFor( sql,
                    model.getName(),
                    model.getDescription(), model.getId() );
        }

        return ( result.equals( 1 ) ) ? true : false;
    }

    /**
     *
     * Delete object
     *
     * @param model
     * @return
     */

    public boolean deleteObject( RightGroupModel model )
    {
        DatabaseObject dbo = new DatabaseObject();
        String sql;
        sql = "DELETE FROM `" + this.tableName + "` " +
                "WHERE id=?";
        Object result;
        result = dbo.getUpdateOrInsertResultFor( sql, model.getId() );

        return ( result.equals( 1 ) ) ? true : false;
    }
}

