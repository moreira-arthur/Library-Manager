package librarySystem.loanSystem.loanGUI;

import librarySystem.TabModel;
import librarySystem.loanSystem.LoanSystemFileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Class that creates the tab to close a loan
 * It implements the TabModel interface
 */
public class CloseLoanTab implements TabModel {
    private final JFrame frame;
    private final LoanSystemFileHandler handler;
    private final JTabbedPane tabbedPane;

    private JPanel addPanel;
    private JTextField getIsbnField;
    private JButton addButton;

    /**
     * Constructor for the CloseLoanTab
     * It initializes the frame, handler and tabbedPane
     * @param frame the frame
     * @param handler the loan handler
     * @param tabbedPane the tabbed pane
     */
    public CloseLoanTab(JFrame frame, LoanSystemFileHandler handler, JTabbedPane tabbedPane){
        this.frame = frame;
        this.handler = handler;
        this.tabbedPane = tabbedPane;

        createTab();
    }

    /**
     * Method to create the tab
     * It initializes the components and adds them to the tab
     */
    public void createTab(){
        initComponents();
        addComponents();
    }

    /**
     * Method to initialize the components
     * It creates the fields for the Loan attributes and the button
     */
    public void initComponents(){
        addPanel = new JPanel(new GridLayout(13, 2));
        addPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getIsbnField = new JTextField();
        addButton = new JButton("Close Loan");
    }

    /**
     * Method to add the components to the tab
     * It adds the fields and the button to the tab
     */
    public void addComponents(){
        addPanel.add(new JLabel("ISBN:"));
        addPanel.add(getIsbnField);

        addButton.addActionListener(this);
        addPanel.add(new JLabel());
        addPanel.add(addButton);
        tabbedPane.addTab("Close Loan", addPanel);
    }

    /**
     * Method to handle the button click
     * It closes the loan
     * It shows a message dialog if the loan is closed successfully
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        try {
            String isbn = getIsbnField.getText();
            if(isbn.isEmpty()){
                throw new Exception("Please fill in the fields.");
            }
            if(!handler.checkLoan(isbn).isEmpty()){
                handler.closeLoan(isbn);
            } else {
                throw new Exception("Loan not found.");
            }
            JOptionPane.showMessageDialog(frame, "Loan closed successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error closing loan book." + "\n" + ex.getMessage());
        } catch(Exception ex){
            JOptionPane.showMessageDialog(frame, "Error closing loan book." + "\n" + "Please check the fields and try again.");
        }
    }

}
