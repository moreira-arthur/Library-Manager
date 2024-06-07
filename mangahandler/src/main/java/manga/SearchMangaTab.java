package manga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class SearchMangaTab implements TabModel{
    private JFrame frame;
    private MangaHandler handler;
    private JTabbedPane tabbedPane;

    private JPanel searchPanel;
    private JTextField searchTitleField;
    private JTextField searchIsbnField;
    private JButton searchByTitleButton;
    private JButton searchByIsbnButton;
    private JTextArea searchResultsArea;

    private JScrollPane scrollPane;
    private JPanel searchInputPanel;
    private JPanel searchButtonPanel;

    public SearchMangaTab(JFrame frame, MangaHandler handler, JTabbedPane tabbedPane){
        this.frame = frame;
        this.handler = handler;
        this.tabbedPane = tabbedPane;

        createTab();
    }

    public void createTab() {
        initComponents();
        addComponents();
    }

    public void initComponents() {
        searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        searchTitleField = new JTextField();
        searchIsbnField = new JTextField();
        searchByTitleButton = new JButton("Search by Title");
        searchByIsbnButton = new JButton("Search by ISBN");
        searchResultsArea = new JTextArea();
        searchResultsArea.setEditable(false);
        scrollPane = new JScrollPane(searchResultsArea);
        searchInputPanel = new JPanel(new GridLayout(2, 2));
        searchButtonPanel = new JPanel(new GridLayout(1, 2));
    }

    public void addComponents() {
        searchInputPanel.add(new JLabel("Search by Title:"));
        searchInputPanel.add(searchTitleField);
        searchInputPanel.add(new JLabel("Search by ISBN:"));
        searchInputPanel.add(searchIsbnField);
        searchPanel.add(searchInputPanel, BorderLayout.NORTH);
        searchPanel.add(scrollPane, BorderLayout.CENTER);

        searchButtonPanel.add(searchByTitleButton);
        searchButtonPanel.add(searchByIsbnButton);
        searchPanel.add(searchButtonPanel, BorderLayout.SOUTH);

        searchByTitleButton.addActionListener(this);
        searchByIsbnButton.addActionListener(this);
        tabbedPane.addTab("Search Manga", searchPanel);
    }

    public void actionPerformed(ActionEvent e) {
        List<Manga> mangas;
        if(e.getSource() == searchByTitleButton){
            try {
                mangas = handler.searchMangasByTitle(searchTitleField.getText());
                searchResultsArea.setText("");
                for (Manga manga : mangas) {
                    searchResultsArea.append(manga.toString() + "\n");
                }
                if (mangas.isEmpty()) {
                    searchResultsArea.append("No mangas found.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error searching manga.");
            }
        } else if(e.getSource() == searchByIsbnButton){
            try {
                mangas = handler.searchMangaByIsbn(searchIsbnField.getText());
                searchResultsArea.setText("");
                for (Manga manga : mangas) {
                    searchResultsArea.append(manga.toString() + "\n");
                }
                if (mangas.isEmpty()) {
                    searchResultsArea.append("No mangas found.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error searching manga.");
            }
        }
    }
}
