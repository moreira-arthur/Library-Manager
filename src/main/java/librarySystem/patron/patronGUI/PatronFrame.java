package librarySystem.patron.patronGUI;

import librarySystem.patron.PatronHandler;

import java.awt.*;
import javax.swing.*;


public class PatronFrame {
    private final PatronHandler patronManager;
    private final JTabbedPane tabbedPane;
    private final JFrame frame;
    
    /**
     * Constructor for the PatronFrame
     * It initializes the patronManager, tabbedPane and frame
     */
    public PatronFrame() {
        patronManager = new PatronHandler();
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


        // Tab for adding patron
        new AddPatronTab(frame,patronManager,tabbedPane);
        //tab for searching patron
        new SearchPatronTab(frame,patronManager,tabbedPane);
        //tab for removing patron
        new RemovePatronTab(frame,patronManager,tabbedPane);
        //tab for updating patron
        new UpdatePatronTab(frame,patronManager,tabbedPane);
        //Tab for viewing patron
        new VisualizePatronTab(frame,patronManager,tabbedPane);

        frame.getContentPane().add(tabbedPane);
        frame.setVisible(true);
    }

}

