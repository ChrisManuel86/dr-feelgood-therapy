import GUI.DevMenuForm;

import javax.swing.*;

/**
 * Main
 * Main Class to launch a menu to select a module GUI.
 *
 * @author Leron Tolmachev, Christopher Manuel
 * @version 2023.08.17
 *
 * Change Log:
 * - Refactored Project after Sprint One
 * - Refactored project, removing deprecated java calls
 */
class Main {

    /**
     * Main Method of Project
     *
     * @param args arguments passed into Main
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Main::createGUI);
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException
                 | ClassNotFoundException e) {
            // handle exception
        }
    }

    /**
     * Creates application window containing root JPanel of Project
     */
    private static void createGUI() {
        JFrame frame = new JFrame("Dev Menu");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new DevMenuForm().getRootPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
