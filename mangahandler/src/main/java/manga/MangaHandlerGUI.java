package manga;

import javax.swing.*;

public class MangaHandlerGUI {
    private MangaHandler manager;
    JTabbedPane tabbedPane;
    JFrame frame;

    public MangaHandlerGUI() {

        manager = new MangaHandler();
        frame = new JFrame("Manga Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        tabbedPane = new JTabbedPane();

        // Adicionar aba de adicionar mangá
        new AddMangaTab(frame, manager, tabbedPane);
        // Aba de atualização de mangá
        new UpdateMangaTab(frame, manager, tabbedPane);

        // Aba de remoção de mangá
        new RemoveMangaTab(frame, manager, tabbedPane);
        // Aba de pesquisa de mangá
        new SearchMangaTab(frame, manager, tabbedPane);

        // Aba de visualização de mangás
        new VisualizeMangaTab(frame, manager, tabbedPane);

        frame.getContentPane().add(tabbedPane);
        frame.setVisible(true);
    }

}
