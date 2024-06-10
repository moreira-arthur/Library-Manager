package manga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

/**
 * Tab for visualizing manga
 * It has a text area to display all the manga titles and a button to refresh the list
 * It implements the TabModel interface
 */
public class VisualizeMangaTab implements TabModel {
    private final JFrame frame;
    private final MangaHandler handler;
    private final JTabbedPane tabbedPane;

    private JPanel viewPanel;
    private JTextArea viewResultsArea;
    JScrollPane viewScrollPane;
    JButton refreshButton;

    /**
     * Constructor for the VisualizeMangaTab
     * It initializes the frame, handler and tabbedPane
     * It creates the tab
     * @param frame the JFrame
     * @param handler the MangaHandler
     * @param tabbedPane the JTabbedPane
     */
    public VisualizeMangaTab(JFrame frame, MangaHandler handler, JTabbedPane tabbedPane) {
        this.frame = frame;
        this.handler = handler;
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
     */
    public void initComponents() {
        viewPanel = new JPanel(new BorderLayout());
        viewResultsArea = new JTextArea();
        viewResultsArea.setEditable(false);
        viewScrollPane = new JScrollPane(viewResultsArea);
        refreshButton = new JButton("Refresh List");
    }
    
    /**
     * Method to add the components to the panel
     */
    public void addComponents() {
        refreshButton.addActionListener(this);
        viewPanel.add(viewScrollPane, BorderLayout.CENTER);
        viewPanel.add(refreshButton, BorderLayout.SOUTH);
        tabbedPane.addTab("View All Mangas", viewPanel);
    }

    /**
     * Method to handle the action events
     * It refreshes the list of manga titles
     * @param e the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        try {
            List<String> mangaTitles = handler.getAllMangaTitles();
            viewResultsArea.setText("");
            for (String title : mangaTitles) {
                viewResultsArea.append(title + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading manga titles.");
        }
    }
}
