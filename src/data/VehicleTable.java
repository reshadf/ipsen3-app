package data;

import models.VehicleModel;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: Reinier
 * Class: The vehicle table is the direct link to the database for vehicles
 */
public class VehicleTable {

    private String tableName;
    private ArrayList<String> config = null;

    /**
     * Class Constructor
     */
    public VehicleTable() {
        this.tableName = "vehicle";
    }

    /**
     * Method to get all the vehicles in the table Vehicle
     * @return ArrayList of vehicles wrapped in a HashMap
     */
    public ArrayList<HashMap<String, Object>> getVehicles() {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList("SELECT * FROM "+this.tableName);

        return results;
    }

    /**
     * Method to write changes to the database, this is done per model.
     * This method catches if it is a insert of update on its own.
     * @param model The Vehicle Model that needs to be updated
     * @return result, true of false;
     */
    public boolean writeChangesFor(VehicleModel model) {
        DatabaseObject dbo = new DatabaseObject();
        String sql;

        Object result;
        if(model.getId() == 0) {
            sql = "INSERT INTO "+this.tableName+" " +
                    "(id, brand, type, licenseplate, description, airco, towbar, seats, hourly_rent, category_id)" +
                    " VALUES " +
                    "(null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            result = dbo.getUpdateOrInsertResultFor(sql,
                    model.getBrand(),
                    model.getType(),
                    model.getLicenseplate(),
                    model.getDescription(),
                    (model.hasAirco())?"1":"0",
                    (model.hasTowbar())?"1":"0",
                    model.getSeats(),
                    String.valueOf(model.getHourly_rent()),
                    model.getCategory_id());
        }
        else {
            sql = "UPDATE "+this.tableName+" SET " +
                    "brand=?, type=?, licenseplate=?, description=?, airco=?, towbar=?, seats=?, hourly_rent=?, category_id=?" +
                    " WHERE " +
                    "id=?";
            result = dbo.getUpdateOrInsertResultFor(sql,
                    model.getBrand(),
                    model.getType(),
                    model.getLicenseplate(),
                    model.getDescription(),
                    (model.hasAirco())?"1":"0",
                    (model.hasTowbar())?"1":"0",
                    model.getSeats(),
                    String.valueOf(model.getHourly_rent()),
                    model.getCategory_id(),
                    model.getId());
        }

        return (result.equals(1)) ? true : false;
    }

    /**
     * Method to get vehicles between two dates and exclude the vehicle that has been chosen already.
     * @param from - the date from which the search needs to start.
     * @param till - the date when the search needs to end.
     * @param id - id of the vehicle that needs to be excluded.
     * @return ArrayList of vehicles wrapped in a HashMap(String, Object)
     */
    public ArrayList<HashMap<String, Object>> getVehiclesExcludedBetween(DateTime from, DateTime till, int id) {
        DatabaseObject dbo = new DatabaseObject();
        String sql;

        sql = "select " +
                "v.id, v.brand, v.type" +
              " from " +
                "vehicle as v" +
              " where" +
                " v.id not in((select v.id from vehicle as v inner join reservation as r on r.`vehicle_id` = v.id where r.status_id in(1,2) AND (r.startdate >= '"+till.toString("y-M-d")+"') <=> (r.enddate <= '"+from.toString("y-M-d")+"') group by v.id))";

        System.out.println(sql);

        ArrayList<HashMap<String, Object>> results;
        results = dbo.getObjectList(sql);

        return results;
    }

    /**
     * Method to delete a vehicle from the database.
     * @param model Expects a model of the vehicle that needs to be deleted.
     * @return result, true or false.
     */
    public boolean deleteObject(VehicleModel model) {
        DatabaseObject dbo = new DatabaseObject();
        String sql;
        sql = "DELETE FROM "+this.tableName+" " +
                "WHERE id=?";
        Object result;
        result = dbo.getUpdateOrInsertResultFor(sql, model.getId());

        return (result.equals(1)) ? true : false;
    }

    /**
     * Get a vehicle by id from the database
     *
     * @param id
     * @return
     */
    public ArrayList<HashMap<String, Object>> getVehicle(int id) {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList("SELECT * FROM "+this.tableName+" WHERE id=?", id);

        return results;
    }
}