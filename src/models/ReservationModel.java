package models;

import helpers.MessageQueue;
import org.joda.time.*;
import views.ReservationView;

import java.util.Date;

/**
 * User: Chris
 * Class: Model, inserting and getting information to create a model for the factory
 */
public class ReservationModel
{
    private ReservationView view;

    private int id;
    private int customer_id;
    private int vehicle_id;
    private int payment_type_id;
    private int status_id;
    private int invoice_id;
    private DateTime startdate;
    private DateTime enddate;
    private String brand;
    private String type;
    private String licenseplate;
    private String name;
    private String company;
    private String payment;
    private String firstname;
    private String insertion;
    private String lastname;
    private String status;

    /**
     * Getter for id
     * @return id of the customer
     */
    public int getId() {return id;}

    /**
     * Setter for id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Getter for payment
     * @param payment
     */
    public String getPayment() {
        return payment;
    }
    /**
     * Setter for payment
     * @param payment
     */
    public void setPayment(String payment) {
        this.payment = payment;
    }
    /**
     * Getter for status
     * @param status
     */
    public String getStatus() {
        return status;
    }
    /**
     * Setter for status
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * Getter for Customer_id
     * @param Customer_id
     */
    public int getCustomer_id() {
        return customer_id;
    }
    /**
     * Setter for Customer_id
     * @param customer_id
     */
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
    /**
     * Getter for Vehicle_id
     * @param Vehicle_id
     */
    public int getVehicle_id() {
        return vehicle_id;
    }
    /**
     * Setter for Vehicle_id
     * @param vehicle_id
     */
    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }
    /**
     * Getter for Payment_type_id
     * @param Payment_type_id
     */
    public int getPayment_type_id() {
        return payment_type_id;
    }
    /**
     * Setter for Payment_type_id
     * @param payment_type_id
     */
    public void setPayment_type_id(int payment_type_id) {
        this.payment_type_id = payment_type_id;
    }
    /**
     * Getter for Status_id
     * @param Status_id
     */
    public int getStatus_id() {
        return status_id;
    }
    /**
     * Setter for Status_id
     * @param status_id
     */
    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }
    /**
     * Getter for Invoice)id
     * @param Invoice)id
     */
    public int getInvoice_id() {
        return invoice_id;
    }
    /**
     * Setter for Invoice)id
     * @param invoice)id
     */
    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }
    /**
     * Getter for Startdate
     * @param Startdate
     */
    public DateTime getStartdate() {
        return startdate;
    }
    /**
     * Setter for Startdate
     * @param startdate
     */
    public void setStartdate(DateTime startdate) {
        this.startdate = startdate;
    }
    /**
     * Getter for Enddate
     * @param Enddate
     */
    public DateTime getEnddate() {
        return enddate;
    }
    /**
     * Setter for Enddate
     * @param enddate
     */
    public void setEnddate(DateTime enddate) {
        this.enddate = enddate;
    }
    /**
     * Getter for Brand
     * @param Brand
     */
    public String getBrand() {
        return brand;
    }
    /**
     * Setter for Brand
     * @param brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }
    /**
     * Getter for Type
     * @param Type
     */
    public String getType() {
        return type;
    }
    /**
     * Setter for Type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * Getter for Licenseplate
     * @param Licenseplate
     */
    public String getLicenseplate() {
        return licenseplate;
    }
    /**
     * Setter for Licenseplate
     * @param licenseplate
     */
    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }
    /**
     * Getter for name
     * @param name
     */
    public String getName() {
        return name;
    }
    /**
     * Setter for name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Getter for company
     * @param company
     */
    public String getCompany() {
        return company;
    }
    /**
     * Setter for company
     * @param company
     */
    public void setCompany(String company) {
        this.company = company;
    }
    /**
     * Getter for Firstname
     * @param firstname
     */
    public String getFirstname() {
        return firstname;
    }
    /**
     * Setter for Firstname
     * @param firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    /**
     * Getter for Insertion
     * @param Insertion
     */
    public String getInsertion() {
        return insertion;
    }
    /**
     * Setter for Insertion
     * @param insertion
     */
    public void setInsertion(String insertion) {
        this.insertion = insertion;
    }
    /**
     * Getter for Lastname
     * @param Lastname
     */
    public String getLastname() {
        return lastname;
    }
    /**
     * Setter for Lastname
     * @param lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public ReservationModel(ReservationView view) {
        this.view = view;
    }

    /**
     * Validation for the entry of data
     * @param key - the key of the data string
     * @param value - the value of the data string
     * @return true of false
     */
    public boolean validate(Object key, Object value)
    {
        if(key.toString().equals("enddate")) {
            if(value.toString().trim().length() == 0) {
                MessageQueue.getInstance().addMessage("Einddatum is niet ingevuld", 1);
            }
            DateTime dt = new DateTime(value.toString().split(" ")[0]+"T00:00:00.0");
            if(dt.isBefore(this.getStartdate())) {
                MessageQueue.getInstance().addMessage("Einddatum is ingesteld voor de begindatum", 1);
                return false;
            }
        }

        return true;
    }
}
