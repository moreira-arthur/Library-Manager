package librarySystem.patron.patronGUI;

import librarySystem.TabModel;
import librarySystem.patron.Patron;
import librarySystem.patron.PatronHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

/**
 * Class that creates the tab to remove a patron
 * It implements the TabModel interface
 */
public class RemovePatronTab implements TabModel {
    private final  JFrame frame;
    private final PatronHandler handler;
    private final JTabbedPane tabbedPane;

    private JTextField removeCpfField ;
    private JTextField removeLastNameField ;
    private JPanel removePanel;
    private JButton removeByCpfButton;
    JButton removeByLastNameButton;

    /**
     * Constructor for the RemovePatronTab
     * It initializes the frame, handler and tabbedPane
     * @param frame the frame
     * @param handler the patron handler
     * @param tabbedPane the tabbed pane
     */
    public RemovePatronTab(JFrame frame, PatronHandler handler, JTabbedPane tabbedPane){
        this.frame = frame;
        this.handler = handler;
        this.tabbedPane = tabbedPane;

        createTab();
    }

    /**
     * Method to create the tab
     * It initializes the components and adds them to the tab
     */
    public void createTab() {
        initComponents();
        addComponents();
    }

    /**
     * Method to initialize the components
     * It creates the fields for the Patron attributes and the button
     */
    public void initComponents() {
        removePanel = new JPanel(new GridLayout(3, 2));
        removePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        removeCpfField = new JTextField();
        removeLastNameField = new JTextField();
        removeByCpfButton = new JButton("Remove by ISBN");
        removeByLastNameButton = new JButton("Remove by LastName");

    }

    /**
     * Method to add the components to the tab
     * It adds the fields and the button to the tab
     */
    public void addComponents() {
        removePanel.add(new JLabel("CPF:"));
        removePanel.add(removeCpfField);
        removePanel.add(new JLabel("LastName:"));
        removePanel.add(removeLastNameField);

        removeByCpfButton.addActionListener(this);
        removeByLastNameButton.addActionListener(this);
        removePanel.add(removeByCpfButton);
        removePanel.add(removeByLastNameButton);
        tabbedPane.addTab("Remove Patron", removePanel);
    }

    /**
     * Method to handle the actions of the buttons
     * It removes the patron from the list of patrons
     * @param e the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to remove this patron?");
        if (confirm == JOptionPane.YES_OPTION) {
            if (e.getSource() == removeByCpfButton) {
                try {
                    if(removeCpfField.getText().isEmpty()){
                        JOptionPane.showMessageDialog(frame, "Please enter a CPF.");
                        return;
                    }
                    handler.deletePatron(removeCpfField.getText());
                    JOptionPane.showMessageDialog(frame, "Patron removed successfully!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error removing patron.");
                }

            }else if (e.getSource() == removeByLastNameButton) {
                try {
                    if(removeLastNameField.getText().isEmpty()){
                        JOptionPane.showMessageDialog(frame, "Please enter a last name.");
                        return;
                    }
                    List<Patron> patrons = handler.searchPatronsByLastName(removeLastNameField.getText());
                    if (!patrons.isEmpty()) {
                        handler.deletePatron(patrons.get(0).getCpf());
                        JOptionPane.showMessageDialog(frame, "Patron removed successfully!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Patron not found.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error removing patron.");
                }
            }
        }
    }
}

