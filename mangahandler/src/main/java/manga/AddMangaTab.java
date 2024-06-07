package manga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AddMangaTab implements TabModel{
    private JFrame frame;
    private MangaHandler handler;
    private JTabbedPane tabbedPane;
    
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


    public AddMangaTab(JFrame frame, MangaHandler handler, JTabbedPane tabbedPane){
        this.frame = frame;
        this.handler = handler;
        this.tabbedPane = tabbedPane;

        createTab();
    }

    public void createTab(){
        initComponents();
        addComponents();
    }

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
        addButton = new JButton("Add Manga");
    }

    public void addComponents(){
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

        addButton.addActionListener(this);
        addPanel.add(new JLabel());
        addPanel.add(addButton);
        tabbedPane.addTab("Add Manga", addPanel);
    }


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
            handler.addManga(manga);
            JOptionPane.showMessageDialog(frame, "Manga added successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error adding manga." + "\n" + ex.getMessage());
        } catch(Exception ex){
            JOptionPane.showMessageDialog(frame, "Error adding manga." + "\n" + "Please check the fields and try again.");
        }
    }

}
