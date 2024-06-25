package librarySystem;

import java.awt.event.*;

/**
 * Interface for the tab models
 * It has methods to create the tab, initialize the components and add the components
 * It also has an action listener for the buttons in the tab
 */

public interface TabModel extends ActionListener{

    void createTab();
    void initComponents();
    void addComponents();

    void actionPerformed(ActionEvent e);
}   
