package librarySystem.login.loginGUI;

import librarySystem.login.Login;
import librarySystem.login.LoginHandler;
import librarySystem.TabModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Tab for removing login
 * It has fields for the login attributes and a button to remove the login
 * It implements the TabModel interface
 */
public class RemoveLoginTab implements TabModel {
    private final JFrame frame;
    private final LoginHandler loginHandler;
    private final JTabbedPane tabbedPane;

    private JPanel removePanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton removeButton;

    /**
     * Constructor for the RemoveLoginTab
     * It initializes the frame, handler and tabbedPane
     * It creates the tab
     * @param frame the JFrame
     * @param loginHandler the LoginHandler
     * @param tabbedPane the JTabbedPane
     */
    public RemoveLoginTab(JFrame frame,LoginHandler loginHandler, JTabbedPane tabbedPane) {
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
        removePanel = new JPanel(new GridLayout(3, 2, 10, 10));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        removeButton = new JButton("Remove Login");
        removeButton.addActionListener(this);
    }

    /**
     * Method to add the components to the panel
     */
    public void addComponents() {
        removePanel.add(new JLabel("Username:"));
        removePanel.add(usernameField);
        removePanel.add(new JLabel("Password:"));
        removePanel.add(passwordField);
        removePanel.add(new JLabel()); // empty label for spacing
        removePanel.add(removeButton);
        tabbedPane.addTab("Remove Login", removePanel);
    }

    /**
     * Method to remove the login
     * It gets the username and password from the fields
     * It removes the login
     * It shows a message dialog with the result
     * @param e the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        try {
            if(username.isEmpty() || password.isEmpty()) {
                throw new IOException("Username and password cannot be empty");
            }
            loginHandler.removeLogin(new Login(username, password));
            JOptionPane.showMessageDialog(frame, "Login removed successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

}