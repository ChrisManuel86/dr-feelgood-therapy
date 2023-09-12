package database;

/**
 * DBInfo
 * DBInfo class for storing all database login information
 *
 * @author Christopher Manuel
 * @version 2023.08.17
 * <p>
 * Change Log:
 * - Initial version
 * - General code cleanup
 */

public class DBInfo {
    private static String DB_HOSTNAME;
    private static String DATABASE;
    private static String DB_USERNAME;
    private static String DB_PASSWORD;

    /**
     * Constructor for DBInfo class
     */
    public DBInfo() {
        DB_HOSTNAME = "onilynx69.live:3360";
        DATABASE = "feelgood_therapy_[unique-server-string]";
        DB_USERNAME = "feelgood_admin";
        DB_PASSWORD = "[placeholder]";
    }

    /**
     * Access Database hostname
     *
     * @return String DB_HOSTNAME
     */
    public String getDB_HOSTNAME() {
        return DB_HOSTNAME;
    }

    /**
     * Access Database name
     *
     * @return String DATABASE
     */
    public String getDATABASE() {
        return DATABASE;
    }

    /**
     * Access Database username
     *
     * @return String DB_USERNAME
     */
    public String getDB_USERNAME() {
        return DB_USERNAME;
    }

    /**
     * Access Database password
     *
     * @return String DB_PASSWORD
     */
    public String getDB_PASSWORD() {
        return DB_PASSWORD;
    }
}
