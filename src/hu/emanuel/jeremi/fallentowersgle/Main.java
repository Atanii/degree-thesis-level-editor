package hu.emanuel.jeremi.fallentowersgle;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import hu.emanuel.jeremi.fallentowersgle.gui.EditorWindow;

public class Main {

    public Main() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // TODO Auto-generated catch block
        // TODO Auto-generated catch block
        // TODO Auto-generated catch block

        SwingUtilities.invokeLater(() -> {
            new EditorWindow();
        });
    }

}
