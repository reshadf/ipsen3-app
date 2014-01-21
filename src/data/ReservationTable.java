package data;

import models.ReservationModel;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: Chris, Floris
 * Class: The reservation table is the direct link to the database for reservations
 */
public class ReservationTable
{
    private String tableName;
    private ArrayList<String> config = null;

    /**
     * Class Constructor
     */
    public ReservationTable() {
        this.tableName = "reservation";
    }

    /**
     * Method to retrieve reservations from the database
     * @return ArrayList of reservations
     */
    public ArrayList<HashMap<String, Object>> getReservations(String view) {
        DatabaseObject dbo = new DatabaseObject();
        String inClause = "";
        if(view.equals("reservations"))
            inClause = " WHERE sid IN(1, 2, 3)";
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList("SELECT * FROM new_"+this.tableName+"_view"+inClause+" ORDER BY startdate ASC, enddate ASC");

        return results;
    }

    /**
     * Get a single reservation from the database
     * @param id - id of the record
     * @return db result
     */
    public ArrayList<HashMap<String, Object>> getReservation(int id) {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList("SELECT * FROM "+this.tableName+" WHERE id=?", id);

        return results;
    }

    /**
     * Get a single reservation with extra information from the database
     * @param id - id of the record
     * @return db result
     */
    public ArrayList<HashMap<String, Object>> getReservationWithExtraInfo(int id) {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList("SELECT * FROM new_"+this.tableName+"_view WHERE id=?", id);

        return results;
    }

    /**
     * Change the status of a reservation
     * @param model - model of the reservation
     * @return true or false
     */
    public boolean changeStatus(ReservationModel model) {
        DatabaseObject dbo = new DatabaseObject();
        String sql;
        sql = "UPDATE "+this.tableName+" SET status_id=? WHERE id=?";

        Object result;
        result = dbo.getUpdateOrInsertResultFor(sql, model.getStatus_id(), model.getId());

        return (result.equals(1)) ? true : false;
    }

    /**
     * Overload of getReservations, can be filtered by dates
     * @param from - date from
     * @param to - date till
     * @return ArrayList of reservations
     */
    public ArrayList<HashMap<String, Object>> getReservations(String from, String to) {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList("SELECT * FROM new_"+this.tableName+"_view WHERE startdate BETWEEN  ?  AND  ? ", from, to);

        return results;
    }

    /**
     * Get all the payment types from the database
     * @return db results
     */
    public ArrayList<HashMap<String, Object>> getPaymentTypes() {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList("SELECT id, name FROM payment_type");

        return results;
    }

    /**
     * Get all the reservation statuses from the database
     * @return db results
     */
    public ArrayList<HashMap<String, Object>> getReservationStatus() {
        DatabaseObject dbo = new DatabaseObject();
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList("SELECT id, value FROM status");

        return results;
    }

    /**
     * Write the changes made in a model to the database
     * @param model - model of the reservation
     * @return true or false
     */
    public boolean writeChangesFor(ReservationModel model) {
        //open database connection
        DatabaseObject dbo = new DatabaseObject();
        String sql;

        Object result;
        if(model.getId() == 0) {
            //insert
            sql = "INSERT INTO reservation VALUES (null, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?)";

            result = dbo.getUpdateOrInsertResultFor(sql,
                    model.getStartdate().toString("y-M-d"),
                    model.getEnddate().toString("y-M-d"),
                    model.getVehicle_id(),
                    model.getCustomer_id(),
                    model.getPayment_type_id(),
                    model.getStatus_id(),
                    2);
        } else {
            //update
            sql = "UPDATE "+this.tableName+" SET startdate=?, enddate=?, vehicle_id=?, customer_id=?, payment_type_id=?, status_id=? WHERE id=?";

            result = dbo.getUpdateOrInsertResultFor(sql,
                    model.getStartdate().toString("y-M-d"),
                    model.getEnddate().toString("y-M-d"),
                    model.getVehicle_id(),
                    model.getCustomer_id(),
                    model.getPayment_type_id(),
                    model.getStatus_id(),
                    model.getId());
        }

        return (result.equals(1)) ? true : false;
    }

    /**
     * Get all the reservations for the prepare list
     * @param date - date today + 1
     * @return db results
     */
    public ArrayList<HashMap<String, Object>> getPrepareListFor(DateTime date) {
        DatabaseObject dbo = new DatabaseObject();
        String sql = "SELECT * FROM new_reservation_view WHERE startdate = '"+date.toString("y-M-d")+"'";
        ArrayList<HashMap<String, Object>> results = dbo.getObjectList(sql);

        return results;
    }
}
