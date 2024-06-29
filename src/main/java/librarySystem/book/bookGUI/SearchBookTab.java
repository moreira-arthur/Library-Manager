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
 * Tab for searching book
 * It has fields for the book attributes and buttons to search the book
 * It implements the TabModel interface
 */
public class SearchBookTab implements TabModel{
    private final JFrame frame;
    private final BookHandler handler;
    private final JTabbedPane tabbedPane;

    private JPanel mainPanel;
    private JTabbedPane internalTabbedPane;

    private JTextArea searchResultsArea;
    private JScrollPane scrollPane;

    private JTextField searchTitleField;
    private JTextField searchIsbnField;
    private JTextField searchAuthorField;
    private JTextField searchGenreField;

    private JButton searchByTitleButton;
    private JButton searchByIsbnButton;
    private JButton searchByAuthorButton;
    private JButton searchByGenreButton;

    /**
     * Constructor for the SearchBookTab
     * It initializes the frame, handler and tabbedPane
     * It creates the tab
     * @param frame the JFrame
     * @param handler the bookHandler
     * @param tabbedPane the JTabbedPane
     */
    public SearchBookTab(JFrame frame, BookHandler handler, JTabbedPane tabbedPane) {
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
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        internalTabbedPane = new JTabbedPane();

        searchResultsArea = new JTextArea();
        searchResultsArea.setEditable(false);
        scrollPane = new JScrollPane(searchResultsArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        searchTitleField = new JTextField();
        searchIsbnField = new JTextField();
        searchAuthorField = new JTextField();
        searchGenreField = new JTextField();

        searchByTitleButton = new JButton("Search by Title");
        searchByIsbnButton = new JButton("Search by ISBN");
        searchByAuthorButton = new JButton("Search by Author");
        searchByGenreButton = new JButton("Search by Genre");

        Dimension buttonSize = new Dimension(150, 30);
        searchByTitleButton.setPreferredSize(buttonSize);
        searchByIsbnButton.setPreferredSize(buttonSize);
        searchByAuthorButton.setPreferredSize(buttonSize);
        searchByGenreButton.setPreferredSize(buttonSize);
        
        searchByTitleButton.addActionListener(this);
        searchByIsbnButton.addActionListener(this);
        searchByAuthorButton.addActionListener(this);
        searchByGenreButton.addActionListener(this);
    }

    /**
     * Method to add the components
     */
    public void addComponents() {
        mainPanel.add(internalTabbedPane, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        internalTabbedPane.addTab("Search by Title", createSearchPanel("Title", searchTitleField, searchByTitleButton));
        internalTabbedPane.addTab("Search by ISBN", createSearchPanel("ISBN", searchIsbnField, searchByIsbnButton));
        internalTabbedPane.addTab("Search by Author", createSearchPanel("Author", searchAuthorField, searchByAuthorButton));
        internalTabbedPane.addTab("Search by Genre", createSearchPanel("Genre", searchGenreField, searchByGenreButton));

        tabbedPane.addTab("Search Book", mainPanel);
    }

    /**
     * Method to create individual search panels
     * @param type the type of search
     * @param textField the JTextField for the search input
     * @param button the JButton for the search action
     * @return the JPanel for the search type
     */
    private JPanel createSearchPanel(String type, JTextField textField, JButton button) {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(1, 2));
        inputPanel.add(new JLabel("Search by " + type + ":"));
        inputPanel.add(textField);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(button, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Method to handle the action events
     * It searches the book by title, ISBN, author, or genre
     * @param e the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        List<Book> books = null;
        String query = null;

        if (e.getSource() == searchByTitleButton) {
            query = searchTitleField.getText();
            try {
                if(query.isEmpty()){
                    throw new IOException("Title cannot be empty.");
                }
                books = handler.searchBooksByTitle(query);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error searching book by title.");
            }
        } else if (e.getSource() == searchByIsbnButton) {
            query = searchIsbnField.getText();
            try {
                if(query.isEmpty()){
                    throw new IOException("ISBN cannot be empty.");
                }
                books = handler.searchBookByIsbn(query);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error searching book by ISBN.");
            }
        } else if (e.getSource() == searchByAuthorButton) {
            query = searchAuthorField.getText();
            try {
                if(query.isEmpty()){
                    throw new IOException("Author cannot be empty.");
                }
                books = handler.searchBooksByAuthor(query);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error searching book by author.");
            }
        } else if (e.getSource() == searchByGenreButton) {
            query = searchGenreField.getText();
            try {
                if(query.isEmpty()){
                    throw new IOException("Genre cannot be empty.");
                }
                books = handler.searchBooksByGenre(query);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error searching book by genre.");
            }
        }

        if (books != null) {
            searchResultsArea.setText("");
            for (Book book : books) {
                searchResultsArea.append(book.toString() + "\n");
            }
            if (books.isEmpty()) {
                searchResultsArea.append("No books found.");
            }
        }
    }
}