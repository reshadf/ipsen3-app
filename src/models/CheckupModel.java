package models;

import org.joda.time.DateTime;
import views.CheckupView;

/**
 * User: Chris
 * Class: Model, inserting and getting information to create a model for the factory
 */
public class CheckupModel extends IpsenModel
{
    private CheckupView view;

    private int id = 0;
    private DateTime date_detection;
    private double distance_driven;
    private boolean cleaned;
    private String damage;
    private int vehicle_id;


    public CheckupModel(CheckupView view) {
        super();
        this.view = view;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DateTime getDate_detection() {
        return date_detection;
    }

    public void setDate_detection(DateTime date_detection) {
        this.date_detection = date_detection;
    }

    public double getDistance_driven() {
        return distance_driven;
    }

    public void setDistance_driven(double distance_driven) {
        this.distance_driven = distance_driven;
    }

    public boolean getCleaned() {
        return cleaned;
    }

    public void setCleaned(boolean cleaned) {
        this.cleaned = cleaned;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    /**
     * Validation for the entry of data
     * @param key - the key of the data string
     * @param value - the value of the data string
     * @return true of false
     */
    public Object validate(Object key, Object value)
    {
//        if(key.toString().equals("seats")) {
//            try {
//                Integer.valueOf(value.toString());
//            } catch(NumberFormatException ex) {
//                return false;
//            }
//        }
//        if(key.toString().equals("hourly_rent")) {
//            try {
//                Double.valueOf(value.toString());
//            } catch(NumberFormatException ex) {
//                return false;
//            }
//        }

        return true;
    }


}
