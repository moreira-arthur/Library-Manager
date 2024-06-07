package manga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class VisualizeMangaTab implements TabModel {
    private JFrame frame;
    private MangaHandler handler;
    private JTabbedPane tabbedPane;

    private JPanel viewPanel;
    private JTextArea viewResultsArea;
    JScrollPane viewScrollPane;
    JButton refreshButton;

    public VisualizeMangaTab(JFrame frame, MangaHandler handler, JTabbedPane tabbedPane) {
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
        viewPanel = new JPanel(new BorderLayout());
        viewResultsArea = new JTextArea();
        viewResultsArea.setEditable(false);
        viewScrollPane = new JScrollPane(viewResultsArea);
        refreshButton = new JButton("Refresh List");
    }

    public void addComponents() {
        refreshButton.addActionListener(this);
        viewPanel.add(viewScrollPane, BorderLayout.CENTER);
        viewPanel.add(refreshButton, BorderLayout.SOUTH);
        tabbedPane.addTab("View All Mangas", viewPanel);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            List<String> mangaTitles = handler.getAllMangaTitles();
            viewResultsArea.setText("");
            for (String title : mangaTitles) {
                viewResultsArea.append(title + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading manga titles.");
        }
    }
}
