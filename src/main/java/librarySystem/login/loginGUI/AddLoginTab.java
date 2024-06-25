package librarySystem.login.loginGUI;

import librarySystem.login.Login;
import librarySystem.login.LoginHandler;
import librarySystem.TabModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Tab for adding login
 * It has fields for the login attributes and a button to add the login
 * It implements the TabModel interface
 */
public class AddLoginTab implements TabModel {
    private final JFrame frame;
    private final LoginHandler loginHandler;
    private final JTabbedPane tabbedPane;
    private JPanel addPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton addButton;

    /**
     * Constructor for the AddLoginTab
     * It initializes the frame, handler and tabbedPane
     * It creates the tab
     * @param frame the JFrame
     * @param loginHandler the LoginHandler
     * @param tabbedPane the JTabbedPane
     */
    public AddLoginTab(JFrame frame,LoginHandler loginHandler, JTabbedPane tabbedPane) {
        this.frame = frame;
        this.loginHandler = loginHandler;
        this.tabbedPane = tabbedPane;
        createTab();
    }

    /**
     * Method to create the tab
     * It initializes the components and adds the components
     */
    public void createTab() {
        initComponents();
        addComponents();
    }

    /**
     * Method to initialize the components
     * It initializes the panel, fields and button
     */
    public void initComponents() {
        addPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        addButton = new JButton("Add Login");
        addButton.addActionListener(this);
    }

    /**
     * Method to add the components to the panel
     * It adds labels and fields to the panel
     */
    public void addComponents() {
        addPanel.add(new JLabel("Username:"));
        addPanel.add(usernameField);
        addPanel.add(new JLabel("Password:"));
        addPanel.add(passwordField);
        addPanel.add(new JLabel()); // empty label for spacing
        addPanel.add(addButton);
        tabbedPane.addTab("Add Login", addPanel);
    }

    /**
     * Method to handle the action when the button is clicked
     * It gets the username and password from the fields
     * It adds the login to the loginHandler
     * It shows a message dialog with the result
     * @param e the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        try {
            loginHandler.addLogin(new Login(username, password));
            JOptionPane.showMessageDialog(frame, "Login added successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

}