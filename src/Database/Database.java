package Database;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import static java.sql.DriverManager.*;
import Database.DBInfo;


/**
 * Database
 * 
 * Class used for communicating with database, and for placing items
 * into arrays and then passing that information to the form(s).
 *
 * @author Brooke Higgins, Christopher Manuel, Leron Tolmachev, and Luke Kyser
 * @version 2021.11.08
 *
 * Change Log:
 * - Refactored Project after Sprint One
 * - Added insertImage method
 * - Added getTestByName method
 * - Changed insertItem to add an image to the item in the database
 * - Changed getItemByItemID and getItemsByTestID to add images to items from database
 * - Added getAllSessionsForUser method
 * - Removed getAllSessionsForUser method to refactor a different way of obtaining the needed data
 * - Changed SqlServer driver to MySql driver
 * - Updated DB_SERVER variable to point to localhost sql server
 * - Update SQL code to be compatible with mysql
 * - Refactored SQL to utilize SQL lite syntax as opposed to MS SQL
 */
 
public class Database {
    private final static DBInfo dbinfo = new DBInfo();

    private final static String DB_HOSTNAME = dbinfo.getDB_HOSTNAME();
    private final static String DATABASE    = dbinfo.getDATABASE();
    private final static String DB_USERNAME = dbinfo.getDB_USERNAME();
    private final static String DB_PASSWORD = dbinfo.getDB_PASSWORD();

    // Final Database Strings
    private static final String DB_CONNECTION = "jdbc:mysql://" + DB_HOSTNAME + "/" + DATABASE + "?user=" + DB_USERNAME + "&password=" + DB_PASSWORD + "&serverTimezone=PST";

    // Private variables
    private Connection mConnection = null;

    /**
     * Constructor for Database Class
     */
    public Database() {
        connect();
    }

    /**
     * Retrieve a single Item from Database whose ItemID matches the parameter
     *
     * @param int itemID
     * @return Item item
     */
    private Item getItemByItemID(int itemID) {
        String itemQuery = "SELECT * FROM item WHERE ItemID = ?;";
        ArrayList<Item> items = new ArrayList<>();
        try {
            PreparedStatement stmt = mConnection.prepareStatement(itemQuery);
            stmt.setInt(1, itemID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                InputStream stream = rs.getBinaryStream("Image");
                BufferedImage image = null;

                try {
                    if (stream != null)
                        image = ImageIO.read(stream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                items.add(new Item(rs.getInt("ItemID"),
                        rs.getInt("TestID"),
                        rs.getString("Name"),
                        image));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items.get(0);
    }

    /**
     * Retrieve all Items from Database whose TestID matches the parameter
     *
     * @param int testID
     * @return ArrayList items
     */
    public ArrayList<Item> getItemsByTestID(int testID) {
        String itemQuery = "SELECT * FROM item WHERE TestID = ?;";
        ArrayList<Item> items = new ArrayList<>();
        try {
            PreparedStatement stmt = mConnection.prepareStatement(itemQuery);
            stmt.setInt(1, testID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                InputStream stream = rs.getBinaryStream("Image");
                BufferedImage image = null;

                try {
                    if (stream != null)
                        image = ImageIO.read(stream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                items.add(new Item(rs.getInt("ItemID"),
                        rs.getInt("TestID"),
                        rs.getString("Name"),
                        image));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Insert Item object into the Database's ITEM table
     *
     * @param int testID indicates which Test an Item belongs to
     * @param String name indicates the name of the Item
     */
    public void insertItem(int testID, String name, BufferedImage image) {
        String query = "INSERT INTO item (TestId, Name, Image) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = mConnection.prepareStatement(query);
            stmt.setInt(1, testID);
            stmt.setString(2, name);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = null;
            int length = 0;

            try {
                if (image != null) {
                    ImageIO.write(image, "png", outputStream);
                    inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                    length = inputStream.available();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            stmt.setBinaryStream(3, inputStream, length);

            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve all MatchUps from Database whose SessionID matches the parameter
     *
     * @param int sessionID indicates the Session with which all desired MatchUps are associated
     * @return ArrayList matchUps
     */
    public ArrayList<MatchUp> getMatchUps(int sessionID) {
        String itemsQuery = "SELECT QNumber, ItemID_A, ItemID_B, IFNULL(Decision,'')" +
                " AS Decision FROM matchup WHERE SessionID = ?;";
        ArrayList<MatchUp> matchUps = new ArrayList<>();
        try {
            PreparedStatement stmt = mConnection.prepareStatement(itemsQuery);
            stmt.setInt(1, sessionID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                matchUps.add(new MatchUp(rs.getInt("QNumber"),
                        (getItemByItemID(rs.getInt("ItemID_A"))),
                        (getItemByItemID(rs.getInt("ItemID_B"))),
                        rs.getString("Decision")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matchUps;
    }

    /**
     * Insert MatchUp object into the Database's MATCHUP table
     *
     * @param int sessionID indicates which Session a MatchUp belongs to
     * @param int questionNumber indicates order in which MatchUp was presented to user
     * @param int itemAID indicates item presented on Left side of test form
     * @param int itemBID indicates item presented on Right side of test form
     * @param String decision indicates which of the above items user selected (can be "" for neither)
     */
    public void insertMatchUp(int sessionID, int questionNumber, int itemAID, int itemBID, String decision) {
        String query = "INSERT INTO matchup (QNumber, ItemID_A, ItemID_B, Decision)" +
                " VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmt = mConnection.prepareStatement(query);
            stmt.setInt(1, sessionID);
            stmt.setInt(2, questionNumber);
            stmt.setInt(3, itemAID);
            stmt.setInt(4, itemBID);
            stmt.setString(5, decision);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve all Sessions from Database whose TestID matches the parameter
     *
     * @param int testID
     * @return ArrayList sessions
     */
    public ArrayList<Session> getSessionsByTestID(int testID) {
        String sessionQuery = "SELECT * FROM session WHERE TestID = ? ORDER BY Timestamp;";
        ArrayList<Session> sessions = new ArrayList<>();
        try {
            PreparedStatement stmt = mConnection.prepareStatement(sessionQuery);
            stmt.setInt(1, testID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sessions.add(new Session(rs.getInt("SessionID"),
                        rs.getInt("UserID"),
                        rs.getInt("TestID"),
                        rs.getString("Timestamp")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    /**
     * Retrieves all Sessions from Database whose UserID matches the parameter
     *
     * @param int userID int representing userID of desired Session
     * @return ArrayList sessions
     */
    public ArrayList<Session> getSessionsByUserID(int userID) {
        String sessionQuery = "SELECT * FROM session WHERE UserID = ? ORDER BY Timestamp;";
        ArrayList<Session> sessions = new ArrayList<>();
        try {
            PreparedStatement stmt = mConnection.prepareStatement(sessionQuery);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sessions.add(new Session(rs.getInt("SessionID"),
                        rs.getInt("UserID"),
                        rs.getInt("TestID"),
                        rs.getString("Timestamp")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    /**
     * Insert Session object into the Database's SESSION table
     *
     * @param int userID indicates which User completed the Test
     * @param int testID indicates which Test User completed
     * @param String timestamp indicates date and time of Test completion
     */
    public int insertSession(int userID, int testID, String timestamp) {
        String query = "INSERT INTO session (UserID, TestID, Timestamp) " +
                "VALUES (?, ?, ?);";
        try {
            PreparedStatement stmt = mConnection.prepareStatement(query);
            stmt.setInt(1, userID);
            stmt.setInt(2, testID);
            stmt.setString(3, timestamp);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Retrieves all Tests from Database
     *
     * @return ArrayList test
     */
    public ArrayList<Test> getTests() {
        String testQuery = "SELECT * FROM test ORDER BY Name";
        ArrayList<Test> tests = new ArrayList<>();
        try {
            PreparedStatement stmt = mConnection.prepareStatement(testQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tests.add(new Test(rs.getInt("TestID"),
                        rs.getString("Name"),
                        rs.getString("Settings")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tests;
    }

    /**
     * Retrieves Names and IDs of all Tests taken by a specific User from Database
     *
     * @param int userID
     * @return ArrayList test
     */
    public ArrayList<Test> getTestsByUser(int userID) {
        String testQuery = "SELECT DISTINCT test.TestID, Name FROM test " +
                "JOIN session ON (test.TestID = session.TestID) WHERE UserID = ? ORDER BY Name;";
        ArrayList<Test> tests = new ArrayList<>();
        try {
            PreparedStatement stmt = mConnection.prepareStatement(testQuery);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tests.add(new Test(rs.getInt("TestID"),
                        rs.getString("Name"),
                        null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tests;
    }

    /**
     * Insert Test object into Database's TEST table
     *
     * @param String testName name of the Test
     * @return int testID
     */
    public int insertTest(int id, String testName, String settings) {
        if (id == -1) {
            String query = "INSERT INTO test (Name, Settings) VALUES (?, ?); SELECT AUTO_INCREMENT() AS ID;";
            try {
                PreparedStatement stmt = mConnection.prepareStatement(query);
                stmt.setString(1, testName);
                stmt.setString(2, settings);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    return rs.getInt("ID");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String query = "UPDATE test SET Settings = ? WHERE TestID = ?;";
            try {
                PreparedStatement stmt = mConnection.prepareStatement(query);
                stmt.setString(1, settings);
                stmt.setInt(2, id);
                stmt.execute();
                return id;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }



    /**
     * Retrieve a single User from Database whose Email matches the parameter
     *
     * @param String email String representing Email address of desired user
     * @return User user
     */
    public User getUserByEmail(String email) {
        String emailQuery = "SELECT * FROM user_account WHERE Email = ?;";
        ArrayList<User> users = new ArrayList<>();
        try {
            PreparedStatement stmt = mConnection.prepareStatement(emailQuery);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getInt("UserID"),
                        rs.getString("Role"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        rs.getString("Password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users.size() == 0 ? null : users.get(0);
    }



    /**
     * Retrieve identity information (but not passwords) for all Users in
     * Database who have completed a Test Session and whose Role is User
     *
     * @return ArrayList users
     */
    public ArrayList<User> getUsersWithSessions() {
        String userQuery = "SELECT DISTINCT user_account.UserID, FirstName, LastName, Email " +
                "FROM user_account JOIN session ON (user_account.UserID = session.UserID) " +
                "WHERE Role = 'User' ORDER BY LastName, FirstName, Email;";
        ArrayList<User> users = new ArrayList<>();
        try {
            PreparedStatement stmt = mConnection.prepareStatement(userQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getInt("UserID"), "User",
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Insert User object the Database's USER_ACCOUNT table
     *
     * @param String firstName First name of new User
     * @param String lastName Last name of new User
     * @param String email Email address of new User
     * @param String password Password of new User
     */
    public void insertUser(String firstName, String lastName, String email, String password) {
        String query = "INSERT INTO user_account (Role, FirstName, LastName, Email, Password) " +
                "VALUES ('User', ?, ?, ?, ?);";
        try {
            PreparedStatement stmt = mConnection.prepareStatement(query);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, password);
            System.out.println(stmt);
            stmt.executeUpdate();
            System.out.println("Account successfully created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close connection to Database
     */
    public void closeConnection() {
        if (mConnection != null) {
            try {
                mConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Create a connection to the Database
     */
    private void connect() {
        if (mConnection != null)
            return;
        try {
            mConnection = getConnection(DB_CONNECTION);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
    * Remove test from database
    * 
    * @param String table
    * @param int testID
    */
    public void deleteFromDatabase(String table, int id) {
        String criteria = table.toLowerCase().equals("matchup") ? "SessionID" : "TestID";
        String deleteQuery = "DELETE FROM " + table.toLowerCase() + " WHERE " + criteria + " = ?";
        try {
            PreparedStatement stmt = mConnection.prepareStatement(deleteQuery);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
