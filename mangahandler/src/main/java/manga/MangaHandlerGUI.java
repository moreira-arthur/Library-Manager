package manga;

import javax.swing.*;
import java.awt.*;

/**
 * GUI for the MangaHandler
 * It creates a JFrame with a JTabbedPane
 * The JTabbedPane has tabs for adding, updating, removing, searching and viewing manga
 */
public class MangaHandlerGUI {
    private MangaHandler manager;
    JTabbedPane tabbedPane;
    JFrame frame;
    
    /**
     * Constructor for the MangaHandlerGUI
     * It creates a JFrame with a JTabbedPane
     * The JTabbedPane has tabs for adding, updating, removing, searching and viewing manga
     */
    public MangaHandlerGUI() {
        manager = new MangaHandler();
        frame = new JFrame("Manga Manager");
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

        // Tab for adding manga
        new AddMangaTab(frame, manager, tabbedPane);
        // Tab for updating manga
        new UpdateMangaTab(frame, manager, tabbedPane);
        // Tab for removing manga
        new RemoveMangaTab(frame, manager, tabbedPane);
        // Tab for searching manga
        new SearchMangaTab(frame, manager, tabbedPane);
        // Tab for viewing manga
        new VisualizeMangaTab(frame, manager, tabbedPane);

        frame.getContentPane().add(tabbedPane);
        frame.setVisible(true);
    }

}
