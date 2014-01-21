package data;

import models.CategoryModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User:  Floris
 * Class: The Category table is the direct link to the database for categories
 */
public class CategoryTable {


    private String tableName = "category";
    private ArrayList<String> config = null;

    public ArrayList<HashMap<String, Object>> getCategories() {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList("SELECT * FROM category");

        return results;
    }


//    public void writeChangesFor(CategoryModel model) {
//        DatabaseObject dbo = new DatabaseObject();
//        String sql;
//
//        if(model.getId() == 0){
//            sql = "INSERT INTO "+this.tableName+" " +
//                    "(id, name, description)" +
//                    " VALUES " +
//                    "(null, ?, ?)";
//        }
//        else{
//            sql = "UPDATE category SET " +
//                    "name=?, description=?" +
//                    " WHERE " +
//                    "id=?";
//        }
//
//
//        Object result;
//        result = dbo.getUpdateOrInsertResultFor(sql,
//                model.getName(),
//                model.getDescription()
//
//                );
//
//    }

    public boolean writeChangesFor(CategoryModel model) {
        DatabaseObject dbo = new DatabaseObject();
        String sql;

        Object result;
        if(model.getId() == 0) {
            System.out.println("insert table");
            sql = "INSERT INTO category " +
                    "(id, name, description)" +
                    " VALUES " +
                    "(null, ?, ?)";
            result = dbo.getUpdateOrInsertResultFor(sql,
                    //model.getId(),
                   // model.getDate(),
                    model.getName(),
                    model.getDescription());
        }
        else {
            System.out.println("update table");
            sql = "UPDATE category SET " +
                    "name=?, description=?" +
                    " WHERE " +
                    "id=?";
            result = dbo.getUpdateOrInsertResultFor(sql,
                    model.getName(),
                    model.getDescription(),
                    model.getId());

        }

        return (result.equals(1)) ? true : false;
    }


    public boolean deleteObject(CategoryModel model) {
        DatabaseObject dbo = new DatabaseObject();
        String sql;
        sql = "DELETE FROM "+this.tableName+" " +
                "WHERE id=?";
        Object result;
        result = dbo.getUpdateOrInsertResultFor(sql, model.getId());

        return (result.equals(1)) ? true : false;
    }

}
