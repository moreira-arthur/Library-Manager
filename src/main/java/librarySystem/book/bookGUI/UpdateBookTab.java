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
 * Tab for updating Book
 * It has fields for the Book attributes and a button to update the Book
 * It implements the TabModel interface
 */

public class UpdateBookTab implements TabModel {
    private final JFrame frame;
    private final BookHandler handler;
    private final JTabbedPane tabbedPane;

    private JPanel updatePanel;
    private JTextField updateTitleSearchField;
    private JTextField updateTitleField;
    private JTextField updateAuthorField;
    private JTextField updateStartYearField;
    private JTextField updateEndYearField;
    private JTextField updateGenreField;
    private JTextField updateMagazineField;
    private JTextField updatePublisherField;
    private JTextField updateEditionYearField;
    private JTextField updateTotalVolumesField;
    private JTextField updateAcquiredVolumesField;
    private JButton updateByTitleButton;
    /**
     * Constructor for the UpdateBookTab
     * It initializes the frame, handler and tabbedPane
     * It creates the tab
     * @param frame the JFrame
     * @param handler the BookHandler
     * @param tabbedPane the JTabbedPane
     */
    public UpdateBookTab(JFrame frame, BookHandler handler, JTabbedPane tabbedPane){
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
        updatePanel = new JPanel(new GridLayout(13, 2));
        updatePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        updateTitleSearchField = new JTextField();
        updateTitleField = new JTextField();
        updateAuthorField = new JTextField();
        updateStartYearField = new JTextField();
        updateEndYearField = new JTextField();
        updateGenreField = new JTextField();
        updateMagazineField = new JTextField();
        updatePublisherField = new JTextField();
        updateEditionYearField = new JTextField();
        updateTotalVolumesField = new JTextField();
        updateAcquiredVolumesField = new JTextField();
        updateByTitleButton = new JButton("Update Books by Title");
    }   

    /**
     * Method to add the components
     */
    public void addComponents() {
        updatePanel.add(new JLabel("Title to be UPDATED:"));
        updatePanel.add(updateTitleSearchField);
        updatePanel.add(new JLabel());
        updatePanel.add(new JLabel());
        updatePanel.add(new JLabel("Title:"));
        updatePanel.add(updateTitleField);
        updatePanel.add(new JLabel("Author:"));
        updatePanel.add(updateAuthorField);
        updatePanel.add(new JLabel("Start Year:"));
        updatePanel.add(updateStartYearField);
        updatePanel.add(new JLabel("End Year:"));
        updatePanel.add(updateEndYearField);
        updatePanel.add(new JLabel("Genre:"));
        updatePanel.add(updateGenreField);
        updatePanel.add(new JLabel("Magazine:"));
        updatePanel.add(updateMagazineField);
        updatePanel.add(new JLabel("Publisher:"));
        updatePanel.add(updatePublisherField);
        updatePanel.add(new JLabel("Edition Year:"));
        updatePanel.add(updateEditionYearField);
        updatePanel.add(new JLabel("Total Volumes:"));
        updatePanel.add(updateTotalVolumesField);
        updatePanel.add(new JLabel("Acquired Volumes (comma separated):"));
        updatePanel.add(updateAcquiredVolumesField);

        updateByTitleButton.addActionListener(this);
        updatePanel.add(new JLabel());
        updatePanel.add(updateByTitleButton);
        tabbedPane.addTab("Update Book", updatePanel);
    }

    /**
     * Method to update the Book
     * It gets the values from the fields and updates the Book
     * It shows a message dialog if the Book is updated successfully or if there is an error
     * @param e the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        try {
            List<Integer> acquiredVolumes = Arrays.stream(updateAcquiredVolumesField.getText().split(",")).map(Integer::parseInt).toList();
            List<String> isbns = handler.titleIndexManager.getIsbnsByKey(updateTitleSearchField.getText());
            if(isbns.size() == 0) {
                throw new IOException("Book not found.");
            }
            Book updatedBook = new Book(
                    isbns.get(0),
                    updateTitleField.getText(),
                    updateAuthorField.getText(),
                    Integer.parseInt(updateStartYearField.getText()),
                    Integer.parseInt(updateEndYearField.getText()),
                    updateGenreField.getText(),
                    updateMagazineField.getText(),
                    updatePublisherField.getText(),
                    Integer.parseInt(updateEditionYearField.getText()),
                    Integer.parseInt(updateTotalVolumesField.getText()),
                    acquiredVolumes.size(),
                    acquiredVolumes
            );
            handler.updateBook(isbns.get(0), updatedBook);
            JOptionPane.showMessageDialog(frame, "Book updated successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error updating book.");
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error updating book. Please check the fields.");
        }

    }

}