package librarySystem.book.bookGUI;

import librarySystem.TabModel;
import librarySystem.book.Book;
import librarySystem.book.BookHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

/**
 * Tab for removing book
 * It has fields for the book attributes and a button to remove the book
 * It implements the TabModel interface
 */
public class RemoveBookTab implements TabModel {
    private final  JFrame frame;
    private final BookHandler handler;
    private final JTabbedPane tabbedPane;

    private JTextField removeIsbnField ;
    private JTextField removeTitleField ;
    private JPanel removePanel;
    private JButton removeByIsbnButton;
    JButton removeByTitleButton;

    /**
     * Constructor for the RemoveBookTab
     * It initializes the frame, handler and tabbedPane
     * It creates the tab
     * @param frame the JFrame
     * @param handler the BookHandler
     * @param tabbedPane the JTabbedPane
     */
    public RemoveBookTab(JFrame frame, BookHandler handler, JTabbedPane tabbedPane){
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
        removePanel = new JPanel(new GridLayout(2, 3));
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
        removeByIsbnButton.addActionListener(this);
        removeByTitleButton.addActionListener(this);


        removePanel.add(new JLabel("ISBN:"));
        removePanel.add(removeIsbnField);
        removePanel.add(removeByIsbnButton);
        removePanel.add(new JLabel("Title:"));
        removePanel.add(removeTitleField);
        removePanel.add(removeByTitleButton);

        tabbedPane.addTab("Remove Book", removePanel);
    }

    /**
     * Method to handle the action events
     * It removes the book from the database
     * @param e the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to remove this book?");
        if (confirm == JOptionPane.YES_OPTION) {
            if (e.getSource() == removeByIsbnButton) {
                try {
                    if(removeIsbnField.getText().isEmpty()){
                        JOptionPane.showMessageDialog(frame, "Please enter an ISBN.");
                        return;
                    }
                    handler.deleteBook(removeIsbnField.getText());
                    JOptionPane.showMessageDialog(frame, "Book removed successfully!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error removing book.");
                }
                
            }else if (e.getSource() == removeByTitleButton) {
                try {
                    if(removeTitleField.getText().isEmpty()){
                        JOptionPane.showMessageDialog(frame, "Please enter a title.");
                        return;
                    }
                    List<Book> books = handler.searchBooksByTitle(removeTitleField.getText());
                    if (!books.isEmpty()) {
                        handler.deleteBook(books.get(0).getIsbn());
                        JOptionPane.showMessageDialog(frame, "Book removed successfully!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Book not found.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error removing book.");
                }
            }
        }
    }
}   