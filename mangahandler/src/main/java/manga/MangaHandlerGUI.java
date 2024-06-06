package manga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MangaHandlerGUI {
    private MangaHandler manager;

    public MangaHandlerGUI() {
        manager = new MangaHandler();
        JFrame frame = new JFrame("Manga Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Adicionar aba de adicionar mangá
        JPanel addPanel = new JPanel(new GridLayout(13, 2));
        addPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField addIsbnField = new JTextField();
        JTextField addTitleField = new JTextField();
        JTextField addAuthorsField = new JTextField();
        JTextField addStartYearField = new JTextField();
        JTextField addEndYearField = new JTextField();
        JTextField addGenreField = new JTextField();
        JTextField addMagazineField = new JTextField();
        JTextField addPublisherField = new JTextField();
        JTextField addEditionYearField = new JTextField();
        JTextField addTotalVolumesField = new JTextField();
        JTextField addAcquiredVolumesField = new JTextField();

        addPanel.add(new JLabel("ISBN:"));
        addPanel.add(addIsbnField);
        addPanel.add(new JLabel("Title:"));
        addPanel.add(addTitleField);
        addPanel.add(new JLabel("Authors (comma separated):"));
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

        JButton addButton = new JButton("Add Manga");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<String> authors = Arrays.asList(addAuthorsField.getText().split(","));
                    List<Integer> acquiredVolumes = Arrays.asList(addAcquiredVolumesField.getText().split(",")).stream().map(Integer::parseInt).toList();
                    Manga manga = new Manga(
                            addIsbnField.getText(),
                            addTitleField.getText(),
                            authors,
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
                    manager.addManga(manga);
                    JOptionPane.showMessageDialog(frame, "Manga added successfully!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error adding manga." + "\n" + ex.getMessage());

                }
            }
        });
        addPanel.add(new JLabel());
        addPanel.add(addButton);
        tabbedPane.addTab("Add Manga", addPanel);

        // Aba de atualização de mangá
        JPanel updatePanel = new JPanel(new GridLayout(13, 2));
        updatePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField updateIsbnField = new JTextField();
        JTextField updateTitleField = new JTextField();
        JTextField updateAuthorsField = new JTextField();
        JTextField updateStartYearField = new JTextField();
        JTextField updateEndYearField = new JTextField();
        JTextField updateGenreField = new JTextField();
        JTextField updateMagazineField = new JTextField();
        JTextField updatePublisherField = new JTextField();
        JTextField updateEditionYearField = new JTextField();
        JTextField updateTotalVolumesField = new JTextField();
        JTextField updateAcquiredVolumesField = new JTextField();

        updatePanel.add(new JLabel("ISBN:"));
        updatePanel.add(updateIsbnField);
        updatePanel.add(new JLabel("Title:"));
        updatePanel.add(updateTitleField);
        updatePanel.add(new JLabel("Authors (comma separated):"));
        updatePanel.add(updateAuthorsField);
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

        JButton updateButton = new JButton("Update Manga");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<String> authors = Arrays.asList(updateAuthorsField.getText().split(","));
                    List<Integer> acquiredVolumes = Arrays.asList(updateAcquiredVolumesField.getText().split(",")).stream().map(Integer::parseInt).toList();
                    Manga updatedManga = new Manga(
                            updateIsbnField.getText(),
                            updateTitleField.getText(),
                            authors,
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
                    manager.updateManga(updateIsbnField.getText(), updatedManga);
                    JOptionPane.showMessageDialog(frame, "Manga updated successfully!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error updating manga.");
                }
            }
        });
        updatePanel.add(new JLabel());
        updatePanel.add(updateButton);
        tabbedPane.addTab("Update Manga", updatePanel);

        // Aba de remoção de mangá
        JPanel removePanel = new JPanel(new GridLayout(3, 2));
        removePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField removeIsbnField = new JTextField();
        JTextField removeTitleField = new JTextField();

        removePanel.add(new JLabel("ISBN:"));
        removePanel.add(removeIsbnField);
        removePanel.add(new JLabel("Title:"));
        removePanel.add(removeTitleField);

        JButton removeByIsbnButton = new JButton("Remove by ISBN");
        removeByIsbnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to remove this manga?");
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        manager.deleteManga(removeIsbnField.getText());
                        JOptionPane.showMessageDialog(frame, "Manga removed successfully!");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error removing manga.");
                    } 
                }
            }
        });

        JButton removeByTitleButton = new JButton("Remove by Title");
        removeByTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to remove this manga?");
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        List<Manga> mangas = manager.searchMangasByTitle(removeTitleField.getText());
                        if (!mangas.isEmpty()) {
                            manager.deleteManga(mangas.get(0).getIsbn());
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
        });

        removePanel.add(removeByIsbnButton);
        removePanel.add(removeByTitleButton);
        tabbedPane.addTab("Remove Manga", removePanel);

        // Aba de pesquisa de mangá
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField searchTitleField = new JTextField();
        JTextField searchIsbnField = new JTextField();
        JButton searchByTitleButton = new JButton("Search by Title");
        JButton searchByIsbnButton = new JButton("Search by ISBN");
        JTextArea searchResultsArea = new JTextArea();
        searchResultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(searchResultsArea);

        JPanel searchInputPanel = new JPanel(new GridLayout(2, 2));
        searchInputPanel.add(new JLabel("Search by Title:"));
        searchInputPanel.add(searchTitleField);
        searchInputPanel.add(new JLabel("Search by ISBN:"));
        searchInputPanel.add(searchIsbnField);
        searchPanel.add(searchInputPanel, BorderLayout.NORTH);
        searchPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel searchButtonPanel = new JPanel(new GridLayout(1, 2));
        searchButtonPanel.add(searchByTitleButton);
        searchButtonPanel.add(searchByIsbnButton);
        searchPanel.add(searchButtonPanel, BorderLayout.SOUTH);

        searchByTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Manga> mangas = manager.searchMangasByTitle(searchTitleField.getText());
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
        });

        searchByIsbnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Manga> mangas = manager.searchMangaByIsbn(searchIsbnField.getText());
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
        });

        tabbedPane.addTab("Search Manga", searchPanel);


        // Aba de visualização de mangás
        JPanel viewPanel = new JPanel(new BorderLayout());
        JTextArea viewResultsArea = new JTextArea();
        viewResultsArea.setEditable(false);
        JScrollPane viewScrollPane = new JScrollPane(viewResultsArea);

        JButton refreshButton = new JButton("Refresh List");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<String> mangaTitles = manager.getAllMangaTitles();
                    viewResultsArea.setText("");
                    for (String title : mangaTitles) {
                        viewResultsArea.append(title + "\n");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error loading manga titles.");
                }
            }
        });

        viewPanel.add(viewScrollPane, BorderLayout.CENTER);
        viewPanel.add(refreshButton, BorderLayout.SOUTH);

        tabbedPane.addTab("View All Mangas", viewPanel);

    
        frame.getContentPane().add(tabbedPane);
        frame.setVisible(true);
    }

}
