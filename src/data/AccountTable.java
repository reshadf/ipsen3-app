package data;

import helpers.Utils;
import models.AccountModel;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: Reinier
 * Class: The account table is the direct link to the database for accounts
 */
public class AccountTable
{     private String tableName;
    private ArrayList<String> config = null;

    public AccountTable() {
        this.tableName = "user";
    }

    public ArrayList<HashMap<String, Object>> getAccount() {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList("SELECT * FROM "+this.tableName);

        return results;
    }

    /**
     * Returns the group_Id from a user_Id
     * @param accountId
     * @return
     */
    public  int getAccountRightGroup(int accountId)
    {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> result = dbo.getObjectList("SELECT group_id FROM `user_group` WHERE user_id = ? ", accountId);

        int something = 0;
        if(result.size() > 0){
        something = Integer.valueOf(""+result.get(0).get("group_id"));

        }

        return something;
    }


    public boolean writeChangesFor( AccountModel model)  {
        DatabaseObject dbo = new DatabaseObject();
        String sql;
        Object result = null;

        try{
        if(model.getId() == 0) {
            sql = "INSERT INTO "+this.tableName+" " +
                    "(id, firstname, insertion, lastname, email, password)" +
                    " VALUES " +
                    "(null, ?, ?, ?, ?, ?)";
            result = dbo.getUpdateOrInsertResultFor(sql,
                    model.getFirstname(),
                    model.getInsertion(),
                    model.getLastname(),
                    model.getEmail(),
                    Utils.sha1(model.getPassword()));
        }
        else {
            if(model.getPassword().length() > 1)
            {
                sql = "UPDATE "+this.tableName+" SET " +
                        " firstname=?, insertion=?, lastname=?, email=?, password=?" +
                        " WHERE " +
                        "id=?";
                result = dbo.getUpdateOrInsertResultFor(sql,
                        model.getFirstname(),
                        model.getInsertion(),
                        model.getLastname(),
                        model.getEmail(),
                        Utils.sha1(model.getPassword()),
                        model.getId()
                );
            }
            else
            {
                sql = "UPDATE "+this.tableName+" SET " +
                        " firstname=?, insertion=?, lastname=?, email=?" +
                        " WHERE " +
                        "id=?";
                result = dbo.getUpdateOrInsertResultFor(sql,
                        model.getFirstname(),
                        model.getInsertion(),
                        model.getLastname(),
                        model.getEmail(),
                        model.getId()
                );
            }

        }
        }catch (NoSuchAlgorithmException e)
        {

        }
        return (result.equals(1)) ? true : false;
    }


    public boolean writeChangesForUserGroup(AccountModel model)
    {
        DatabaseObject dbo = new DatabaseObject();
        String sql;
        Object result = null;

        sql = "INSERT INTO `user_group` (`user_id`, `group_id`) VALUES (?, ?) ON DUPLICATE KEY UPDATE `group_id` = ?";
        result = dbo.getUpdateOrInsertResultFor(sql,
                model.getId(),
                model.getRightGroupId(),
                model.getRightGroupId());


        return (result.equals(1)) ? true : false;

    }

    public int getLastInsertId()
    {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> result = dbo.getObjectList("SELECT `id` FROM `user` ORDER BY `id` DESC LIMIT 1");

        int id = 0;
        if(result.size() > 0){
            id = Integer.valueOf(""+result.get(0).get("id"));
        }

        System.out.println("inserted id: "+id);

        return id;
    }

    public boolean deleteObject(AccountModel model) {
        DatabaseObject dbo = new DatabaseObject();
        String sql;
        sql = "DELETE FROM "+this.tableName+" " +
                "WHERE id=?";
        Object result;
        result = dbo.getUpdateOrInsertResultFor(sql, model.getId());

        return (result.equals(1)) ? true : false;
    }
}

