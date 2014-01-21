package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: Chris
 * Class: This class inserts info into the database
 */
public class DatabaseObject extends DatabaseConnector
{
    private PreparedStatement preparedStatement; //private Statement statement;

    public DatabaseObject() {
        this.preparedStatement = null;
    }

    /**
     * Method to acquire objectlist
     * @param query
     * @param objects
     * @return
     */
    public ArrayList<HashMap<String, Object>> getObjectList(String query, Object... objects) {
        ArrayList<HashMap<String, Object>> itemList = new ArrayList<HashMap<String, Object>>(); //create arraylist with hashmap, each rule can use the HashMap string as getter
        String statementType[] = query.split(" ");

        try {
            this.connect();
            if (!this.con.isClosed()) //connection is open
            {
                preparedStatement = this.con.prepareStatement(query); //prepare query

                int counted = 1; //start counting at 1 (stupid java thing)
                for (Object o : objects) { //check type for each object

                    if (o instanceof String) { //string, jdbc will add ''
                        preparedStatement.setString(counted, "" + o); //counted tell us what ? it will replace
                    } else if (o instanceof Integer) { //integer
                        preparedStatement.setInt(counted, (Integer) o);
                    } else  if (o instanceof Date) { //integer
                        preparedStatement.setDate(counted, (Date) o);
                    } else if (o instanceof Float) {
                        preparedStatement.setFloat(counted, (Float) o);
                    } else if (o instanceof Blob) {
                        preparedStatement.setBlob(counted, (Blob) o);
                    } else {
                        preparedStatement.setString(counted, (String) o);
                    }
                    counted++; //add 1 to our counter
                }

                if(statementType[0].toUpperCase().equals("INSERT") || statementType[0].toUpperCase().equals("UPDATE") || statementType[0].toUpperCase().equals("DELETE"))
                { //manipulation
                    preparedStatement.executeUpdate();
                }
                else
                {
                    ResultSet results = preparedStatement.executeQuery(); //get query results
                    ResultSetMetaData metaData = results.getMetaData(); //get query meta data

                    while (results.next()) //as long as we have results
                    {
                        HashMap<String, Object> map = new HashMap(); //create hashmap
                        for (int i = 0; i < metaData.getColumnCount(); i++) { //for each column
                            map.put(metaData.getColumnName(i + 1), convertToType(metaData.getColumnType(i + 1 ), results.getString(metaData.getColumnName(i + 1)))); //add value for each column
                        }
                        itemList.add(map);
                    }

                    preparedStatement.close();
                    results.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Query Exception: " + e.getMessage());
            itemList = null;
        } finally {
            this.disconnect();
        }

        return itemList; //return result
    }

    /**
     * connection with database and then try to change values if required
     * @param query
     * @param objects
     * @return
     */
    public Object getUpdateOrInsertResultFor(String query, Object... objects) {
        Object retValue = null;

        String statementType[] = query.split(" ");
        try {
            this.connect();
            if (!this.con.isClosed()) //connection is open
            {
                preparedStatement = this.con.prepareStatement(query); //prepare query

                int counted = 1; //start counting at 1 (stupid java thing)
                for (Object o : objects) { //check type for each object
                    if (o instanceof String) { //string, jdbc will add ''
                        preparedStatement.setString(counted, "" + o); //counted tell us what ? it will replace
                    } else if (o instanceof Integer) { //integer
                        preparedStatement.setInt(counted, (Integer) o);
                    } else  if (o instanceof Date) { //integer
                        preparedStatement.setDate(counted, (Date) o);
                    } else if (o instanceof Float) {
                        preparedStatement.setFloat(counted, (Float) o);
                    } else if (o instanceof Blob) {
                        preparedStatement.setBlob(counted, (Blob) o);
                    } else {
                        preparedStatement.setString(counted, (String) o);
                    }
                    counted++; //add 1 to our counter
                }

                if(statementType[0].toUpperCase().equals("INSERT") || statementType[0].toUpperCase().equals("UPDATE") || statementType[0].toUpperCase().equals("DELETE"))
                { //manipulation
                    retValue = preparedStatement.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.out.println("Query Exception: " + e.getMessage());
        } finally {
            this.disconnect();
        }

        return retValue;
    }

    /**
     * For Testing (Chris)
     * @param sql statement without where clause
     * @return results
     */
    public ArrayList<HashMap<String, Object>> simpleObjectList(String sql) {
        ArrayList<HashMap<String, Object>> itemList = new ArrayList<HashMap<String, Object>>();

        try {
            this.connect();
            if(!con.isClosed()) {
                Statement s = con.createStatement( );
                s.executeQuery(sql);

                ResultSet rs = s.getResultSet ( );
                ResultSetMetaData metaData = rs.getMetaData();

                while(rs.next()) {
                    HashMap<String, Object> map = new HashMap();
                    for (int i = 0; i < metaData.getColumnCount(); i++) { //for each column
                        map.put(metaData.getColumnName(i + 1), convertToType(metaData.getColumnType(i + 1 ), rs.getString(metaData.getColumnName(i + 1))));
                    }
                    itemList.add(map);
                }

                rs.close(); // close result set
                s.close(); // close statement
            }
        } catch (Exception e) {
            System.out.println("Exception: "+e.getMessage());
        } finally {
            // If we don't have a error, close the connection!
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("SQLException: "+e.getMessage());
            }
        }

        return itemList;
    }

    /**
     * method to change the object to a value
     * @param type
     * @param value
     * @return
     */
    public static Object convertToType(int type, Object value) {
        return "" + value;
    }
}
