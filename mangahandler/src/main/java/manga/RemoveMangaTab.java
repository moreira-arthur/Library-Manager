package manga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class RemoveMangaTab implements TabModel {  
    private JFrame frame;
    private MangaHandler handler;
    private JTabbedPane tabbedPane;

    private JTextField removeIsbnField ;
    private JTextField removeTitleField ;
    private JPanel removePanel;
    private JButton removeByIsbnButton;
    JButton removeByTitleButton;

    public RemoveMangaTab(JFrame frame, MangaHandler handler, JTabbedPane tabbedPane){
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
        removePanel = new JPanel(new GridLayout(3, 2));
        removePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        removeIsbnField = new JTextField();
        removeTitleField = new JTextField();
        removeByIsbnButton = new JButton("Remove by ISBN");
        removeByTitleButton = new JButton("Remove by Title");

    }

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

