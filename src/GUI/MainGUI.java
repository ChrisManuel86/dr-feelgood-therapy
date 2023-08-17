package GUI;

import GUI.Login.CreateAccountForm;
import GUI.Login.LoginForm;
import GUI.Results.ReportForm;
import GUI.Setup.CustomizeForm;
import GUI.Setup.SetupForm;
import GUI.Testing.TestForm;
import Logic.Login.LoginAttempt;
import Logic.Setup.TestSetup;
import Logic.Testing.TestSession;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

/**
 * MainGUI
 * Main GUI class responsible for creating frames for all sections of the program
 *
 * @Author Leron Tolmachev, Christopher Manuel
 * @Version 2023.08.17
 * <p>
 * Change log:
 * - Refactored project, removing deprecated java calls
 */

public class MainGUI extends JPanel {

    private static final LoginAttempt loginAttempt = new LoginAttempt();
    private static JFrame frame = new JFrame();
    private static boolean registering = false;
    private static boolean customizing = false;

    /**
     * Main Method of Project
     *
     * @param args arguments passed into Main
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(MainGUI::createGUI);
    }

    /**
     * Creates application window containing root JPanel of Project
     */
    private static void createGUI() {
        frame = new JFrame("Dev Menu");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new DevMenuForm().getRootPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void createSetupGUI() {
        TestSetup testSetup = new TestSetup();
        frame = new JFrame();
        SetupForm setupForm = new SetupForm(testSetup, frame);
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(setupForm.getRootPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Toggles JFrame content between Login and CreateAccount Forms
     */
    public static void changeSetupGUI(TestSetup testSetup, Object form) {
        JFrame customizeFrame = new JFrame(frame.getTitle());
        SetupForm setupForm = (SetupForm) form;
        frame.getContentPane().removeAll();
        if (customizing) {
            customizing = false;
            frame.getContentPane().add(setupForm.getRootPanel());
            frame.setVisible(true);
            frame.setLocationRelativeTo(customizeFrame);
            frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
            frame.validate();
            frame.repaint();
            frame.pack();
            frame.getContentPane().setVisible(true);
            customizeFrame.dispose();
        } else {
            customizing = true;
            frame.getContentPane().add(new CustomizeForm(testSetup, frame).getRootPanel());
            customizeFrame.getContentPane().removeAll();
            customizeFrame.getContentPane().add(new CustomizeForm(testSetup, frame).getRootPanel());
            customizeFrame.setLocationRelativeTo(frame);
            frame.setVisible(false);
            customizeFrame.validate();
            customizeFrame.repaint();
            customizeFrame.pack();
            customizeFrame.dispose();
        }
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.validate();
        frame.repaint();
        frame.pack();
        frame.getContentPane().setVisible(true);
    }


    public static void createLoginGUI() {
        frame = new JFrame();
        LoginForm loginForm = new LoginForm(loginAttempt, frame);
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(loginForm.getRootPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Toggles JFrame content between Login and CreateAccount Forms
     */
    public static void changeLoginGUI() {
        frame.getContentPane().removeAll();
        if (registering) {
            registering = false;
            frame.getContentPane().add(new LoginForm(loginAttempt, frame).getRootPanel());
        } else {
            registering = true;
            frame.getContentPane().add(new CreateAccountForm(loginAttempt, frame).getRootPanel());
        }
        frame.validate();
        frame.repaint();
        frame.pack();
        frame.getContentPane().setVisible(true);
    }

    public static void createTestGUI(int userID) {
        TestSession session = new TestSession(userID);
        frame = new JFrame();
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new TestForm(session, frame).getRootPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void createReportGUI() {
        JFrame frame = new JFrame("Test Results");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new ReportForm(frame).getRootPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}