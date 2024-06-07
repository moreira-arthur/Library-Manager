package manga;

import javax.swing.SwingUtilities;

/**
 * Main class
 * It is the entry point of the application
 */
public class Main {
    /**
     * Main method to start the application
     * @param args Arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MangaHandlerGUI();
            }
        });
    }
}
