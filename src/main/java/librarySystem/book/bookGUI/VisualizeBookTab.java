package librarySystem.book.bookGUI;

import librarySystem.TabModel;
import librarySystem.book.BookHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

/**
 * Tab for visualizing Book
 * It has a text area to display all the Book titles and a button to refresh the list
 * It implements the TabModel interface
 */
public class VisualizeBookTab implements TabModel {
    private final JFrame frame;
    private final BookHandler handler;
    private final JTabbedPane tabbedPane;

    private JPanel viewPanel;
    private JTextArea viewResultsArea;
    JScrollPane viewScrollPane;
    JButton refreshButton;

    /**
     * Constructor for the VisualizeBookTab
     * It initializes the frame, handler and tabbedPane
     * It creates the tab
     * @param frame the JFrame
     * @param handler the BookHandler
     * @param tabbedPane the JTabbedPane
     */
    public VisualizeBookTab(JFrame frame, BookHandler handler, JTabbedPane tabbedPane) {
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
        tabbedPane.addTab("View All Books", viewPanel);
    }

    /**
     * Method to handle the action events
     * It refreshes the list of Book titles
     * @param e the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        try {
            List<String> bookTitles = handler.getAllBooksTitles();
            viewResultsArea.setText("");
            for (String title : bookTitles) {
                viewResultsArea.append(title + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading books titles.");
        }
    }
}