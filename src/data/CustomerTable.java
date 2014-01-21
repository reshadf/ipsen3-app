package data;

import models.CustomerModel;
import models.VehicleModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: Jasper
 * Class: The customer table is the direct link to the database for customerinfo
 */

public class CustomerTable {

    private String tableName; //name of the table in the database

    /**
     *
     * Contstructor, this sets the table name
     *
     */

    public CustomerTable() {
        this.tableName = "customer"; //we are a customer and we like it
    }

    /**
     * Get a list of customers
     *
     * @return list of customers
     */

    public ArrayList<HashMap<String, Object>> getCustomers() {
        DatabaseObject dbo = new DatabaseObject(); //create a database object
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList("SELECT * FROM "+this.tableName); //select all columns from the table

        return results;
    }

    /**
     * Convert a Dutch formatted date to an American formatted date
     * @param dutchDate
     * @return formatted-date
     */

    public String convertToAmericanDate(String dutchDate)
    {
        String[] exploded;
        exploded = dutchDate.split("-");

        return exploded[2] + "-" + exploded[1] + "-" + exploded[0]; //return formatted date, yeah science bitch!
    }

    /**
     * Update or Insert customer model into the database
     *
     * @param model
     * @return boolean true if result is successful otherwise return false
     */

    public boolean writeChangesFor(CustomerModel model) {
        DatabaseObject dbo = new DatabaseObject(); //create database object
        String sql; //SQL string

        Object result; //result
        if(model.getId() == 0) {
            sql = "INSERT INTO "+this.tableName+" " +
                    "(id, email, firstname, insertion, lastname, company, kvknr, phone, address, zip, city, birthdate, passportnumber)" +
                    " VALUES " +
                    "(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; //create safe query
            result = dbo.getUpdateOrInsertResultFor(sql,
                    model.getEmail(),
                    model.getFirstname(),
                    model.getInsertion(),
                    model.getLastname(),
                    model.getCompany(),
                    model.getKvknr(),
                    model.getPhone(),
                    model.getAddress(),
                    model.getZip(),
                    model.getCity(),
                    convertToAmericanDate(model.getBirthdate()),
                    model.getPassportnumber()); //fill all questionmarks
        }
        else {
            sql = "UPDATE "+this.tableName+" SET " +
                    "email=?, firstname=?, insertion=?, lastname=?, company=?, kvknr=?, phone=?, address=?, zip=?, city=?, birthdate=?, passportnumber=?" +
                    " WHERE " +
                    "id=?"; //create query
            result = dbo.getUpdateOrInsertResultFor(sql,
                    model.getEmail(),
                    model.getFirstname(),
                    model.getInsertion(),
                    model.getLastname(),
                    model.getCompany(),
                    model.getKvknr(),
                    model.getPhone(),
                    model.getAddress(),
                    model.getZip(),
                    model.getCity(),
                    convertToAmericanDate(model.getBirthdate()),
                    model.getPassportnumber(),
                    model.getId()); //fill questionmarks
        }

        return (result.equals(1)) ? true : false; //return state
    }

    /**
     *
     * Delete customer object
     *
     * @param model
     * @return true or false
     */

    public boolean deleteObject(CustomerModel model) { //in case we hate the customer
        DatabaseObject dbo = new DatabaseObject(); //create database object
        String sql; //SQL String
        sql = "DELETE FROM "+this.tableName+" " +   "WHERE id=?"; //delete query
        Object result;
        result = dbo.getUpdateOrInsertResultFor(sql, model.getId());

        return (result.equals(1)) ? true : false; //return state
    }
}