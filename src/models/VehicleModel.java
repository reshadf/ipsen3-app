package models;

import views.VehicleView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * User: Reinier
 * Class: Model, inserting and getting information to create a model for the factory
 */
public class VehicleModel extends IpsenModel
{
    private VehicleView view;

    private int id = 0;                 // gives the availible options for filling in the forms
    private String brand = "";
    private String type = "";
    private String licenseplate = "";
    private String description = "";
    private boolean airco = false;
    private boolean towbar = false;
    private int seats = 0;
    private double hourly_rent = 0.0f;
    private int category_id;

    /**
     * getCategory
     * @return
     */

    public int getCategory_id() {
        return category_id;
    }

    /**
     * set category id
     *
     * @param category_id
     */
    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    /**
     * Getter and setter for id
     * @return id of the vehicle
     */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter and setter for brand
     * @return brand of the vehicle
     */
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Getter and setter for type
     * @return type of the vehicle
     */
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter and setter for license plate
     * @return license plate of the vehicle
     */
    public String getLicenseplate() {
        return licenseplate;
    }
    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    /**
     * Getter and setter for description
     * @return description of the vehicle
     */
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter and setter for airco
     * @return if vehicle has airco
     */
    public boolean hasAirco() {
        return this.airco;
    }
    public void setAirco(boolean airco) {
        this.airco = airco;
    }

    /**
     * Getter and setter for tow bar
     * @return if vehicle has tow bar
     */
    public boolean hasTowbar() {
        return this.towbar;
    }
    public void setTowbar(boolean towbar) {
        this.towbar = towbar;
    }

    /**
     * Getter and setter for seats
     * @return amount of seats in the vehicle
     */
    public int getSeats() {
        return this.seats;
    }
    public void setSeats(int seats) {
        this.seats = seats;
    }

    /**
     * Getter and setter for hourly rent
     * @return the hourly rent of the vehicle
     */
    public double getHourly_rent() {
        return this.hourly_rent;
    }
    public void setHourly_rent(double hourly_rent) {
        this.hourly_rent = hourly_rent;
    }

    /**
     * Constructor of the model
     */
    public VehicleModel(VehicleView view) {
        super();
        this.view = view;
    }

    /**
     * Validation for the entry of data
     * @param key - the key of the data string
     * @param value - the value of the data string
     * @return true of false
     */
    public Object validate(Object key, Object value)  // all about checking license plates
    {
        if(key.toString().equals("licenseplate"))
        {
            int sidecode = this.getSidecodeLicenseplate(value.toString());
            if(sidecode == -1 || sidecode == 0)
                return false;
        }
        if(key.toString().equals("seats")) {
            try {
                Integer.valueOf(value.toString());
            } catch(NumberFormatException ex) {
                return false;
            }
        }
        if(key.toString().equals("hourly_rent")) {
            try {
                Double.valueOf(value.toString());
            } catch(NumberFormatException ex) {
                return false;
            }
        }
        if(key.toString().equals("brand")) {
            Pattern specialChars = Pattern.compile("[a-zA-Z0-9\\s]*");

            if(!specialChars.matcher(value.toString()).matches()) {
                return false;
            }
        }
        if(key.toString().equals("type")) {
            Pattern specialChars = Pattern.compile("[a-zA-Z0-9\\s]*");

            if(!specialChars.matcher(value.toString()).matches()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validate licenseplate
     * @param licenseplate
     * @return if okay or not
     */

    private int getSidecodeLicenseplate(String licenseplate){
        licenseplate = licenseplate.replace("-", "").toUpperCase();
                                                                          // checking license plate for validation
        Pattern[] arrSC = new Pattern[10];
        Pattern scUitz;

        arrSC[0] = Pattern.compile("^[a-zA-Z]{2}[\\d]{2}[\\d]{2}$");         //   1       XX-99-99
        arrSC[1] = Pattern.compile("^[\\d]{2}[\\d]{2}[a-zA-Z]{2}$");         //   2       99-99-XX
        arrSC[2] = Pattern.compile("^[\\d]{2}[a-zA-Z]{2}[\\d]{2}$");         //   3       99-XX-99
        arrSC[3] = Pattern.compile("^[a-zA-Z]{2}[\\d]{2}[a-zA-Z]{2}$");      //   4       XX-99-XX
        arrSC[4] = Pattern.compile("^[a-zA-Z]{2}[a-zA-Z]{2}[\\d]{2}$");      //   5       XX-XX-99
        arrSC[5] = Pattern.compile("^[\\d]{2}[a-zA-Z]{2}[a-zA-Z]{2}$");      //   6       99-XX-XX
        arrSC[6] = Pattern.compile("^[\\d]{2}[a-zA-Z]{3}[\\d]{1}$");         //   7       99-XXX-9
        arrSC[7] = Pattern.compile("^[\\d]{1}[a-zA-Z]{3}[\\d]{2}$");         //   8       9-XXX-99
        arrSC[8] = Pattern.compile("^[a-zA-Z]{2}[\\d]{3}[a-zA-Z]{1}$");      //   9       XX-999-X
        arrSC[9] = Pattern.compile("^[a-zA-Z]{1}[\\d]{3}[a-zA-Z]{2}$");      //   10      X-999-XX

        //except licenseplates for diplomats
        scUitz = Pattern.compile("^CD[ABFJNST][0-9]{1,3}$");              //for example: CDB1 or CDJ45

        for(int i=0;i<arrSC.length;i++){
            if (arrSC[i].matcher(licenseplate).matches()) {
                return i+1;
            }
        }

        if (scUitz.matcher(licenseplate).matches()) {
            return 0;
        }

        return -1;
    }
}
