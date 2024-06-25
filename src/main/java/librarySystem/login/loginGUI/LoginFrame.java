
package librarySystem.login.loginGUI;

import librarySystem.login.LoginHandler; 

import java.awt.*;
import javax.swing.*; 

/**
 * Class to handle the GUI for the Login System
 * It has a JFrame with a login form
 * It has fields for username and password and a login button
 * It implements the ActionListener interface
 */
public class LoginFrame {
    private final LoginHandler loginManager;
    private final JTabbedPane tabbedPane;
    private final JFrame frame;

    /**
     * Constructor for the LoginFrame
     * It initializes the loginManager and creates the frame
     */
    public LoginFrame(){
        loginManager = new LoginHandler();
        frame = new JFrame("Book Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
    
        Font font = new Font("Arial", Font.BOLD, 16);
        Color backgroundColor = new Color(230, 230, 250); // Light lavender
        Color foregroundColor = new Color(25, 25, 112); // Midnight blue
    
        // Modify the look and feel of the GUI
        UIManager.put("Label.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("TabbedPane.font", font);
        UIManager.put("Label.foreground", foregroundColor);
        UIManager.put("TextField.foreground", foregroundColor);
        UIManager.put("Button.foreground", foregroundColor);
        UIManager.put("TextArea.foreground", foregroundColor);
        UIManager.put("TabbedPane.foreground", foregroundColor);
        UIManager.put("Panel.background", backgroundColor);
        UIManager.put("TextField.background", Color.WHITE);
        UIManager.put("TextArea.background", Color.WHITE);
        UIManager.put("Button.background", Color.LIGHT_GRAY);
    
        tabbedPane = new JTabbedPane();
    
        // Tab for adding login
        new AddLoginTab(frame, loginManager, tabbedPane);
        // Tab for removing login
        new RemoveLoginTab(frame, loginManager, tabbedPane);
    
        frame.getContentPane().add(tabbedPane);
        frame.setVisible(true);
    }
}
