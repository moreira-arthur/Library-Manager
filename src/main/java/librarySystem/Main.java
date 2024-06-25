package librarySystem;

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

        SwingUtilities.invokeLater(LibrarySystemHandlerGUI::new);
    }
}
