package data;

/**
 * User: Chris
 * Class: This class shows nessecary address information and such
 * which will be used to connect with the database and
 * futher down are the adress information for the business which buys this application
 */
public abstract class Config {

    public final static String DB_TYPE              = "mysql";
    public final static String DB_HOST              = "145.97.16.192"; //example: localhost:3306
    public final static String DB_NAME              = "ipsen3";
    public final static String DB_USER              = "ipsen";
    public final static String DB_PASS              = "Ip27sen";

    public final static String INVOICE_COMPANY      = "LeenMeij BV";
    public final static String INVOICE_ADDRESS      = "Zernikedreef 11";
    public final static String INVOICE_ZIP_CITY     = "2333 CK Leiden";
    public final static String INVOICE_COMPANY_CODE = "K.v.k. 123456789";
    public final static String INVOICE_TAX_CODE     = "Btw nr. 987654321";
    public final static String INVOICE_ACCOUNT_NR   = "ING 789789789";
    public final static String INVOICE_ACCOUNT_NAME = "Tnv. LeenMeij BV";
    public final static String INVOICE_EMAIL        = "info@leenmeij.nl";
    public final static String INVOICE_PHONE        = "+31 (0)71 - 5188800";
    public final static String INVOICE_WEBSITE      = "www.leenmeij.nl";

}
