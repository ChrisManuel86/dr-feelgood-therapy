package Database;

import java.awt.image.BufferedImage;

/**
 * DBInfo
 * 
 * DBInfo class for storing all database host/login informatoin
 *
 * @author Christopher Manuel
 * @version 2021.11.08
 * 
 * Change Log:
 * - Initial version
 */
public class DBInfo{
    private final static String DB_HOSTNAME;
    private final static String DATABASE;
    private final static String DB_USERNAME;
    private final static String DB_PASSWORD;

    /**
     * Constructor for Item class
     *
     * @param itemID   unique identifier for each Item
     * @param itemName name of Item to be displayed to user
     * @param testID   Test with which Item is associated
     */
    public DBInfo() {
        this.DB_HOSTNAME = "[placeholder]";
        this.DATABASE = "[placeholder]";
        this.DB_USERNAME = "[placeholder]"
        this.DB_PASSWORD = "[placeholder]";
        
    }

    /**
     * Access Item's id field
     *
     * @return int Item ID
     */
    public int getDB_HOSTNAME() {
        return id;
    }

    /**
     * Access Item's losses field
     *
     * @return int losses
     */
    public int getDATABASE() {
        return losses;
    }

    /**
     * Mutate Item's losses field
     *
     * @param losses sum of how many times preference was given to another item
     */
    public void setLosses(int losses) {
        this.losses = losses;
    }

    /**
     * Access Item's name field
     *
     * @return int Item name
     */
    public String getDB_USERNAME() {
        return name;
    }

    /**
     * Access Item's score field
     *
     * @return int score
     */
    public int getScore() {
        return score;
    }

    /**
     * Mutate Item's score field
     *
     * @param score sum of Item's wins, ties, and losses
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Access Item's ties field
     *
     * @return int ties
     */
    public int getTies() {
        return ties;
    }

    /**
     * Mutate Item's ties field
     *
     * @param ties sum of how many times no preference was given
     */
    public void setTies(int ties) {
        this.ties = ties;
    }

    /**
     * Access Item's wins field
     *
     * @return int wins
     */
    public int getWins() {
        return wins;
    }

    /**
     * Mutate Item's wins field
     *
     * @param wins sum of times preference was given to Item
     */
    public void setWins(int wins) {
        this.wins = wins;
    }

    /**
     * Access Item's image field
     *
     * @return BufferedImage image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Mutate Item's image field
     *
     * @param image Image to be displayed along with Item
     */
    public void setImage(BufferedImage image) { this.image = image; }

    // Allows item array to be sorted by score
    @Override
    public int compareTo(Item compareItem) {

        int compareScore = compareItem.getScore();

        return compareScore - this.getScore();
    }

    //Formatting to appropriately display Items in JLists and JComboBoxes.
    @Override
    public String toString() {
        return getName() == null ? "Select an Item..." : getName();
    }
}
