package librarySystem;

import javax.swing.*;
import java.awt.*;

import librarySystem.book.bookGUI.BookFrame;
import librarySystem.loanSystem.loanGUI.LoanFrame;
import librarySystem.login.loginGUI.LoginFrame;
import librarySystem.patron.patronGUI.PatronFrame;


public class AdminFrame {
    private final JFrame frame;
    private JPanel panel;

    private JLabel titleLabel;
    private JButton bookButton;
    private JButton patronButton;
    private JButton loginButton;
    private JButton loanButton;

    public AdminFrame() {
        frame = new JFrame("Administer Library System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        Font font = new Font("Arial", Font.BOLD, 20);
        Color backgroundColor = new Color(230, 230, 250); // Light lavender
        Color foregroundColor = new Color(25, 25, 112); // Midnight blue

        // Modify the look and feel of the GUI
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("Label.foreground", foregroundColor);
        UIManager.put("Button.foreground", foregroundColor);
        UIManager.put("Panel.background", backgroundColor);
        UIManager.put("Button.background", Color.LIGHT_GRAY);

        createComponents();
        addComponents();

        frame.setVisible(true);
    }

    private void createComponents(){
        panel = new JPanel(new GridLayout(5, 1));
        titleLabel = new JLabel("Choose one of the following options to manage:");
        titleLabel.setBounds(350, 50, 100, 50);

        bookButton = new JButton("Manage Books");
        bookButton.setBounds(300, 200, 200, 50);
        bookButton.addActionListener(e -> {
            new BookFrame();
            frame.dispose();
        });

        patronButton = new JButton("Manage Patrons");
        patronButton.setBounds(300, 300, 200, 50);
        patronButton.addActionListener(e -> {
            new PatronFrame();
            frame.dispose();
        });

        loginButton = new JButton("Manage Logins");
        loginButton.setBounds(300, 400, 200, 50);
        loginButton.addActionListener(e -> {
            new LoginFrame();
            frame.dispose();
        });

        loanButton = new JButton("Manage Loans");
        loanButton.setBounds(300, 500, 200, 50);
        loanButton.addActionListener(e -> {
            new LoanFrame();
            frame.dispose();
        });
    }

    private void addComponents(){
        panel.add(titleLabel);
        panel.add(bookButton);
        panel.add(patronButton);
        panel.add(loginButton);
        panel.add(loanButton);
        frame.add(panel);
    }
}
