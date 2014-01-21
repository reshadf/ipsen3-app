package data;

import java.sql.*;

/**
 * User: Jasper
 * Class: The databaseconnector connects the application to the database
 */
public class DatabaseConnector {

    protected Connection con = null;

    /**
     * Constructs the class
     * Initiates the MySQL Driver
     */
    public DatabaseConnector() {
        try {
            Class.forName("com." + Config.DB_TYPE + ".jdbc.Driver"); //import class
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Opens connection to the database
     */
    public void connect() {
        try {
            String url = "jdbc:" + Config.DB_TYPE + "://" + Config.DB_HOST + "/" + Config.DB_NAME;
            con = DriverManager.getConnection(url, Config.DB_USER, Config.DB_PASS);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Closes connection to the database
     */
    public void disconnect() {
        try {
            con.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
