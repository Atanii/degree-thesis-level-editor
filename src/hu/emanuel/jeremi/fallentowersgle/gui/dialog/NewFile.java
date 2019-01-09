package hu.emanuel.jeremi.fallentowersgle.gui.dialog;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hu.emanuel.jeremi.fallentowersgle.gui.EditorWindow;

public class NewFile extends JDialog {

    JPanel p;
    JLabel l_w, l_h;
    JTextField w, h;
    JButton ok;

    public NewFile(EditorWindow gui, boolean modal) {
        super(gui, modal);

        setTitle("New Map");

        p = new JPanel();
        p.setBackground(Color.BLACK);
        p.setLayout(new FlowLayout());

        l_w = new JLabel("Width: ");
        l_h = new JLabel("Height: ");

        w = new JTextField(5);
        h = new JTextField(5);

        ok = new JButton("OK");

        ok.addActionListener(e -> {
            if (!w.getText().isEmpty() && !h.getText().isEmpty()) {
                gui.setMapSize(Integer.parseInt(w.getText()), Integer.parseInt(h.getText()));
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Height or width is not defined!");
            }
        });

        l_w.setForeground(Color.YELLOW);
        l_h.setForeground(Color.YELLOW);

        w.setBackground(Color.BLUE);
        h.setBackground(Color.BLUE);

        w.setForeground(Color.GREEN);
        h.setForeground(Color.GREEN);

        ok.setBackground(Color.BLACK);
        ok.setForeground(Color.BLUE);

        p.add(l_w);
        p.add(w);

        p.add(l_h);
        p.add(h);

        p.add(ok);

        add(p);

        pack();

        setVisible(true);
    }

}
