package models;

import helpers.MessageQueue;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import views.CustomerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Jasper
 * Class: Model, inserting and getting information to create a model for the factory
 */

public class CustomerModel extends IpsenModel
{
    private CustomerView view;

    private int id = 0; //class variable if new id i s0
    private String email = "";
    private String firstname = "";
    private String insertion = "";
    private String lastname = "";
    private String company = "";
    private int kvknr = 0;
    private String phone = "";
    private String address = "";
    private String zip = "";
    private String city = "";
    private String birthdate;
    private String passportnumber = "";

    //regex patterns
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_ZIP_REGEX = Pattern.compile("^[0-9]{4}\\s*[a-zA-Z]{2}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_DATE_REGEX = Pattern.compile("/(0[1-9]|1[012])[\\/](0[12]|[1-9][0-9]|3[01])[\\/](19|20)[0-9]{2}/", Pattern.CASE_INSENSITIVE);

    /**
     * Constructor of the model
     */
    public CustomerModel(CustomerView view) {
        super();
        this.view = view;
    }

    /**
     * Getter for id
     * @return id of the customer
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for email
     * @return email
     */
    public String getEmail() {
        if(!email.equals("null"))
        {
            return email;
        }
        else
        {
            return "";
        }
    }

    /**
     * Setter for email
     * @param email
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for firstname
     * @return firstname
     */
    public String getFirstname() {
        if(!firstname.equals("null"))
        {
            return firstname;
        }
        else
        {
            return "";
        }
    }

    /**
     * setter for firstname
     *
     * @param firstname
     */

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Getter for insertion
     * @return insertion
     */

    public String getInsertion() {
        if(!insertion.equals("null"))
        {
            return insertion;
        }
        else
        {
            return "";
        }
    }

    public void setInsertion(String insertion) {
        this.insertion = insertion;
    }

    /**

     * Getter and setter for lastname
     * @return type of the customer
     */

    public String getLastname() {
        if(!lastname.equals("null"))
        {
            return lastname;
        }
        else
        {
            return "";
        }
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Getter for company
     * @return company
     */

    public String getCompany() {
        if(!company.equals("null"))
        {
            return company;
        }
        else
        {
            return "";
        }
    }

    /**
     * Setter for company
     * @param company
     */

    public void setCompany(String company) {
        this.company = company;
    }

    public int getKvknr() {

            return kvknr;
    }

    /**
     * Added for ipsen3 compatibility
     * @param kvknr
     */
    public void setKvknr(Integer kvknr) {
        this.kvknr = kvknr;
    }

    /**
     * Getter for phone
     * @return phone
     */

    public String getPhone() {
        if(!phone.equals("null"))
        {
            return phone;
        }
        else
        {
            return "";
        }
    }

    /**
     * Setter for phone
     * @param phone
     */

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**

     * Getter for address
     * @return address
     */

    public String getAddress() {
        if(!address.equals("null"))
        {
            return address;
        }
        else
        {
            return "";
        }
    }

    /**
     * Setter for address
     * @param address
     */

    public void setAddress(String address) {
        this.address = address;
    }

    /**

     * Getter for zip
     * @return zip
     */

    public String getZip() {
        if(!zip.equals("null"))
        {
            return zip;
        }
        else
        {
            return "";
        }
    }

    /**
     * Setter for zip
     * @param zip
     */

    public void setZip(String zip) {
        this.zip = zip;
    }

    /**

     * Getter city
     * @return city
     */

    public String getCity() {
        if(!city.equals("null"))
        {
            return city;
        }
        else
        {
            return "";
        }
    }

    /**
     * Setter for city
     * @param city
     */

    public void setCity(String city) {
        this.city = city;
    }

    /**

     * Getter for birthdate
     * @return type of the customer
     */

    public String getBirthdate() {
        if(!birthdate.equals("null"))
        {
            return birthdate;
        }
        else
        {
            return "";
        }
    }

    /**
     * Setter for birthdate
     * @param birthdate
     */

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
    /**

     * Getter for passportnumber
     * @return type of the customer
     */

    public String getPassportnumber() {
        if(!passportnumber.equals("null"))
        {
            return passportnumber;
        }
        else
        {
            return "";
        }
    }

    /**
     * Setter for passportnumber
     * @param passportnumber
     */

    public void setPassportnumber(String passportnumber) {
        this.passportnumber = passportnumber;
    }


    /**
     * Validation for the entry of data
     * @param key - the key of the data string
     * @param value - the value of the data string
     * @return true of false
     */
    public boolean validate(Object key, Object value)
    {
        if(key.toString().equals("email"))
        {
            if(!validateEmail(value.toString())) {
                MessageQueue.getInstance().addMessage("E-mail is niet geldig (example@provider.nl)", 1);
                return false;
            }

        }
        if(key.toString().equals("zip"))
        {
            if(!validateZip(value.toString())) {
                MessageQueue.getInstance().addMessage("Postcode is niet geldig (XXXXAB of XXXX AB)", 1);
                return false;
            }
        }
        if(key.toString().equals("birthdate")) {
            if(!value.toString().equals("null") && value != null) {

            }
        }
        if( key.toString().equals("firstname")      ||
            key.toString().equals("insertion")      ||
            key.toString().equals("lastname")       ||
            key.toString().equals("company")        ||
            key.toString().equals("kvknr")          ||
            key.toString().equals("passportnumber") ||
            key.toString().equals("address"))
        {
            Pattern specialChars = Pattern.compile("[a-zA-Z0-9\\s]*");

            if(!specialChars.matcher(value.toString()).matches()) {
                MessageQueue.getInstance().addMessage(key.toString()+": mag alleen letters, cijfers en spaties bevatten", 1);
                return false;
            }
        }

        return true;
    }

    /**
     * Validate email
     * @param emailStr
     * @return true or false
     */

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    /**
     * Validate zip
     * @param zipStr
     * @return true or false
     */

    public static boolean validateZip(String zipStr) {
        Matcher matcher = VALID_ZIP_REGEX .matcher(zipStr);
        return matcher.find();
    }

    /**
     * Validate date
     * @param dateStr
     * @return true or false
     */

    public static boolean validateDate(String dateStr) {
        Matcher matcher = VALID_DATE_REGEX .matcher(dateStr);
        return matcher.find();
    }

}
