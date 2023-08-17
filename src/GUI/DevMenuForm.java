package GUI;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static GUI.MainGUI.*;

/**
 * DevMenuForm
 * A GUI form which allows for selecting a specific module GUI to load.
 * Currently only supports AdminSetup and Test.
 *
 * @author Leron Tolmachev, Christopher Manuel
 * @version 2023.08.17
 * <p>
 * Change Log:
 * - Refactored Project after Sprint One.
 * - Updated with correct button destinations for all modules
 * - Refactored project, removing deprecated java calls
 */
public class DevMenuForm extends ComponentUI {
    private JPanel rootPanel;
    private JButton adminSetupButton;
    private JButton userLoginButton;
    private JButton testButton;
    private JButton resultReportingButton;

    /**
     * Constructor for the DevMenuForm Class
     */
    public DevMenuForm() {
        rootPanel.setPreferredSize(new Dimension(300, 200));
        adminSetupButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                createSetupGUI();
            }
        });
        userLoginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                createLoginGUI();
            }
        });
        testButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                createTestGUI(1);
            }
        });
        resultReportingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                createReportGUI();
            }
        });
    }

    /**
     * Access DevMenuForm's rootPanel field
     *
     * @return JPanel rootPanel
     */
    public JPanel getRootPanel() {
        return rootPanel;
    }
}
