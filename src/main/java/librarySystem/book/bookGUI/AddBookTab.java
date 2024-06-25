package librarySystem.book.bookGUI;

import librarySystem.TabModel;
import librarySystem.book.Book;
import librarySystem.book.BookHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Tab for adding book
 * It has fields for the book attributes and a button to add the book
 * It implements the TabModel interface
 */
public class AddBookTab implements TabModel {
    private final JFrame frame;
    private final BookHandler handler;
    private final JTabbedPane tabbedPane;
    
    private JPanel addPanel;
    private JTextField addIsbnField;
    private JTextField addTitleField;
    private JTextField addAuthorsField;
    private JTextField addStartYearField;
    private JTextField addEndYearField;
    private JTextField addGenreField;
    private JTextField addMagazineField;
    private JTextField addPublisherField;
    private JTextField addEditionYearField;
    private JTextField addTotalVolumesField;
    private JTextField addAcquiredVolumesField;
    private JButton addButton;

    /**
     * Constructor for the AddBookTab
     * It initializes the frame, handler and tabbedPane
     * It creates the tab
     * @param frame the JFrame
     * @param handler the BookHandler
     * @param tabbedPane the JTabbedPane
     */
    public AddBookTab(JFrame frame, BookHandler handler, JTabbedPane tabbedPane){
        this.frame = frame;
        this.handler = handler;
        this.tabbedPane = tabbedPane;

        createTab();
    }

    /**
     * Method to create the tab
     * It initializes the components and adds the components
     */
    public void createTab(){
        initComponents();
        addComponents();
    }

    /**
     * Method to initialize the components
     * It initializes the panel, fields and button
     */
    public void initComponents(){
        addPanel = new JPanel(new GridLayout(13, 2));
        addPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        addIsbnField = new JTextField();
        addTitleField = new JTextField();
        addAuthorsField = new JTextField();
        addStartYearField = new JTextField();
        addEndYearField = new JTextField();
        addGenreField = new JTextField();
        addMagazineField = new JTextField();
        addPublisherField = new JTextField();
        addEditionYearField = new JTextField();
        addTotalVolumesField = new JTextField();
        addAcquiredVolumesField = new JTextField();
        addButton = new JButton("Add Book");
    }

    /**
     * Method to add the components to the panel
     * It adds labels and fields for the book attributes
     * It adds a button to add the book
     */
    public void addComponents(){
        addPanel.add(new JLabel("ISBN:"));
        addPanel.add(addIsbnField);
        addPanel.add(new JLabel("Title:"));
        addPanel.add(addTitleField);
        addPanel.add(new JLabel("Author:"));
        addPanel.add(addAuthorsField);
        addPanel.add(new JLabel("Start Year:"));
        addPanel.add(addStartYearField);
        addPanel.add(new JLabel("End Year:"));
        addPanel.add(addEndYearField);
        addPanel.add(new JLabel("Genre:"));
        addPanel.add(addGenreField);
        addPanel.add(new JLabel("Magazine:"));
        addPanel.add(addMagazineField);
        addPanel.add(new JLabel("Publisher:"));
        addPanel.add(addPublisherField);
        addPanel.add(new JLabel("Edition Year:"));
        addPanel.add(addEditionYearField);
        addPanel.add(new JLabel("Total Volumes:"));
        addPanel.add(addTotalVolumesField);
        addPanel.add(new JLabel("Acquired Volumes (comma separated):"));
        addPanel.add(addAcquiredVolumesField);

        addButton.addActionListener(this);
        addPanel.add(new JLabel());
        addPanel.add(addButton);
        tabbedPane.addTab("Add Book", addPanel);
    }

    
    public void actionPerformed(ActionEvent e) {
        try {
            
            List<Integer> acquiredVolumes = Arrays.stream(addAcquiredVolumesField.getText().split(",")).map(Integer::parseInt).toList();

            Book book = new Book(
                    addIsbnField.getText(),
                    addTitleField.getText(),
                    addAuthorsField.getText(),
                    Integer.parseInt(addStartYearField.getText()),
                    Integer.parseInt(addEndYearField.getText()),
                    addGenreField.getText(),
                    addMagazineField.getText(),
                    addPublisherField.getText(),
                    Integer.parseInt(addEditionYearField.getText()),
                    Integer.parseInt(addTotalVolumesField.getText()),
                    acquiredVolumes.size(),
                    acquiredVolumes
            );
            handler.addBook(book);
            JOptionPane.showMessageDialog(frame, "Book added successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error adding book." + "\n" + ex.getMessage());
        } catch(Exception ex){
            JOptionPane.showMessageDialog(frame, "Error adding book." + "\n" + "Please check the fields and try again.");
        }
    }

}