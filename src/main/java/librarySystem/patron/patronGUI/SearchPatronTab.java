package librarySystem.patron.patronGUI;

import librarySystem.TabModel;
import librarySystem.patron.Patron;
import librarySystem.patron.PatronHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

/**
 * Class that creates the tab to search a patron
 * It implements the TabModel interface
 */
public class SearchPatronTab implements TabModel {
    private final JFrame frame;
    private final PatronHandler handler;
    private final JTabbedPane tabbedPane;

    private JPanel searchPanel;
    private JTextField searchLastNameField;
    private JTextField searchCpfField;
    private JButton searchByLastNameButton;
    private JButton searchByCpfButton;
    private JTextArea searchResultsArea;

    private JScrollPane scrollPane;
    private JPanel searchInputPanel;
    private JPanel searchButtonPanel;

    /**
     * Constructor for the SearchMangaTab
     * It initializes the frame, handler and tabbedPane
     * It creates the tab
     * @param frame the JFrame
     * @param handler the MangaHandler
     * @param tabbedPane the JTabbedPane
     */
    public SearchPatronTab(JFrame frame, PatronHandler handler, JTabbedPane tabbedPane){
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
        searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        searchLastNameField = new JTextField();
        searchCpfField = new JTextField();
        searchByLastNameButton = new JButton("Search by Last Name");
        searchByCpfButton = new JButton("Search by CPF");
        searchResultsArea = new JTextArea();
        searchResultsArea.setEditable(false);
        scrollPane = new JScrollPane(searchResultsArea);
        searchInputPanel = new JPanel(new GridLayout(2, 2));
        searchButtonPanel = new JPanel(new GridLayout(1, 2));
    }

    /**
     * Method to add the components
     */
    public void addComponents() {
        searchInputPanel.add(new JLabel("Search by Last Name:"));
        searchInputPanel.add(searchLastNameField);
        searchInputPanel.add(new JLabel("Search by CPF:"));
        searchInputPanel.add(searchCpfField);
        searchPanel.add(searchInputPanel, BorderLayout.NORTH);
        searchPanel.add(scrollPane, BorderLayout.CENTER);

        searchButtonPanel.add(searchByLastNameButton);
        searchButtonPanel.add(searchByCpfButton);
        searchPanel.add(searchButtonPanel, BorderLayout.SOUTH);

        searchByLastNameButton.addActionListener(this);
        searchByCpfButton.addActionListener(this);
        tabbedPane.addTab("Search Patron", searchPanel);
    }

    /**
     * Method to handle the action events
     * It searches the patron by last name or cpf
     * @param e the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        List<Patron> patrons;
        if(e.getSource() == searchByLastNameButton){
            try {
                patrons = handler.searchPatronsByLastName(searchLastNameField.getText());
                searchResultsArea.setText("");
                for (Patron patron : patrons) {
                    searchResultsArea.append(patron.toString() + "\n");
                }
                if (patrons.isEmpty()) {
                    searchResultsArea.append("No patrons found.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error searching patron.");
            }
        } else if(e.getSource() == searchByCpfButton){
            try {
                patrons = handler.searchPatronByCpf(searchCpfField.getText());
                searchResultsArea.setText("");
                for (Patron patron : patrons) {
                    searchResultsArea.append(patron.toString() + "\n");
                }
                if (patrons.isEmpty()) {
                    searchResultsArea.append("No patrons found.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error searching patron.");
            }
        }
    }
}
