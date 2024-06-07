package manga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UpdateMangaTab implements TabModel {
    private JFrame frame;
    private MangaHandler handler;
    private JTabbedPane tabbedPane;

    private JPanel updatePanel;
    private JTextField updateIsbnField;
    private JTextField updateTitleField;
    private JTextField updateAuthorsField;
    private JTextField updateStartYearField;
    private JTextField updateEndYearField;
    private JTextField updateGenreField;
    private JTextField updateMagazineField;
    private JTextField updatePublisherField;
    private JTextField updateEditionYearField;
    private JTextField updateTotalVolumesField;
    private JTextField updateAcquiredVolumesField;
    private JButton updateButton;

    public UpdateMangaTab(JFrame frame, MangaHandler handler, JTabbedPane tabbedPane){
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
        updatePanel = new JPanel(new GridLayout(13, 2));
        updatePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        updateIsbnField = new JTextField();
        updateTitleField = new JTextField();
        updateAuthorsField = new JTextField();
        updateStartYearField = new JTextField();
        updateEndYearField = new JTextField();
        updateGenreField = new JTextField();
        updateMagazineField = new JTextField();
        updatePublisherField = new JTextField();
        updateEditionYearField = new JTextField();
        updateTotalVolumesField = new JTextField();
        updateAcquiredVolumesField = new JTextField();
        updateButton = new JButton("Update Manga");
    }

    public void addComponents() {
        updatePanel.add(new JLabel("ISBN (to be updated):"));
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

        updateButton.addActionListener(this);
        updatePanel.add(new JLabel());
        updatePanel.add(updateButton);
        tabbedPane.addTab("Update Manga", updatePanel);
    }

    // Need atention here, because the update is not set ideally
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
            handler.updateManga(updateIsbnField.getText(), updatedManga);
            JOptionPane.showMessageDialog(frame, "Manga updated successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error updating manga.");
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error updating manga. Please check the fields.");
        }

    }

}
