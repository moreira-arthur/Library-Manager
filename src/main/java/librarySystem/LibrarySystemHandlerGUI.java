package librarySystem;

import librarySystem.login.Login;
import librarySystem.login.LoginHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI for the MangaHandler
 * It creates a JFrame with a JTabbedPane
 * The JTabbedPane has tabs for adding, updating, removing, searching and viewing book
 */
public class LibrarySystemHandlerGUI implements ActionListener {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private LoginHandler loginHandler;

    /**
     * Constructor for the MangaHandlerGUI
     * It creates a JFrame with a JTabbedPane
     * The JTabbedPane has tabs for adding, updating, removing, searching and viewing book
     */
    public LibrarySystemHandlerGUI() {
        loginHandler = new LoginHandler();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Login System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 300);

        // Define a GridLayout with 3 rows and 2 columns
        frame.setLayout(new GridLayout(4, 2, 10, 10));
        JLabel greetingLabel = new JLabel("Welcome to the Library Management System !!!");
        JLabel titleLabel = new JLabel("Please enter your credentials to login:");
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        
        // Add components to the frame
        frame.add(greetingLabel);
        frame.add(titleLabel);
        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(new JLabel()); // empty label to adjust the layout
        frame.add(loginButton);

        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        Login login = new Login(username, password);

        if (loginHandler.isAdminLogin(login)) {
            JOptionPane.showMessageDialog(frame, "Admin login successful!");
            frame.dispose();
            new AdminFrame();
            return;
        } else if (loginHandler.isValidLogin(login)) {
            JOptionPane.showMessageDialog(frame, "Login successful!");
            frame.dispose();
            new LibraryFrame();
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password.");
        }
    }

}

