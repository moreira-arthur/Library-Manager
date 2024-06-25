package librarySystem.loanSystem.loanGUI;

import librarySystem.TabModel;
import librarySystem.loanSystem.LoanSystem;
import librarySystem.loanSystem.LoanSystemFileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDate;


/**
 * Tab for adding a loan
 * It has fields for the loan attributes and a button to add the loan
 */
public class NewLoanTab implements TabModel {
    private final JFrame frame;
    private final LoanSystemFileHandler handler;
    private final JTabbedPane tabbedPane;

    private JPanel addPanel;
    private JTextField getIsbnField;
    private JTextField getCpfField;
    private JButton addButton;

    /**
     * Constructor fot the new loan tab
     * It initializes the frame, handler and tabbedPane
     * It creates the tab
     * @param frame
     * @param handler
     * @param tabbedPane
     */
    public NewLoanTab(JFrame frame, LoanSystemFileHandler handler, JTabbedPane tabbedPane){
        this.frame = frame;
        this.handler = handler;
        this.tabbedPane = tabbedPane;

        createTab();
    }

    /**
     * Method to create the tab
     * It initializes the components and adds the components
     */
    public void createTab(){
        initComponents();
        addComponents();
    }

    /**
     * Method to initialize the components
     * It creates the fields for the loan attributes and the button
     */
    public void initComponents(){
        addPanel = new JPanel(new GridLayout(13, 2));
        addPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getIsbnField = new JTextField();
        getCpfField = new JTextField();
        addButton = new JButton("New Loan");
    }

    /**
     * Method to add the components to the tab
     * It adds the fields for the manga attributes and the button
     */
    public void addComponents(){
        addPanel.add(new JLabel("ISBN:"));
        addPanel.add(getIsbnField);
        addPanel.add(new JLabel("Cpf:"));
        addPanel.add(getCpfField);

        addButton.addActionListener(this);
        addPanel.add(new JLabel());
        addPanel.add(addButton);
        tabbedPane.addTab("New Loan", addPanel);
    }

    /**
     * Method to add a new loan
     * It gets the values from the fields and creates a loan object
     * It adds the loan to the handler
     * It shows a message dialog with the result
     * @param e the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        try {
            if(getIsbnField.getText().isEmpty() || getCpfField.getText().isEmpty()) {
                throw new Exception("Empty fields");
            }
            if(!handler.isBookAvailable(getIsbnField.getText())){
                throw new IOException("Book not available.");
            }
            if(!handler.isCpfRegistered(getCpfField.getText())){
                throw new IOException("Patron not found.");
            }
            LoanSystem loanSystem = new LoanSystem(
                    getIsbnField.getText(),
                    getCpfField.getText(),
                    LocalDate.now(),
                    LocalDate.now().plusDays(7),
                    "OPEN  "
            );
            handler.newLoan(loanSystem);
            JOptionPane.showMessageDialog(frame, "Loan added successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error adding loan." + "\n" + ex.getMessage());
        } catch(Exception ex){
            JOptionPane.showMessageDialog(frame, "Error adding loan." + "\n" + "Please check the fields and try again.");
        }
    }

}
