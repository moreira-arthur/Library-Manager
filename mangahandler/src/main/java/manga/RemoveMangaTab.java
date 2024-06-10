package manga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

/**
 * Tab for removing manga
 * It has fields for the manga attributes and buttons to remove the manga
 * It implements the TabModel interface
 */
public class RemoveMangaTab implements TabModel {  
    private final  JFrame frame;
    private final  MangaHandler handler;
    private final JTabbedPane tabbedPane;

    private JTextField removeIsbnField ;
    private JTextField removeTitleField ;
    private JPanel removePanel;
    private JButton removeByIsbnButton;
    JButton removeByTitleButton;

    /**
     * Constructor for the RemoveMangaTab
     * It initializes the frame, handler and tabbedPane
     * It creates the tab
     * @param frame the JFrame
     * @param handler the MangaHandler
     * @param tabbedPane the JTabbedPane
     */
    public RemoveMangaTab(JFrame frame, MangaHandler handler, JTabbedPane tabbedPane){
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
        removePanel = new JPanel(new GridLayout(3, 2));
        removePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        removeIsbnField = new JTextField();
        removeTitleField = new JTextField();
        removeByIsbnButton = new JButton("Remove by ISBN");
        removeByTitleButton = new JButton("Remove by Title");

    }

    /**
     * Method to add the components
     */
    public void addComponents() {
        removePanel.add(new JLabel("ISBN:"));
        removePanel.add(removeIsbnField);
        removePanel.add(new JLabel("Title:"));
        removePanel.add(removeTitleField);

        removeByIsbnButton.addActionListener(this);
        removeByTitleButton.addActionListener(this);
        removePanel.add(removeByIsbnButton);
        removePanel.add(removeByTitleButton);
        tabbedPane.addTab("Remove Manga", removePanel);
    }

    /**
     * Method to handle the actions of the buttons
     * It removes the manga from the list of mangas
     * @param e the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to remove this manga?");
        if (confirm == JOptionPane.YES_OPTION) {
            if (e.getSource() == removeByIsbnButton) {
                try {
                    handler.deleteManga(removeIsbnField.getText());
                    JOptionPane.showMessageDialog(frame, "Manga removed successfully!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error removing manga.");
                }
                
            }else if (e.getSource() == removeByTitleButton) {
                try {
                    List<Manga> mangas = handler.searchMangasByTitle(removeTitleField.getText());
                    if (!mangas.isEmpty()) {
                        handler.deleteManga(mangas.get(0).getIsbn());
                        JOptionPane.showMessageDialog(frame, "Manga removed successfully!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Manga not found.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error removing manga.");
                }
            }
        }
    }
}   

