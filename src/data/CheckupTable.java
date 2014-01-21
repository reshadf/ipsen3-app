package data;

import models.CheckupModel;
import models.VehicleModel;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: Jasper
 * Class: helps insert info into the database for certain models.
 **/
public class CheckupTable {

    private String tableName;

    public CheckupTable() {
        this.tableName = "checkup";
    }


    public ArrayList<HashMap<String, Object>> getCheckups() {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList("SELECT * FROM "+this.tableName);

        return results;
    }

    public ArrayList<HashMap<String, Object>> getCheckupsByVehicleId(int id) {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList("SELECT * FROM "+this.tableName + " WHERE vehicle_id = ? ORDER BY date_detection DESC", id);

        return results;
    }

    /**
     * acquires the model and inserts it into the database
     * @param model
     * @return
     */
    public boolean writeChangesFor(CheckupModel model) {
        DatabaseObject dbo = new DatabaseObject();
        String sql;

        System.out.println(  model.getDate_detection().toString("y-M-d") + " - " +  new Double(model.getDistance_driven()) + " - " + model.getCleaned() + " - " + model.getDamage() + " - " + model.getVehicle_id());

        Object result;
        if(model.getId() == 0) {
            //                                                 1                   2           3        4         5                    1  2  3  4  5
            sql = "INSERT INTO " + this.tableName + " (id, date_detection, distance_driven, cleaned, damage, vehicle_id) VALUES (null, ?, ?, ?, ?, ?)";
            result = dbo.getUpdateOrInsertResultFor(
                    sql,
                    "" + model.getDate_detection().toString("y-M-d"),    //1
                    "" + model.getDistance_driven(),                     //2
                    (model.getCleaned())?1:0,                            //3
                    "" + model.getDamage(),                              //4
                    Integer.valueOf(model.getVehicle_id()));             //5
            System.out.println(result);
        }
        else {
            //                                              1                  2             3         4           5               6
            sql = "UPDATE " + this.tableName + " SET date_detection=?, distance_driven=?, cleaned=?, damage=?, vehicle_id=? WHERE id=?";
            result = dbo.getUpdateOrInsertResultFor(
                    sql,
                    "" + model.getDate_detection().toString("y-M-d"),   //1
                    "" + model.getDistance_driven(),             //2
                    (model.getCleaned())?1:0,                           //3
                    "" + model.getDamage(),                             //4
                    Integer.valueOf(model.getVehicle_id()),             //5
                    Integer.valueOf(model.getId()));                    //6
        }

        System.out.println(result);

        return (result.equals(1)) ? true : false;
    }

    /**
     * delete object if chosen by the user
     * @param model
     * @return
     */
    public boolean deleteObject(CheckupModel model) {
        DatabaseObject dbo = new DatabaseObject();
        String sql;
        sql = "DELETE FROM "+this.tableName+" " +
                "WHERE id=?";
        Object result;
        result = dbo.getUpdateOrInsertResultFor(sql, model.getId());

        return (result.equals(1)) ? true : false;
    }
}