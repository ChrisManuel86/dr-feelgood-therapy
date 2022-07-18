package Database;

/**
 * DBInfo
 * 
 * DBInfo class for storing all database login information
 *
 * @author Christopher Manuel
 * @version 2021.11.08
 * 
 * Change Log:
 * - Initial version
 */

public class DBInfo{
    private static String DB_HOSTNAME;
    private static String DATABASE;
    private static String DB_USERNAME;
    private static String DB_PASSWORD;

    /**
     * Constructor for DBInfo class
     */
    public DBInfo() {
        this.DB_HOSTNAME = "onilynx69.live:3360";
        this.DATABASE   = "feelgood_therapy_[unique-server-string]";
        this.DB_USERNAME = "feelgood_admin";
        this.DB_PASSWORD = "[placeholder]";
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
