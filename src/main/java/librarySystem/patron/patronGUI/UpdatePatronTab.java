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
 * Tab for updating patron
 * It has fields for the patron attributes and a button to update the patron
 * It implements the TabModel interface
 */

public class UpdatePatronTab implements TabModel {
    private final JFrame frame;
    private final PatronHandler handler;
    private final JTabbedPane tabbedPane;

    private JPanel updatePanel;
    private JTextField updateLastNameSearchField;
    private JTextField updateLastNameField;
    private JTextField updateNameField;
    private JTextField updateEmailField;
    private JTextField updatePhoneNumberField;
    private JTextField updatePasswordField;
    private JButton updateByLastNameButton;

    /**
     * Constructor for the UpdatePatronTab
     * It initializes the frame, handler and tabbedPane
     * @param frame the frame
     * @param handler the patron handler
     * @param tabbedPane the tabbed pane
     */
    public UpdatePatronTab(JFrame frame, PatronHandler handler, JTabbedPane tabbedPane){
        this.frame = frame;
        this.handler = handler;
        this.tabbedPane = tabbedPane;

        createTab();
    }


    /**
     * Method to create the tab
     * It initializes the components and adds the components
     */
    public void createTab() {
        initComponents();
        addComponents();

    }

    /**
     * Method to initialize the components
     */
    public void initComponents() {
        updatePanel = new JPanel(new GridLayout(13, 2));
        updatePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        updateLastNameSearchField = new JTextField();
        updateLastNameField = new JTextField();
        updateNameField = new JTextField();
        updateEmailField = new JTextField();
        updatePhoneNumberField = new JTextField();
        updatePasswordField = new JTextField();
        updateByLastNameButton = new JButton("Update Patrons by Title");
    }

    /**
     * Method to add the components
     */
    public void addComponents() {
        updatePanel.add(new JLabel("Last Name to be UPDATED:"));
        updatePanel.add(updateLastNameSearchField);
        updatePanel.add(new JLabel());
        updatePanel.add(new JLabel());
        updatePanel.add(new JLabel("Name:"));
        updatePanel.add(updateNameField);
        updatePanel.add(new JLabel("Last Name:"));
        updatePanel.add(updateLastNameField);
        updatePanel.add(new JLabel("Email:"));
        updatePanel.add(updateEmailField);
        updatePanel.add(new JLabel("Phone Number:"));
        updatePanel.add(updatePhoneNumberField);
        updatePanel.add(new JLabel("Password:"));
        updatePanel.add(updatePasswordField);

        updateByLastNameButton.addActionListener(this);
        updatePanel.add(new JLabel());
        updatePanel.add(updateByLastNameButton);
        tabbedPane.addTab("Update Patron", updatePanel);
    }

    /**
     * Method to update the patron
     * It gets the values from the fields and updates the patron
     * It shows a message dialog if the patron is updated successfully or if there is an error
     * @param e the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        try {
            List<String> cpfs = handler.getCpfsByLastName(updateLastNameSearchField.getText());
            if(cpfs.size() == 0) {
                throw new IOException("Patron not found.");
            }
            if(updateNameField.getText().isEmpty() ||
                    updateLastNameField.getText().isEmpty() ||
                    updateEmailField.getText().isEmpty() ||
                    updatePhoneNumberField.getText().isEmpty() ||
                    updatePasswordField.getText().isEmpty()) {
                throw new IOException("All fields must be filled out.");
            }
            Patron updatedPatron = new Patron(
                    updateNameField.getText(),
                    updateLastNameField.getText(),
                    cpfs.get(0),
                    updatePasswordField.getText(),
                    updateEmailField.getText(),
                    updatePhoneNumberField.getText()
            );
            handler.updatePatron(cpfs.get(0), updatedPatron);
            JOptionPane.showMessageDialog(frame, "Patron updated successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error updating patron.");
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error updating patron. Please check the fields.");
        }

    }

}
