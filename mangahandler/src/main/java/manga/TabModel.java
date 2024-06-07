package manga;

import java.awt.event.*;

public interface TabModel extends ActionListener{

    public void createTab();
    public void initComponents();
    public void addComponents();

    public void actionPerformed(ActionEvent e);
}   
