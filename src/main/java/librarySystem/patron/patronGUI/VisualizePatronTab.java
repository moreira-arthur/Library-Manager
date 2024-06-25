package librarySystem.patron.patronGUI;

import librarySystem.TabModel;
import librarySystem.patron.PatronHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;


/**
 * Class that creates the tab to visualize all patrons
 * It implements the TabModel interface
 */
public class VisualizePatronTab implements TabModel {
    private final JFrame frame;
    private final PatronHandler handler;
    private final JTabbedPane tabbedPane;

    private JPanel viewPanel;
    private JTextArea viewResultsArea;
    JScrollPane viewScrollPane;
    JButton refreshButton;

    /**
     * Constructor for the VisualizePatronTab
     * It initializes the frame, handler and tabbedPane
     * @param frame the frame
     * @param handler the patron handler
     * @param tabbedPane the tabbed pane
     */
    public VisualizePatronTab(JFrame frame, PatronHandler handler, JTabbedPane tabbedPane) {
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
        tabbedPane.addTab("View All Patrons", viewPanel);
    }


    public void actionPerformed(ActionEvent e) {
        try {
            List<String> lastNames = handler.getAllPatronsLastNames();
            viewResultsArea.setText("");
            for (String title : lastNames) {
                viewResultsArea.append(title + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading patrons titles.");
        }
    }
}
